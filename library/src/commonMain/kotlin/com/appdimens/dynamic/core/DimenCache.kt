/**
 * Global, lock-free, shared cache for AppDimens (KMP: atomicfu + [CachePersistence]).
 */
package com.appdimens.dynamic.core

import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.platform.CachePersistence
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import kotlinx.atomicfu.AtomicIntArray
import kotlinx.atomicfu.AtomicLongArray
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

object DimenCache {

    private val resetListeners = mutableListOf<() -> Unit>()
    private val resetListenerLock = Any()

    fun addResetListener(listener: () -> Unit) {
        synchronized(resetListenerLock) {
            resetListeners.add(listener)
        }
    }

    fun removeResetListener(listener: () -> Unit) {
        synchronized(resetListenerLock) {
            resetListeners.remove(listener)
        }
    }

    private fun notifyResetListeners() {
        val snapshot = synchronized(resetListenerLock) {
            resetListeners.toList()
        }
        snapshot.forEach { it() }
    }

    @Volatile
    private var _scope: CoroutineScope? = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val scopeLock = Any()

    internal val scope: CoroutineScope
        get() = _scope ?: synchronized(scopeLock) {
            _scope ?: CoroutineScope(SupervisorJob() + Dispatchers.Default).also {
                _scope = it
                launchSaveCollector(it)
            }
        }

    private val isInitializing = atomic(false)
    @Volatile
    @PublishedApi
    internal var isInitializedFast = false

    /** Must stay private for atomicfu JVM/Native IR (no public atomic refs). */
    private val isInitializedAtomic = atomic(false)

    /** Instrumented / Java tests only. */
    @JvmStatic
    fun setInitializedFlagForTests(initialized: Boolean) {
        isInitializedAtomic.value = initialized
    }

    enum class CalcType {
        AUTO, DIAGONAL, FILL, FIT, FLUID, INTERPOLATED, LOGARITHMIC,
        PERCENT, PERIMETER, POWER, RESIZE, SCALED, UNITIES, ASPECT_RATIO, DENSITY
    }

    @JvmField @PublishedApi internal val CT_PERCENT = CalcType.PERCENT.ordinal
    @JvmField @PublishedApi internal val CT_SCALED = CalcType.SCALED.ordinal
    @JvmField @PublishedApi internal val CT_DENSITY = CalcType.DENSITY.ordinal
    @JvmField @PublishedApi internal val CT_ASPECT_RATIO = CalcType.ASPECT_RATIO.ordinal
    @JvmField @PublishedApi internal val CT_DIAGONAL = CalcType.DIAGONAL.ordinal
    @JvmField @PublishedApi internal val CT_INTERPOLATED = CalcType.INTERPOLATED.ordinal
    @JvmField @PublishedApi internal val CT_PERIMETER = CalcType.PERIMETER.ordinal

    @Volatile
    @PublishedApi
    internal var diagnosticsEnabled: Boolean = false

    private val hitCount = atomic(0L)
    private val missCount = atomic(0L)
    private val evictionCount = atomic(0L)

    @Volatile
    @PublishedApi
    internal var isEnabled: Boolean = true

    @JvmField @Volatile
    internal var cachedUiMode: UiModeType = UiModeType.UNDEFINED

    @Volatile
    private var cachedUiModeConfigHash: Int = 0

    fun updateCachedUiMode(uiMode: UiModeType, configHash: Int) {
        cachedUiMode = uiMode
        cachedUiModeConfigHash = configHash
    }

    fun getCachedUiModeForMetrics(metrics: ScreenMetricsSnapshot): UiModeType {
        val h = metrics.layoutConfigHash
        val cached = cachedUiMode
        if (cachedUiModeConfigHash == h && cached != UiModeType.UNDEFINED) return cached
        return UiModeType.NORMAL
    }

