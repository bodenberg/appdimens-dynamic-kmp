/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-sdps.git
 * Date: 2025-10-04
 *
 * Library: AppDimens
 *
 * Description:
 * The AppDimens library is a dimension management system that automatically
 * adjusts Dp, Sp, and Px values in a responsive and mathematically refined way,
 * ensuring layout consistency across any screen size or ratio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appdimens.dynamic.code.fluid

import com.appdimens.dynamic.platform.DimenCallContext
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import com.appdimens.dynamic.common.ScreenOrientation


import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.DpQualifierEntry
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.core.DimenCache

/**
 * EN
 * Represents a custom dimension entry with qualifiers and priority.
 * Used by the DimenFluidScaled class to define specific values for screen conditions.
 *
 * PT
 * Representa uma entrada de dimensão customizada com qualificadores e prioridade.
 * Usada pela classe DimenFluidScaled para definir valores específicos para condições de tela.
 */
data class CustomDpEntry(
    val uiModeType: UiModeType? = null,
    val dpQualifierEntry: DpQualifierEntry? = null,
    val orientation: Orientation? = Orientation.DEFAULT,
    val customValue: Float, // Representing DP
    val finalQualifierResolver: DpQualifier? = null,
    val priority: Int,
    val inverter: Inverter? = Inverter.DEFAULT
)

// EN Methods for creating the DimenFluidScaled class.
// PT Métodos de criação da classe DimenFluidScaled.

/**
 * EN Starts the build chain for the custom dimension DimenFluidScaled from a base Float (Dp).
 * PT Inicia a cadeia de construção para a dimensão customizada DimenFluidScaled a partir de um Float (Dp) base.
 */
fun Float.fluidScaledDp(): DimenFluidScaled = DimenFluidScaled(this)

/**
 * EN Starts the build chain for the custom dimension DimenFluidScaled from a base Int (Dp).
 * PT Inicia a cadeia de construção para a dimensão customizada DimenFluidScaled a partir de um Int (Dp) base.
 */
fun Number.fluidScaledDp(): DimenFluidScaled = this.toFloat().fluidScaledDp()

/**
 * EN
 * A class that allows defining custom dimensions
 * based on screen qualifiers (UiModeType, Width, Height, Smallest Width).
 *
 * The value is resolved using a Context and uses the base value or a
 * custom value, applying dynamic scaling at the end.
 *
 * PT
 * Classe que permite a definição de dimensões customizadas
 * baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).
 */
