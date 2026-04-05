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
package com.appdimens.dynamic.code.diagonal

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
object DimenDiagonal {

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
    fun dgsdp(ctx: DimenCallContext, value: Int): Float = value.dgsdp(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpa) with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.
     */
    @JvmStatic
    fun dgsdpa(ctx: DimenCallContext, value: Int): Float = value.dgsdpa(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows.
     * PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.
     */
    @JvmStatic
    fun dgsdpi(ctx: DimenCallContext, value: Int): Float = value.dgsdpi(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.
     */
    @JvmStatic
    fun dgsdpia(ctx: DimenCallContext, value: Int): Float = value.dgsdpia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun dgsdpPh(ctx: DimenCallContext, value: Int): Float = value.dgsdpPh(ctx)

    @JvmStatic
    fun dgsdpPha(ctx: DimenCallContext, value: Int): Float = value.dgsdpPha(ctx)

    @JvmStatic
    fun dgsdpPhi(ctx: DimenCallContext, value: Int): Float = value.dgsdpPhi(ctx)

    @JvmStatic
    fun dgsdpPhia(ctx: DimenCallContext, value: Int): Float = value.dgsdpPhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun dgsdpLh(ctx: DimenCallContext, value: Int): Float = value.dgsdpLh(ctx)

    @JvmStatic
    fun dgsdpLha(ctx: DimenCallContext, value: Int): Float = value.dgsdpLha(ctx)

    @JvmStatic
    fun dgsdpLhi(ctx: DimenCallContext, value: Int): Float = value.dgsdpLhi(ctx)

    @JvmStatic
    fun dgsdpLhia(ctx: DimenCallContext, value: Int): Float = value.dgsdpLhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun dgsdpPw(ctx: DimenCallContext, value: Int): Float = value.dgsdpPw(ctx)

    @JvmStatic
    fun dgsdpPwa(ctx: DimenCallContext, value: Int): Float = value.dgsdpPwa(ctx)

    @JvmStatic
    fun dgsdpPwi(ctx: DimenCallContext, value: Int): Float = value.dgsdpPwi(ctx)

    @JvmStatic
    fun dgsdpPwia(ctx: DimenCallContext, value: Int): Float = value.dgsdpPwia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun dgsdpLw(ctx: DimenCallContext, value: Int): Float = value.dgsdpLw(ctx)

    @JvmStatic
    fun dgsdpLwa(ctx: DimenCallContext, value: Int): Float = value.dgsdpLwa(ctx)

    @JvmStatic
    fun dgsdpLwi(ctx: DimenCallContext, value: Int): Float = value.dgsdpLwi(ctx)

    @JvmStatic
    fun dgsdpLwia(ctx: DimenCallContext, value: Int): Float = value.dgsdpLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp).
     * PT Resolução rápida para Altura da Tela (hdp).
     */
    @JvmStatic
    fun dghdp(ctx: DimenCallContext, value: Int): Float = value.dghdp(ctx)

    @JvmStatic
    fun dghdpa(ctx: DimenCallContext, value: Int): Float = value.dghdpa(ctx)

    @JvmStatic
    fun dghdpi(ctx: DimenCallContext, value: Int): Float = value.dghdpi(ctx)

    @JvmStatic
    fun dghdpia(ctx: DimenCallContext, value: Int): Float = value.dghdpia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun dghdpLw(ctx: DimenCallContext, value: Int): Float = value.dghdpLw(ctx)

    @JvmStatic
    fun dghdpLwa(ctx: DimenCallContext, value: Int): Float = value.dghdpLwa(ctx)

    @JvmStatic
    fun dghdpLwi(ctx: DimenCallContext, value: Int): Float = value.dghdpLwi(ctx)

    @JvmStatic
    fun dghdpLwia(ctx: DimenCallContext, value: Int): Float = value.dghdpLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun dghdpPw(ctx: DimenCallContext, value: Int): Float = value.dghdpPw(ctx)

    @JvmStatic
    fun dghdpPwa(ctx: DimenCallContext, value: Int): Float = value.dghdpPwa(ctx)

    @JvmStatic
    fun dghdpPwi(ctx: DimenCallContext, value: Int): Float = value.dghdpPwi(ctx)

    @JvmStatic
    fun dghdpPwia(ctx: DimenCallContext, value: Int): Float = value.dghdpPwia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp).
     * PT Resolução rápida para Largura da Tela (wdp).
     */
    @JvmStatic
    fun dgwdp(ctx: DimenCallContext, value: Int): Float = value.dgwdp(ctx)

    @JvmStatic
    fun dgwdpa(ctx: DimenCallContext, value: Int): Float = value.dgwdpa(ctx)

    @JvmStatic
    fun dgwdpi(ctx: DimenCallContext, value: Int): Float = value.dgwdpi(ctx)

    @JvmStatic
    fun dgwdpia(ctx: DimenCallContext, value: Int): Float = value.dgwdpia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun dgwdpLh(ctx: DimenCallContext, value: Int): Float = value.dgwdpLh(ctx)

    @JvmStatic
    fun dgwdpLha(ctx: DimenCallContext, value: Int): Float = value.dgwdpLha(ctx)

    @JvmStatic
    fun dgwdpLhi(ctx: DimenCallContext, value: Int): Float = value.dgwdpLhi(ctx)

    @JvmStatic
    fun dgwdpLhia(ctx: DimenCallContext, value: Int): Float = value.dgwdpLhia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun dgwdpPh(ctx: DimenCallContext, value: Int): Float = value.dgwdpPh(ctx)

    @JvmStatic
    fun dgwdpPha(ctx: DimenCallContext, value: Int): Float = value.dgwdpPha(ctx)

    @JvmStatic
    fun dgwdpPhi(ctx: DimenCallContext, value: Int): Float = value.dgwdpPhi(ctx)

    @JvmStatic
    fun dgwdpPhia(ctx: DimenCallContext, value: Int): Float = value.dgwdpPhia(ctx)

    // EN Qualifier-based conditional dynamic scaling.
    // PT Escalonamento condicional baseado em qualificador.

    /**
     * EN Quick resolution for Smallest Width (swDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dgsdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgsdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dghdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dghdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dgwdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgwdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

    /**
     * EN Quick resolution for Smallest Width (swDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dgsdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgsdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dghdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dghdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dgwdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgwdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicDiagonalPx(ctx, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicDiagonalDp(ctx, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DimenDiagonalScaled from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DimenDiagonalScaled a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DimenDiagonalScaled = DimenDiagonalScaled(initialBaseValue.toFloat())

    /**
     * EN Starts the build chain for the custom dimension DimenDiagonalScaled from a base Float.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DimenDiagonalScaled = DimenDiagonalScaled(initialBaseValue)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dgsdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgsdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dghdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dghdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dgwdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgwdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dgsdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgsdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dghdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dghdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dgwdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgwdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}