    internal class ScreenFactors {
        @JvmField @Volatile var normalizedAr: Float = 1.0f
        @JvmField @Volatile var logNormalizedAr: Float = 0f
        @JvmField @Volatile var smallestWidthDp: Int = 0
        @JvmField @Volatile var density: Float = 1.0f
        @JvmField @Volatile var scale: Float = 1.0f
        @JvmField @Volatile var arMultiplier: Float = 1.0f
        @JvmField @Volatile var diagonalScale: Float = 1.0f
        @JvmField @Volatile var powerScale: Float = 1.0f
        @JvmField @Volatile var logScale: Float = 1.0f
        @JvmField @Volatile var interpolatedScale: Float = 1.0f
        @JvmField @Volatile var perimeterScale: Float = 1.0f
        @JvmField @Volatile var aspectRatioMul: Float = 1.0f
        @Suppress("unused") @JvmField val _p0 = 0L
        @Suppress("unused") @JvmField val _p1 = 0L
        @Suppress("unused") @JvmField val _p2 = 0L
        @Suppress("unused") @JvmField val _p3 = 0L
        @Suppress("unused") @JvmField val _p4 = 0L
        @Suppress("unused") @JvmField val _p5 = 0L
        @Suppress("unused") @JvmField val _p6 = 0L
        @Suppress("unused") @JvmField val _p7 = 0L
    }

    @JvmField
    @PublishedApi
    internal val factors = ScreenFactors()

    @PublishedApi internal val currentNormalizedAr get() = factors.normalizedAr
    @PublishedApi internal val currentLogNormalizedAr get() = factors.logNormalizedAr
    @PublishedApi internal val currentSmallestWidthDp get() = factors.smallestWidthDp
    @PublishedApi internal val currentDensity get() = factors.density
    @PublishedApi internal val currentScale get() = factors.scale
    @PublishedApi internal val currentArMultiplier get() = factors.arMultiplier
    @PublishedApi internal val currentDiagonalScale get() = factors.diagonalScale
    @PublishedApi internal val currentPowerScale get() = factors.powerScale
    @PublishedApi internal val currentLogScale get() = factors.logScale
    @PublishedApi internal val currentInterpolatedScale get() = factors.interpolatedScale
    @PublishedApi internal val currentPerimeterScale get() = factors.perimeterScale
    @PublishedApi internal val currentAspectRatioMul get() = factors.aspectRatioMul

    @PublishedApi
    internal const val CACHE_SIZE = 2048
    @PublishedApi internal const val SHARD_COUNT = 4
    @PublishedApi internal const val SHARD_MASK = SHARD_COUNT - 1
    @PublishedApi internal const val SHARD_SIZE = CACHE_SIZE / SHARD_COUNT
    @PublishedApi internal const val SHARD_SIZE_MASK = SHARD_SIZE - 1

    /** Lock-free slots via atomicfu primitive arrays (JVM → [java.util.concurrent.atomic.AtomicLongArray] / Int). */
    private val cacheSlotKeys = AtomicLongArray(CACHE_SIZE)
    private val cacheSlotVals = AtomicIntArray(CACHE_SIZE)

    @PublishedApi
    internal fun cacheSlotIndex(shard: Int, slot: Int): Int = shard * SHARD_SIZE + slot

    /** Test / diagnostics: direct shard access */
    @PublishedApi
    internal fun shardKeyGet(shard: Int, slot: Int): Long {
        val i = cacheSlotIndex(shard, slot)
        return cacheSlotKeys[i].value
    }
    @PublishedApi
    internal fun shardKeySet(shard: Int, slot: Int, key: Long) {
        val i = cacheSlotIndex(shard, slot)
        cacheSlotKeys[i].value = key
    }
    @PublishedApi
    internal fun shardValGet(shard: Int, slot: Int): Int {
        val i = cacheSlotIndex(shard, slot)
        return cacheSlotVals[i].value
    }
    @PublishedApi
    internal fun shardValSet(shard: Int, slot: Int, bits: Int) {
        val i = cacheSlotIndex(shard, slot)
        cacheSlotVals[i].value = bits
    }

    @PublishedApi internal const val INV_BASE_RATIO = 0.0033333334f
    @PublishedApi internal const val ADJUSTMENT_SCALE = 0.10f / 30f
    @PublishedApi internal const val SENSITIVITY_DEFAULT = 0.08f / 30f

