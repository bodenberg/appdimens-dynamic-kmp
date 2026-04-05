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
package com.appdimens.dynamic.code.perimeter

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
object DimenPerimeter {

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
    fun prsdp(ctx: DimenCallContext, value: Int): Float = value.prsdp(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpa) with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.
     */
    @JvmStatic
    fun prsdpa(ctx: DimenCallContext, value: Int): Float = value.prsdpa(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows.
     * PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.
     */
    @JvmStatic
    fun prsdpi(ctx: DimenCallContext, value: Int): Float = value.prsdpi(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.
     */
    @JvmStatic
    fun prsdpia(ctx: DimenCallContext, value: Int): Float = value.prsdpia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun prsdpPh(ctx: DimenCallContext, value: Int): Float = value.prsdpPh(ctx)

    @JvmStatic
    fun prsdpPha(ctx: DimenCallContext, value: Int): Float = value.prsdpPha(ctx)

    @JvmStatic
    fun prsdpPhi(ctx: DimenCallContext, value: Int): Float = value.prsdpPhi(ctx)

    @JvmStatic
    fun prsdpPhia(ctx: DimenCallContext, value: Int): Float = value.prsdpPhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun prsdpLh(ctx: DimenCallContext, value: Int): Float = value.prsdpLh(ctx)

    @JvmStatic
    fun prsdpLha(ctx: DimenCallContext, value: Int): Float = value.prsdpLha(ctx)

    @JvmStatic
    fun prsdpLhi(ctx: DimenCallContext, value: Int): Float = value.prsdpLhi(ctx)

    @JvmStatic
    fun prsdpLhia(ctx: DimenCallContext, value: Int): Float = value.prsdpLhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun prsdpPw(ctx: DimenCallContext, value: Int): Float = value.prsdpPw(ctx)

    @JvmStatic
    fun prsdpPwa(ctx: DimenCallContext, value: Int): Float = value.prsdpPwa(ctx)

    @JvmStatic
    fun prsdpPwi(ctx: DimenCallContext, value: Int): Float = value.prsdpPwi(ctx)

    @JvmStatic
    fun prsdpPwia(ctx: DimenCallContext, value: Int): Float = value.prsdpPwia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun prsdpLw(ctx: DimenCallContext, value: Int): Float = value.prsdpLw(ctx)

    @JvmStatic
    fun prsdpLwa(ctx: DimenCallContext, value: Int): Float = value.prsdpLwa(ctx)

    @JvmStatic
    fun prsdpLwi(ctx: DimenCallContext, value: Int): Float = value.prsdpLwi(ctx)

    @JvmStatic
    fun prsdpLwia(ctx: DimenCallContext, value: Int): Float = value.prsdpLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp).
     * PT Resolução rápida para Altura da Tela (hdp).
     */
    @JvmStatic
    fun prhdp(ctx: DimenCallContext, value: Int): Float = value.prhdp(ctx)

    @JvmStatic
    fun prhdpa(ctx: DimenCallContext, value: Int): Float = value.prhdpa(ctx)

    @JvmStatic
    fun prhdpi(ctx: DimenCallContext, value: Int): Float = value.prhdpi(ctx)

    @JvmStatic
    fun prhdpia(ctx: DimenCallContext, value: Int): Float = value.prhdpia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun prhdpLw(ctx: DimenCallContext, value: Int): Float = value.prhdpLw(ctx)

    @JvmStatic
    fun prhdpLwa(ctx: DimenCallContext, value: Int): Float = value.prhdpLwa(ctx)

    @JvmStatic
    fun prhdpLwi(ctx: DimenCallContext, value: Int): Float = value.prhdpLwi(ctx)

    @JvmStatic
    fun prhdpLwia(ctx: DimenCallContext, value: Int): Float = value.prhdpLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun prhdpPw(ctx: DimenCallContext, value: Int): Float = value.prhdpPw(ctx)

    @JvmStatic
    fun prhdpPwa(ctx: DimenCallContext, value: Int): Float = value.prhdpPwa(ctx)

    @JvmStatic
    fun prhdpPwi(ctx: DimenCallContext, value: Int): Float = value.prhdpPwi(ctx)

    @JvmStatic
    fun prhdpPwia(ctx: DimenCallContext, value: Int): Float = value.prhdpPwia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp).
     * PT Resolução rápida para Largura da Tela (wdp).
     */
    @JvmStatic
    fun prwdp(ctx: DimenCallContext, value: Int): Float = value.prwdp(ctx)

    @JvmStatic
    fun prwdpa(ctx: DimenCallContext, value: Int): Float = value.prwdpa(ctx)

    @JvmStatic
    fun prwdpi(ctx: DimenCallContext, value: Int): Float = value.prwdpi(ctx)

    @JvmStatic
    fun prwdpia(ctx: DimenCallContext, value: Int): Float = value.prwdpia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun prwdpLh(ctx: DimenCallContext, value: Int): Float = value.prwdpLh(ctx)

    @JvmStatic
    fun prwdpLha(ctx: DimenCallContext, value: Int): Float = value.prwdpLha(ctx)

    @JvmStatic
    fun prwdpLhi(ctx: DimenCallContext, value: Int): Float = value.prwdpLhi(ctx)

    @JvmStatic
    fun prwdpLhia(ctx: DimenCallContext, value: Int): Float = value.prwdpLhia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun prwdpPh(ctx: DimenCallContext, value: Int): Float = value.prwdpPh(ctx)

    @JvmStatic
    fun prwdpPha(ctx: DimenCallContext, value: Int): Float = value.prwdpPha(ctx)

    @JvmStatic
    fun prwdpPhi(ctx: DimenCallContext, value: Int): Float = value.prwdpPhi(ctx)

    @JvmStatic
    fun prwdpPhia(ctx: DimenCallContext, value: Int): Float = value.prwdpPhia(ctx)

    // EN Qualifier-based conditional dynamic scaling.
    // PT Escalonamento condicional baseado em qualificador.

    /**
     * EN Quick resolution for Smallest Width (swDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prsdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prsdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prhdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prhdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prwdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prwdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

    /**
     * EN Quick resolution for Smallest Width (swDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prsdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prsdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prhdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prhdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prwdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prwdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicPerimeterPx(ctx, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicPerimeterDp(ctx, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DimenPerimeterScaled from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DimenPerimeterScaled a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DimenPerimeterScaled = DimenPerimeterScaled(initialBaseValue.toFloat())

    /**
     * EN Starts the build chain for the custom dimension DimenPerimeterScaled from a base Float.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DimenPerimeterScaled = DimenPerimeterScaled(initialBaseValue)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun prsdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prsdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun prhdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prhdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun prwdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prwdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun prsdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prsdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun prhdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prhdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun prwdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prwdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}