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
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.core.DimenCache
import kotlin.math.max
import kotlin.math.min

/**
 * EN
 * Utility object for handling SDP (Scalable Dp) dimensions from Java.
 *
 * PT
 * Objeto utilitário para manipulação de dimensões SDP (Scalable Dp) no Java.
 */
object DimenFluid {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(ctx: DimenCallContext) {
        DimenCache.init(ctx.cachePersistence!!, ctx.screenMetrics)
    }

    /**
     * EN Quick resolution for Smallest Width (sdp).
     * PT Resolução rápida para Smallest Width (sdp).
     */
    @JvmStatic
    fun fsdp(ctx: DimenCallContext, value: Int): Float = value.fsdp(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpa) with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.
     */
    @JvmStatic
    fun fsdpa(ctx: DimenCallContext, value: Int): Float = value.fsdpa(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows.
     * PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.
     */
    @JvmStatic
    fun fsdpi(ctx: DimenCallContext, value: Int): Float = value.fsdpi(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.
     */
    @JvmStatic
    fun fsdpia(ctx: DimenCallContext, value: Int): Float = value.fsdpia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun fsdpPh(ctx: DimenCallContext, value: Int): Float = value.fsdpPh(ctx)

    @JvmStatic
    fun fsdpPha(ctx: DimenCallContext, value: Int): Float = value.fsdpPha(ctx)

    @JvmStatic
    fun fsdpPhi(ctx: DimenCallContext, value: Int): Float = value.fsdpPhi(ctx)

    @JvmStatic
    fun fsdpPhia(ctx: DimenCallContext, value: Int): Float = value.fsdpPhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun fsdpLh(ctx: DimenCallContext, value: Int): Float = value.fsdpLh(ctx)

    @JvmStatic
    fun fsdpLha(ctx: DimenCallContext, value: Int): Float = value.fsdpLha(ctx)

    @JvmStatic
    fun fsdpLhi(ctx: DimenCallContext, value: Int): Float = value.fsdpLhi(ctx)

    @JvmStatic
    fun fsdpLhia(ctx: DimenCallContext, value: Int): Float = value.fsdpLhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun fsdpPw(ctx: DimenCallContext, value: Int): Float = value.fsdpPw(ctx)

    @JvmStatic
    fun fsdpPwa(ctx: DimenCallContext, value: Int): Float = value.fsdpPwa(ctx)

    @JvmStatic
    fun fsdpPwi(ctx: DimenCallContext, value: Int): Float = value.fsdpPwi(ctx)

    @JvmStatic
    fun fsdpPwia(ctx: DimenCallContext, value: Int): Float = value.fsdpPwia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun fsdpLw(ctx: DimenCallContext, value: Int): Float = value.fsdpLw(ctx)

    @JvmStatic
    fun fsdpLwa(ctx: DimenCallContext, value: Int): Float = value.fsdpLwa(ctx)

    @JvmStatic
    fun fsdpLwi(ctx: DimenCallContext, value: Int): Float = value.fsdpLwi(ctx)

    @JvmStatic
    fun fsdpLwia(ctx: DimenCallContext, value: Int): Float = value.fsdpLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp).
     * PT Resolução rápida para Altura da Tela (hdp).
     */
    @JvmStatic
    fun fhdp(ctx: DimenCallContext, value: Int): Float = value.fhdp(ctx)

    @JvmStatic
    fun fhdpa(ctx: DimenCallContext, value: Int): Float = value.fhdpa(ctx)

    @JvmStatic
    fun fhdpi(ctx: DimenCallContext, value: Int): Float = value.fhdpi(ctx)

    @JvmStatic
    fun fhdpia(ctx: DimenCallContext, value: Int): Float = value.fhdpia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun fhdpLw(ctx: DimenCallContext, value: Int): Float = value.fhdpLw(ctx)

    @JvmStatic
    fun fhdpLwa(ctx: DimenCallContext, value: Int): Float = value.fhdpLwa(ctx)

    @JvmStatic
    fun fhdpLwi(ctx: DimenCallContext, value: Int): Float = value.fhdpLwi(ctx)

    @JvmStatic
    fun fhdpLwia(ctx: DimenCallContext, value: Int): Float = value.fhdpLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun fhdpPw(ctx: DimenCallContext, value: Int): Float = value.fhdpPw(ctx)

    @JvmStatic
    fun fhdpPwa(ctx: DimenCallContext, value: Int): Float = value.fhdpPwa(ctx)

    @JvmStatic
    fun fhdpPwi(ctx: DimenCallContext, value: Int): Float = value.fhdpPwi(ctx)

    @JvmStatic
    fun fhdpPwia(ctx: DimenCallContext, value: Int): Float = value.fhdpPwia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp).
     * PT Resolução rápida para Largura da Tela (wdp).
     */
    @JvmStatic
    fun fwdp(ctx: DimenCallContext, value: Int): Float = value.fwdp(ctx)