    @PublishedApi
    internal fun calculateRawScaling(
        baseValue: Float,
        applyAspectRatio: Boolean,
        customSensitivityK: Float?,
    ): Float {
        val f = factors
        return if (applyAspectRatio) {
            val factor = if (customSensitivityK == null) {
                f.arMultiplier
            } else {
                val logAr = f.logNormalizedAr
                val adjustment = customSensitivityK * logAr
                1.0f + (f.smallestWidthDp - 300f) * (ADJUSTMENT_SCALE + adjustment)
            }
            baseValue * factor
        } else {
            baseValue * f.scale
        }
    }

    private val saveFlow = MutableSharedFlow<CachePersistence>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private fun launchSaveCollector(target: CoroutineScope) {
        target.launch {
            @OptIn(FlowPreview::class)
            saveFlow.sample(500).collect { persistence ->
                performSave(persistence)
            }
        }
    }

    init {
        _scope?.let { launchSaveCollector(it) }
    }

    fun shutdown() {
        synchronized(scopeLock) {
            _scope?.cancel()
            _scope = null
        }
    }

    enum class ValueType {
        DP, PX, SP_WITH_SCALE, SP_NO_SCALE, SP_PX_WITH_SCALE, SP_PX_NO_SCALE
    }

    fun buildKey(
        baseValue: Float,
        isLandscape: Boolean,
        ignoreMultiWindows: Boolean,
        calcType: CalcType,
        qualifier: DpQualifier,
        inverter: Inverter,
        applyAspectRatio: Boolean,
        valueType: ValueType,
        customSensitivityK: Float? = null,
    ): Long {
        val ar = if (applyAspectRatio) 1L else 0L
        val bv = baseValue.toRawBits().toLong() and 0xFFFFFFFFL
        val ct = calcType.ordinal.toLong() and 0xFL
        val vt = valueType.ordinal.toLong() and 0x7L
        val sk = (customSensitivityK?.toRawBits()?.ushr(16)?.and(0xFFFF)?.toLong() ?: 0xFFFFL)
        val q = qualifier.ordinal.toLong() and 0x3L
        val inv = inverter.ordinal.toLong() and 0xFL
        val land = if (isLandscape) 1L else 0L
        val imw = if (ignoreMultiWindows) 1L else 0L
        return (ar shl 63) or
            (bv shl 31) or
            (ct shl 27) or
            (vt shl 24) or
            (sk shl 8) or
            (q shl 6) or
            (inv shl 2) or
            (land shl 1) or
            imw
    }

    fun buildKey(
        baseValue: Int,
        isLandscape: Boolean,
        ignoreMultiWindows: Boolean,
        calcType: CalcType,
        qualifier: DpQualifier,
        inverter: Inverter,
        applyAspectRatio: Boolean,
        valueType: ValueType,
        customSensitivityK: Float? = null,
    ): Long = buildKey(
        baseValue.toFloat(), isLandscape, ignoreMultiWindows, calcType,
        qualifier, inverter, applyAspectRatio, valueType, customSensitivityK,
    )

    fun init(persistence: CachePersistence, metrics: ScreenMetricsSnapshot) {
        if (isInitializedAtomic.value) {
            isInitializedFast = true
            return
        }
        if (!isInitializing.compareAndSet(false, true)) return

        val currentSw = metrics.smallestWidthDp
        updateFactors(metrics)
        factors.smallestWidthDp = currentSw
        isInitializedFast = true

        scope.launch {
            try {
                val (savedSw, rawData) = persistence.load()
                if (savedSw != currentSw || rawData == null) {
                    if (savedSw != 0 && savedSw != currentSw) {
                        clearAll(null)
                        persistence.clearStore()
                    }
                } else {
                    loadFromByteArray(rawData)
                }
            } catch (_: Exception) {
            } finally {
                isInitializedAtomic.value = true
                isInitializing.value = false
            }
        }
    }