class DimenFluidScaled private constructor(
    private val initialBaseDp: Float,
    private val sortedCustomEntries: List<CustomDpEntry> = emptyList(),
    private val ignoreMultiWindows: Boolean = false,
    private val applyAspectRatio: Boolean = false,
    private val customSensitivityK: Float? = null
) {

    // EN Main constructor to start the chain.
    constructor(initialBaseDp: Float) : this(initialBaseDp, emptyList(), false, false, null)


    /**
     * EN Allow applying aspect ratio based constraint scaling.
     * PT Permite aplicar o escalonamento restrito baseado na proporção da tela (aspect ratio).
     */
    @JvmOverloads
    fun applyAspectRatio(apply: Boolean = true): DimenFluidScaled {
        return DimenFluidScaled(initialBaseDp, sortedCustomEntries, ignoreMultiWindows, apply, customSensitivityK)
    }

    /**
     * EN Allow ignoring the constraint scaling based on multi-window resizing properties.
     * PT Permite ignorar o escalonamento restrito baseado nas propriedades de redimensionamento de multi-janelas.
     */
    @JvmOverloads
    fun ignoreMultiWindows(ignore: Boolean = true): DimenFluidScaled {
        return DimenFluidScaled(initialBaseDp, sortedCustomEntries, ignore, applyAspectRatio, customSensitivityK)
    }

    private fun reorderEntries(newEntry: CustomDpEntry): List<CustomDpEntry> {
        return (sortedCustomEntries + newEntry).sortedWith(
            compareBy<CustomDpEntry> { it.priority }
                .thenByDescending { it.dpQualifierEntry?.value?.toFloat() ?: 0f }
        )
    }

    // EN Builder methods.

    @JvmOverloads
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Number,
        orientation: Orientation? = Orientation.DEFAULT,
        customValue: Float,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFluidScaled {
        val entry = CustomDpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 1,
            inverter = inverter
        )
        return DimenFluidScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    @JvmOverloads
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Number,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFluidScaled = screen(uiModeType, qualifierType, qualifierValue, orientation, customValue.toFloat(), finalQualifierResolver, inverter)

    @JvmOverloads
    fun screen(
        type: UiModeType,
        customValue: Float,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFluidScaled {
        val entry = CustomDpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter
        )
        return DimenFluidScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    @JvmOverloads
    fun screen(
        type: UiModeType,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFluidScaled = screen(type, customValue.toFloat(), finalQualifierResolver, orientation, inverter)

    @JvmOverloads
    fun screen(
        type: DpQualifier,
        value: Int,
        customValue: Float,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFluidScaled {
        val entry = CustomDpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter
        )
        return DimenFluidScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    @JvmOverloads
    fun screen(
        type: DpQualifier,
        value: Int,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFluidScaled = screen(type, value, customValue.toFloat(), finalQualifierResolver, orientation, inverter)

    @JvmOverloads
    fun screen(
        orientation: Orientation = Orientation.DEFAULT,
        customValue: Float,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFluidScaled {
        val entry = CustomDpEntry(
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 4,
            inverter = inverter
        )
        return DimenFluidScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    @JvmOverloads
    fun screen(
        orientation: Orientation = Orientation.DEFAULT,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFluidScaled = screen(orientation, customValue.toFloat(), finalQualifierResolver, inverter)

    // EN Resolution logic.

    private fun resolveDp(ctx: DimenCallContext, qualifier: DpQualifier): Float {
        val metrics = ctx.screenMetrics
        val currentUiModeType = ctx.currentUiMode()
        return resolveDpInternal(ctx, qualifier, metrics, currentUiModeType)
    }

    /**
     * EN Resolves sdp, hdp, and wdp in one pass (single [UiModeType.fromConfiguration] and config read).
     * PT Resolve sdp, hdp e wdp numa só passagem.
     */
    fun sdpHdpWdpPx(ctx: DimenCallContext): Triple<Float, Float, Float> {
        val metrics = ctx.screenMetrics
        val currentUiModeType = ctx.currentUiMode()
        val density = ctx.screenMetrics.density
        val sdp = resolveDpInternal(ctx, DpQualifier.SMALL_WIDTH, metrics, currentUiModeType) * density
        val hdp = resolveDpInternal(ctx, DpQualifier.HEIGHT, metrics, currentUiModeType) * density
        val wdp = resolveDpInternal(ctx, DpQualifier.WIDTH, metrics, currentUiModeType) * density
        return Triple(sdp, hdp, wdp)
    }

    private fun resolveDpInternal(
        ctx: DimenCallContext,
        qualifier: DpQualifier,
        metrics: ScreenMetricsSnapshot,
        currentUiModeType: UiModeType
    ): Float {
        val isLandscape = metrics.orientation == ScreenOrientation.LANDSCAPE
        val isPortrait = metrics.orientation == ScreenOrientation.PORTRAIT

        val foundEntry = sortedCustomEntries.firstOrNull { entry ->
            val qualifierEntry = entry.dpQualifierEntry
            val uiModeMatch = entry.uiModeType == null || entry.uiModeType == currentUiModeType
            val orientationMatch = when (entry.orientation) {
                Orientation.LANDSCAPE -> isLandscape
                Orientation.PORTRAIT -> isPortrait
                else -> true
            }

            if (qualifierEntry != null) {
                val qualifierMatch = getQualifierValue(qualifierEntry.type, metrics) >= qualifierEntry.value.toFloat()
                if (entry.priority == 1 && uiModeMatch && qualifierMatch && orientationMatch) return@firstOrNull true
                if (entry.priority == 3 && qualifierMatch && orientationMatch) return@firstOrNull true
                false
            } else {
                if (entry.priority == 2 && uiModeMatch && orientationMatch) return@firstOrNull true
                if (entry.priority == 4 && orientationMatch) return@firstOrNull true
                false
            }
        }

        val dpToUse = foundEntry?.customValue ?: initialBaseDp
        val finalQualifier = foundEntry?.finalQualifierResolver ?: qualifier

        return dpToUse.toDynamicFluidDp(
            ctx,
            finalQualifier,
            foundEntry?.inverter ?: Inverter.DEFAULT,
            ignoreMultiWindows,
            applyAspectRatio,
            customSensitivityK
        )
    }

    /**
     * EN Resolves the final value in pixels (Float).
     */
    fun px(ctx: DimenCallContext, qualifier: DpQualifier): Float {
        val metrics = ctx.screenMetrics
        val currentUiModeType = ctx.currentUiMode()
        val dpValue = resolveDpInternal(ctx, qualifier, metrics, currentUiModeType)
        return dpValue * ctx.screenMetrics.density
    }

    // EN Convenience properties/methods similar to Compose version.

    fun sdp(ctx: DimenCallContext): Float = px(ctx, DpQualifier.SMALL_WIDTH)
    fun hdp(ctx: DimenCallContext): Float = px(ctx, DpQualifier.HEIGHT)
    fun wdp(ctx: DimenCallContext): Float = px(ctx, DpQualifier.WIDTH)

    /** EN Get the resolved value in DP (as Float). */
    fun sdpBase(ctx: DimenCallContext): Float = resolveDp(ctx, DpQualifier.SMALL_WIDTH)
    fun hdpBase(ctx: DimenCallContext): Float = resolveDp(ctx, DpQualifier.HEIGHT)
    fun wdpBase(ctx: DimenCallContext): Float = resolveDp(ctx, DpQualifier.WIDTH)
}