    @JvmStatic
    fun fwdpa(ctx: DimenCallContext, value: Int): Float = value.fwdpa(ctx)

    @JvmStatic
    fun fwdpi(ctx: DimenCallContext, value: Int): Float = value.fwdpi(ctx)

    @JvmStatic
    fun fwdpia(ctx: DimenCallContext, value: Int): Float = value.fwdpia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun fwdpLh(ctx: DimenCallContext, value: Int): Float = value.fwdpLh(ctx)

    @JvmStatic
    fun fwdpLha(ctx: DimenCallContext, value: Int): Float = value.fwdpLha(ctx)

    @JvmStatic
    fun fwdpLhi(ctx: DimenCallContext, value: Int): Float = value.fwdpLhi(ctx)

    @JvmStatic
    fun fwdpLhia(ctx: DimenCallContext, value: Int): Float = value.fwdpLhia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun fwdpPh(ctx: DimenCallContext, value: Int): Float = value.fwdpPh(ctx)

    @JvmStatic
    fun fwdpPha(ctx: DimenCallContext, value: Int): Float = value.fwdpPha(ctx)

    @JvmStatic
    fun fwdpPhi(ctx: DimenCallContext, value: Int): Float = value.fwdpPhi(ctx)

    @JvmStatic
    fun fwdpPhia(ctx: DimenCallContext, value: Int): Float = value.fwdpPhia(ctx)

    // EN Qualifier-based conditional dynamic scaling.
    // PT Escalonamento condicional baseado em qualificador.

    /**
     * EN Quick resolution for Smallest Width (swDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fsdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fsdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fhdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fhdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fwdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fwdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

    /**
     * EN Quick resolution for Smallest Width (swDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fsdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fsdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fhdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fhdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fwdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fwdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Generic scaling function for Java.
     * PT Função de escala genérica para Java.
     */
    @JvmStatic
    @JvmOverloads
    fun getDimensionInPx(
        ctx: DimenCallContext,
        qualifier: DpQualifier,
        value: Int,
        inverter: Inverter = Inverter.DEFAULT,
        ignoreMultiWindows: Boolean = false,
        applyAspectRatio: Boolean = false,
        customSensitivityK: Float? = null
    ): Float = value.toDynamicFluidPx(ctx, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Generic DP scaling function for Java.
     * PT Função de escala DP genérica para Java.
     */
    @JvmStatic
    @JvmOverloads
    fun getDimensionInDp(
        ctx: DimenCallContext,
        qualifier: DpQualifier,
        value: Int,
        inverter: Inverter = Inverter.DEFAULT,
        ignoreMultiWindows: Boolean = false,
        applyAspectRatio: Boolean = false,
        customSensitivityK: Float? = null
    ): Float = value.toDynamicFluidDp(ctx, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DimenFluidScaled from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DimenFluidScaled a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DimenFluidScaled = DimenFluidScaled(initialBaseValue.toFloat())

    /**
     * EN Starts the build chain for the custom dimension DimenFluidScaled from a base Float.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DimenFluidScaled = DimenFluidScaled(initialBaseValue)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun fsdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fsdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun fhdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fhdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun fwdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fwdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun fsdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fsdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun fhdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fhdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun fwdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fwdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}