    internal fun loadFromByteArray(data: ByteArray) {
        if (data.size < CACHE_SIZE * 12) return
        var offset = 0
        fun readLong(): Long {
            var v = 0L
            for (k in 0..7) v = (v shl 8) or (data[offset++].toInt() and 0xFF).toLong()
            return v
        }
        fun readInt(): Int {
            return ((data[offset++].toInt() and 0xFF) shl 24) or
                ((data[offset++].toInt() and 0xFF) shl 16) or
                ((data[offset++].toInt() and 0xFF) shl 8) or
                (data[offset++].toInt() and 0xFF)
        }
        for (flat in 0 until CACHE_SIZE) {
            val key = readLong()
            val valueBits = readInt()
            if (key != 0L) {
                if (cacheSlotKeys[flat].compareAndSet(0L, key)) {
                    cacheSlotVals[flat].value = valueBits
                }
            }
        }
    }

    fun saveToPersistence(persistence: CachePersistence) {
        saveFlow.tryEmit(persistence)
    }

    private suspend fun performSave(persistence: CachePersistence) {
        val buffer = ByteArray(CACHE_SIZE * 12)
        var offset = 0
        fun writeLong(v: Long) {
            for (k in 7 downTo 0) buffer[offset++] = ((v shr (k * 8)) and 0xFF).toByte()
        }
        fun writeInt(v: Int) {
            buffer[offset++] = ((v ushr 24) and 0xFF).toByte()
            buffer[offset++] = ((v ushr 16) and 0xFF).toByte()
            buffer[offset++] = ((v ushr 8) and 0xFF).toByte()
            buffer[offset++] = (v and 0xFF).toByte()
        }
        for (flat in 0 until CACHE_SIZE) {
            writeLong(cacheSlotKeys[flat].value)
            writeInt(cacheSlotVals[flat].value)
        }
        persistence.save(factors.smallestWidthDp, buffer)
    }

    fun serializeToByteArray(): ByteArray {
        val buffer = ByteArray(CACHE_SIZE * 12)
        var offset = 0
        fun writeLong(v: Long) {
            for (k in 7 downTo 0) buffer[offset++] = ((v shr (k * 8)) and 0xFF).toByte()
        }
        fun writeInt(v: Int) {
            buffer[offset++] = ((v ushr 24) and 0xFF).toByte()
            buffer[offset++] = ((v ushr 16) and 0xFF).toByte()
            buffer[offset++] = ((v ushr 8) and 0xFF).toByte()
            buffer[offset++] = (v and 0xFF).toByte()
        }
        for (flat in 0 until CACHE_SIZE) {
            writeLong(cacheSlotKeys[flat].value)
            writeInt(cacheSlotVals[flat].value)
        }
        return buffer
    }

    @PublishedApi
    internal fun getOrPutInternal(key: Long, persistence: CachePersistence?, compute: () -> Float): Float {
        if (persistence != null && !isInitializedFast) {
            /* caller must have called init; metrics required — noop */
        }
        val h = (key xor (key ushr 32)).toInt()
        val mixed = h xor (h ushr 16)
        val shardIndex = (mixed ushr 9) and SHARD_MASK
        val slotIndex = mixed and SHARD_SIZE_MASK
        val idx = shardIndex * SHARD_SIZE + slotIndex
        val existingKey = cacheSlotKeys[idx].value
        if (existingKey == key) {
            if (diagnosticsEnabled) hitCount.value = hitCount.value + 1
            return Float.fromBits(cacheSlotVals[idx].value)
        }
        if (diagnosticsEnabled) missCount.value = missCount.value + 1
        val computed = compute()
        val ct = (key ushr 27 and 0xFL).toInt()
        val existCt = (existingKey ushr 27 and 0xFL).toInt()
        val isNewAr = ct == CT_ASPECT_RATIO
        val isOldAr = existingKey != 0L && existCt == CT_ASPECT_RATIO
        if (existingKey == 0L || !isOldAr || isNewAr) {
            if (diagnosticsEnabled && existingKey != 0L) evictionCount.value = evictionCount.value + 1
            cacheSlotVals[idx].value = computed.toRawBits()
            cacheSlotKeys[idx].value = key
            persistence?.let { saveToPersistence(it) }
        }
        return computed
    }

