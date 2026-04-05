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
package com.appdimens.dynamic.code.auto

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
object DimenAuto {

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
    fun asdp(ctx: DimenCallContext, value: Int): Float = value.asdp(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpa) with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.
     */
    @JvmStatic
    fun asdpa(ctx: DimenCallContext, value: Int): Float = value.asdpa(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows.
     * PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.
     */
    @JvmStatic
    fun asdpi(ctx: DimenCallContext, value: Int): Float = value.asdpi(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.
     */
    @JvmStatic
    fun asdpia(ctx: DimenCallContext, value: Int): Float = value.asdpia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun asdpPh(ctx: DimenCallContext, value: Int): Float = value.asdpPh(ctx)

    @JvmStatic
    fun asdpPha(ctx: DimenCallContext, value: Int): Float = value.asdpPha(ctx)

    @JvmStatic
    fun asdpPhi(ctx: DimenCallContext, value: Int): Float = value.asdpPhi(ctx)

    @JvmStatic
    fun asdpPhia(ctx: DimenCallContext, value: Int): Float = value.asdpPhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun asdpLh(ctx: DimenCallContext, value: Int): Float = value.asdpLh(ctx)

    @JvmStatic
    fun asdpLha(ctx: DimenCallContext, value: Int): Float = value.asdpLha(ctx)

    @JvmStatic
    fun asdpLhi(ctx: DimenCallContext, value: Int): Float = value.asdpLhi(ctx)

    @JvmStatic
    fun asdpLhia(ctx: DimenCallContext, value: Int): Float = value.asdpLhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun asdpPw(ctx: DimenCallContext, value: Int): Float = value.asdpPw(ctx)

    @JvmStatic
    fun asdpPwa(ctx: DimenCallContext, value: Int): Float = value.asdpPwa(ctx)

    @JvmStatic
    fun asdpPwi(ctx: DimenCallContext, value: Int): Float = value.asdpPwi(ctx)

    @JvmStatic
    fun asdpPwia(ctx: DimenCallContext, value: Int): Float = value.asdpPwia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun asdpLw(ctx: DimenCallContext, value: Int): Float = value.asdpLw(ctx)

    @JvmStatic
    fun asdpLwa(ctx: DimenCallContext, value: Int): Float = value.asdpLwa(ctx)

    @JvmStatic
    fun asdpLwi(ctx: DimenCallContext, value: Int): Float = value.asdpLwi(ctx)

    @JvmStatic
    fun asdpLwia(ctx: DimenCallContext, value: Int): Float = value.asdpLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp).
     * PT Resolução rápida para Altura da Tela (hdp).
     */
    @JvmStatic
    fun ahdp(ctx: DimenCallContext, value: Int): Float = value.ahdp(ctx)

    @JvmStatic
    fun ahdpa(ctx: DimenCallContext, value: Int): Float = value.ahdpa(ctx)

    @JvmStatic
    fun ahdpi(ctx: DimenCallContext, value: Int): Float = value.ahdpi(ctx)

    @JvmStatic
    fun ahdpia(ctx: DimenCallContext, value: Int): Float = value.ahdpia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun ahdpLw(ctx: DimenCallContext, value: Int): Float = value.ahdpLw(ctx)

    @JvmStatic
    fun ahdpLwa(ctx: DimenCallContext, value: Int): Float = value.ahdpLwa(ctx)

    @JvmStatic
    fun ahdpLwi(ctx: DimenCallContext, value: Int): Float = value.ahdpLwi(ctx)

    @JvmStatic
    fun ahdpLwia(ctx: DimenCallContext, value: Int): Float = value.ahdpLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun ahdpPw(ctx: DimenCallContext, value: Int): Float = value.ahdpPw(ctx)

    @JvmStatic
    fun ahdpPwa(ctx: DimenCallContext, value: Int): Float = value.ahdpPwa(ctx)

    @JvmStatic
    fun ahdpPwi(ctx: DimenCallContext, value: Int): Float = value.ahdpPwi(ctx)

    @JvmStatic
    fun ahdpPwia(ctx: DimenCallContext, value: Int): Float = value.ahdpPwia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp).
     * PT Resolução rápida para Largura da Tela (wdp).
     */
    @JvmStatic
    fun awdp(ctx: DimenCallContext, value: Int): Float = value.awdp(ctx)

    @JvmStatic
    fun awdpa(ctx: DimenCallContext, value: Int): Float = value.awdpa(ctx)

    @JvmStatic
    fun awdpi(ctx: DimenCallContext, value: Int): Float = value.awdpi(ctx)

    @JvmStatic
    fun awdpia(ctx: DimenCallContext, value: Int): Float = value.awdpia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun awdpLh(ctx: DimenCallContext, value: Int): Float = value.awdpLh(ctx)

    @JvmStatic
    fun awdpLha(ctx: DimenCallContext, value: Int): Float = value.awdpLha(ctx)

    @JvmStatic
    fun awdpLhi(ctx: DimenCallContext, value: Int): Float = value.awdpLhi(ctx)

    @JvmStatic
    fun awdpLhia(ctx: DimenCallContext, value: Int): Float = value.awdpLhia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun awdpPh(ctx: DimenCallContext, value: Int): Float = value.awdpPh(ctx)

    @JvmStatic
    fun awdpPha(ctx: DimenCallContext, value: Int): Float = value.awdpPha(ctx)

    @JvmStatic
    fun awdpPhi(ctx: DimenCallContext, value: Int): Float = value.awdpPhi(ctx)

    @JvmStatic
    fun awdpPhia(ctx: DimenCallContext, value: Int): Float = value.awdpPhia(ctx)

    // EN Qualifier-based conditional dynamic scaling.
    // PT Escalonamento condicional baseado em qualificador.

    /**
     * EN Quick resolution for Smallest Width (swDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun asdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.asdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ahdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ahdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun awdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.awdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

    /**
     * EN Quick resolution for Smallest Width (swDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun asdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.asdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ahdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ahdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun awdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.awdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicAutoPx(ctx, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicAutoDp(ctx, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DimenAutoScaled from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DimenAutoScaled a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DimenAutoScaled = DimenAutoScaled(initialBaseValue.toFloat())

    /**
     * EN Starts the build chain for the custom dimension DimenAutoScaled from a base Float.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DimenAutoScaled = DimenAutoScaled(initialBaseValue)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun asdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.asdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun ahdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ahdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun awdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.awdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun asdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.asdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun ahdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ahdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun awdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.awdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}