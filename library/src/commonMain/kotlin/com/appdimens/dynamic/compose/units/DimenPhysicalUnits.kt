/**
 * Compose Multiplatform physical unit accessors using [LocalScreenMetrics] and [LocalDensity].
 */
package com.appdimens.dynamic.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import com.appdimens.dynamic.code.units.DimenPhysicalUnits as CodeUnits
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.ScreenOrientation
import com.appdimens.dynamic.common.UnitType
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.LocalScreenMetrics
import com.appdimens.dynamic.platform.CachePersistence
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import kotlin.math.PI

object DimenPhysicalUnits {

    private const val MM_TO_CM_FACTOR = 10.0f
    private const val MM_TO_INCH_FACTOR = 25.4f
    private const val CIRCUMFERENCE_FACTOR = PI * 2.0

    fun toMm(mm: Float, metrics: ScreenMetricsSnapshot, persistence: CachePersistence? = null): Float {
        val cacheKey = DimenCache.buildKey(
            baseValue = mm,
            isLandscape = metrics.orientation == ScreenOrientation.LANDSCAPE,
            ignoreMultiWindows = false,
            calcType = DimenCache.CalcType.UNITIES,
            qualifier = DpQualifier.SMALL_WIDTH,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = false,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = mm,
        )
        return DimenCache.getOrPut(cacheKey, persistence) {
            CodeUnits.toDpFromMm(mm, metrics)
        }
    }

    fun convertMmToCm(mm: Float): Float = mm / MM_TO_CM_FACTOR
    fun convertMmToInch(mm: Float): Float = mm / MM_TO_INCH_FACTOR

    fun Float.mmToCm(): Float = convertMmToCm(this)
    fun Number.mmToCm(): Float = convertMmToCm(this.toFloat())
    fun Float.mmToInch(): Float = convertMmToInch(this)
    fun Number.mmToInch(): Float = convertMmToInch(this.toFloat())

    fun toCm(cm: Float, metrics: ScreenMetricsSnapshot, persistence: CachePersistence? = null): Float {
        val cacheKey = DimenCache.buildKey(
            baseValue = cm,
            isLandscape = metrics.orientation == ScreenOrientation.LANDSCAPE,
            ignoreMultiWindows = false,
            calcType = DimenCache.CalcType.UNITIES,
            qualifier = DpQualifier.HEIGHT,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = false,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = cm,
        )
        return DimenCache.getOrPut(cacheKey, persistence) {
            CodeUnits.toDpFromCm(cm, metrics)
        }
    }

    fun convertCmToMm(cm: Float): Float = cm * MM_TO_CM_FACTOR
    fun convertCmToInch(cm: Float): Float = cm / (MM_TO_INCH_FACTOR / MM_TO_CM_FACTOR)

    fun Float.cmToMm(): Float = convertCmToMm(this)
    fun Number.cmToMm(): Float = convertCmToMm(this.toFloat())
    fun Float.cmToInch(): Float = convertCmToInch(this)
    fun Number.cmToInch(): Float = convertCmToInch(this.toFloat())

    fun toInch(inches: Float, metrics: ScreenMetricsSnapshot, persistence: CachePersistence? = null): Float {
        val cacheKey = DimenCache.buildKey(
            baseValue = inches,
            isLandscape = metrics.orientation == ScreenOrientation.LANDSCAPE,
            ignoreMultiWindows = false,
            calcType = DimenCache.CalcType.UNITIES,
            qualifier = DpQualifier.WIDTH,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = false,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = inches,
        )
        return DimenCache.getOrPut(cacheKey, persistence) {
            CodeUnits.toDpFromInch(inches, metrics)
        }
    }

    fun convertInchToCm(inch: Float): Float = inch * 2.54f
    fun convertInchToMm(inch: Float): Float = inch * MM_TO_INCH_FACTOR

    fun Float.inchToCm(): Float = convertInchToCm(this)
    fun Number.inchToCm(): Float = convertInchToCm(this.toFloat())
    fun Float.inchToMm(): Float = convertInchToMm(this)
    fun Number.inchToMm(): Float = convertInchToMm(this.toFloat())

    @get:Composable
    val Float.mm: Float
        get() {
            val m = LocalScreenMetrics.current
            return with(LocalDensity.current) { toMm(this@mm, m) }
        }

    @get:Composable
    val Int.mm: Float
        get() {
            val m = LocalScreenMetrics.current
            return with(LocalDensity.current) { toMm(this@mm.toFloat(), m) }
        }

    @get:Composable
    val Float.cm: Float
        get() {
            val m = LocalScreenMetrics.current
            return with(LocalDensity.current) { toCm(this@cm, m) }
        }

    @get:Composable
    val Int.cm: Float
        get() {
            val m = LocalScreenMetrics.current
            return with(LocalDensity.current) { toCm(this@cm.toFloat(), m) }
        }

    @get:Composable
    val Float.inch: Float
        get() {
            val m = LocalScreenMetrics.current
            return with(LocalDensity.current) { toInch(this@inch, m) }
        }

    @get:Composable
    val Int.inch: Float
        get() {
            val m = LocalScreenMetrics.current
            return with(LocalDensity.current) { toInch(this@inch.toFloat(), m) }
        }

    fun radius(diameter: Float, type: UnitType, metrics: ScreenMetricsSnapshot): Float =
        CodeUnits.radiusFromDiameter(diameter, type, metrics)

    @Composable
    fun Float.radius(type: UnitType): Float {
        val m = LocalScreenMetrics.current
        return with(LocalDensity.current) { radius(this@radius, type, m) }
    }

    @Composable
    fun Number.radius(type: UnitType): Float {
        val m = LocalScreenMetrics.current
        return with(LocalDensity.current) { radius(this@radius.toFloat(), type, m) }
    }

    fun displayMeasureDiameter(diameter: Float, isCircumference: Boolean): Float =
        if (isCircumference) (diameter * CIRCUMFERENCE_FACTOR).toFloat() else diameter

    fun Float.measureDiameter(isCircumference: Boolean): Float =
        displayMeasureDiameter(this, isCircumference)

    fun Number.measureDiameter(isCircumference: Boolean): Float =
        displayMeasureDiameter(this.toFloat(), isCircumference)

    fun unitSizeInDp(type: UnitType, metrics: ScreenMetricsSnapshot): Float =
        when (type) {
            UnitType.INCH -> toInch(1.0f, metrics)
            UnitType.CM -> toCm(1.0f, metrics)
            UnitType.MM -> toMm(1.0f, metrics)
            UnitType.SP -> metrics.fontScale
            UnitType.DP -> 1f
            UnitType.PX -> 1f / metrics.density.coerceAtLeast(1e-4f)
        }
}