    fun getOrPut(key: Long, persistence: CachePersistence? = null, compute: () -> Float): Float {
        if (!isEnabled) return compute()
        if (key >= 0) {
            val ct = (key ushr 27 and 0xFL).toInt()
            if (ct == CT_PERCENT || ct == CT_SCALED || ct == CT_DENSITY ||
                ct == CT_DIAGONAL || ct == CT_INTERPOLATED || ct == CT_PERIMETER
            ) {
                return compute()
            }
        }
        val h = (key xor (key ushr 32)).toInt()
        val mixed = h xor (h ushr 16)
        val shardIndex = (mixed ushr 9) and SHARD_MASK
        val slot = mixed and SHARD_SIZE_MASK
        val idx = shardIndex * SHARD_SIZE + slot
        val existingKey = cacheSlotKeys[idx].value
        if (existingKey == key) {
            if (diagnosticsEnabled) hitCount.value = hitCount.value + 1
            return Float.fromBits(cacheSlotVals[idx].value)
        }
        if (diagnosticsEnabled) missCount.value = missCount.value + 1
        val computed = compute()
        val ct = (key ushr 27 and 0xFL).toInt()
        val existCt = (existingKey ushr 27 and 0xFL).toInt()
        val isNewAr = ct == CT_ASPECT_RATIO
        val isOldAr = existingKey != 0L && existCt == CT_ASPECT_RATIO
        if (existingKey == 0L || !isOldAr || isNewAr) {
            if (diagnosticsEnabled && existingKey != 0L) evictionCount.value = evictionCount.value + 1
            cacheSlotVals[idx].value = computed.toRawBits()
            cacheSlotKeys[idx].value = key
            persistence?.let { saveToPersistence(it) }
        }
        return computed
    }

    fun getOrPut(key: Long, compute: () -> Float): Float = getOrPut(key, null, compute)

    fun peek(key: Long): Float? {
        if (!isEnabled) return null
        val h = (key xor (key ushr 32)).toInt()
        val mixed = h xor (h ushr 16)
        val shardIndex = (mixed ushr 9) and SHARD_MASK
        val slotIndex = mixed and SHARD_SIZE_MASK
        val idx = shardIndex * SHARD_SIZE + slotIndex
        val existing = cacheSlotKeys[idx].value
        return if (existing == key) Float.fromBits(cacheSlotVals[idx].value) else null
    }

    fun getBatch(
        keys: LongArray,
        persistence: CachePersistence? = null,
        compute: (Int) -> Float,
    ): FloatArray {
        val size = keys.size
        val results = FloatArray(size)
        for (i in 0 until size) {
            results[i] = getOrPut(keys[i], persistence) { compute(i) }
        }
        return results
    }

    @PublishedApi
    internal fun getOrPutAspectRatio(normalizedAr: Float, persistence: CachePersistence? = null): Float {
        val arKey = ((normalizedAr.toRawBits().toLong() and 0xFFFFFFFFL) shl 31) or
            (CT_ASPECT_RATIO.toLong() shl 27)
        return getOrPut(arKey, persistence) {
            kotlin.math.ln(normalizedAr)
        }
    }

    fun invalidateOnMetricsChange(old: ScreenMetricsSnapshot?, new: ScreenMetricsSnapshot) {
        if (old == null) {
            updateFactors(new)
            factors.smallestWidthDp = new.smallestWidthDp
            clearAll(null)
            return
        }
        val oldMin = min(old.widthDp, old.heightDp)
        val oldMax = max(old.widthDp, old.heightDp)
        val newMin = min(new.widthDp, new.heightDp)
        val newMax = max(new.widthDp, new.heightDp)
        val physicalChange = oldMin != newMin ||
            oldMax != newMax ||
            old.widthDp != new.widthDp ||
            old.heightDp != new.heightDp ||
            old.smallestWidthDp != new.smallestWidthDp ||
            old.densityDpi != new.densityDpi
        val fontScaleChange = old.fontScale != new.fontScale
        if (physicalChange || fontScaleChange) {
            if (physicalChange) {
                updateFactors(new)
                factors.smallestWidthDp = new.smallestWidthDp
            }
            clearAll(null)
        }
    }

    private fun updateFactors(metrics: ScreenMetricsSnapshot) {
        val sw = metrics.smallestWidthDp.toFloat()
        val maxDim = max(metrics.widthDp.toFloat(), metrics.heightDp.toFloat())
        val minDim = min(metrics.widthDp.toFloat(), metrics.heightDp.toFloat())
        val f = factors
        f.scale = sw * INV_BASE_RATIO
        val rawAr = if (minDim > 0) maxDim / minDim else 1.0f
        f.normalizedAr = rawAr / 1.78f
        f.logNormalizedAr = fastLn(f.normalizedAr)
        val diff = sw - 300f
        val adjustment = SENSITIVITY_DEFAULT * f.logNormalizedAr
        f.arMultiplier = 1.0f + diff * (ADJUSTMENT_SCALE + adjustment)
        f.density = metrics.densityDpi.toFloat() / 160f
        val diag = sqrt((minDim * minDim + maxDim * maxDim).toDouble()).toFloat()
        f.diagonalScale = diag / DesignScaleConstants.BASE_DIAGONAL_DP
        val ratio = sw / DesignScaleConstants.BASE_WIDTH_DP
        f.powerScale = ratio.toDouble().pow(0.75).toFloat()
        val swInv = sw * INV_BASE_RATIO
        f.logScale = if (sw > DesignScaleConstants.BASE_WIDTH_DP) {
            1f + 0.4f * kotlin.math.ln(swInv)
        } else if (sw > 0f) {
            1f - 0.4f * kotlin.math.ln(DesignScaleConstants.BASE_WIDTH_DP / sw)
        } else {
            1f
        }
        f.interpolatedScale = 1f + (sw * INV_BASE_RATIO - 1f) * 0.5f
        f.perimeterScale = (minDim + maxDim) / DesignScaleConstants.BASE_PERIMETER_DP
        f.aspectRatioMul = 1f + SENSITIVITY_DEFAULT * f.logNormalizedAr
    }

    fun clear(persistence: CachePersistence? = null) = clearAll(persistence)

    fun clearAll(persistence: CachePersistence? = null) {
        var flat = 0
        while (flat < CACHE_SIZE - 3) {
            cacheSlotKeys[flat].lazySet(0L); cacheSlotVals[flat].lazySet(0)
            cacheSlotKeys[flat + 1].lazySet(0L); cacheSlotVals[flat + 1].lazySet(0)
            cacheSlotKeys[flat + 2].lazySet(0L); cacheSlotVals[flat + 2].lazySet(0)
            cacheSlotKeys[flat + 3].lazySet(0L); cacheSlotVals[flat + 3].lazySet(0)
            flat += 4
        }
        while (flat < CACHE_SIZE) {
            cacheSlotKeys[flat].lazySet(0L); cacheSlotVals[flat].lazySet(0)
            flat++
        }
        notifyResetListeners()
        persistence?.let { p ->
            scope.launch {
                try {
                    p.clearStore()
                } catch (_: Exception) {
                }
            }
        }
    }

    fun stats(): CacheStats {
        var populated = 0
        for (flat in 0 until CACHE_SIZE) {
            if (cacheSlotKeys[flat].value != 0L) populated++
        }
        val hits = hitCount.value
        val misses = missCount.value
        val total = hits + misses
        return CacheStats(
            capacity = CACHE_SIZE,
            populated = populated,
            fillRatio = populated.toFloat() / CACHE_SIZE,
            hits = hits,
            misses = misses,
            evictions = evictionCount.value,
            hitRate = if (total > 0) hits.toFloat() / total else 0f,
        )
    }

    fun resetDiagnostics() {
        hitCount.value = 0L
        missCount.value = 0L
        evictionCount.value = 0L
    }

    data class CacheStats(
        val capacity: Int,
        val populated: Int,
        val fillRatio: Float,
        val hits: Long = 0,
        val misses: Long = 0,
        val evictions: Long = 0,
        val hitRate: Float = 0f,
    ) {
        override fun toString(): String =
            "DimenCache: $populated/$capacity slots used (${(fillRatio * 100).toInt()}% fill)" +
                if (hits + misses > 0) ", hits=$hits misses=$misses evictions=$evictions hitRate=${(hitRate * 100).toInt()}%" else ""
    }
}
