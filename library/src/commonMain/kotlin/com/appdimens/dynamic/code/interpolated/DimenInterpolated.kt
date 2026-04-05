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
package com.appdimens.dynamic.code.interpolated

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
object DimenInterpolated {

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
    fun isdp(ctx: DimenCallContext, value: Int): Float = value.isdp(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpa) with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.
     */
    @JvmStatic
    fun isdpa(ctx: DimenCallContext, value: Int): Float = value.isdpa(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows.
     * PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.
     */
    @JvmStatic
    fun isdpi(ctx: DimenCallContext, value: Int): Float = value.isdpi(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.
     */
    @JvmStatic
    fun isdpia(ctx: DimenCallContext, value: Int): Float = value.isdpia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun isdpPh(ctx: DimenCallContext, value: Int): Float = value.isdpPh(ctx)

    @JvmStatic
    fun isdpPha(ctx: DimenCallContext, value: Int): Float = value.isdpPha(ctx)

    @JvmStatic
    fun isdpPhi(ctx: DimenCallContext, value: Int): Float = value.isdpPhi(ctx)

    @JvmStatic
    fun isdpPhia(ctx: DimenCallContext, value: Int): Float = value.isdpPhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun isdpLh(ctx: DimenCallContext, value: Int): Float = value.isdpLh(ctx)

    @JvmStatic
    fun isdpLha(ctx: DimenCallContext, value: Int): Float = value.isdpLha(ctx)

    @JvmStatic
    fun isdpLhi(ctx: DimenCallContext, value: Int): Float = value.isdpLhi(ctx)

    @JvmStatic
    fun isdpLhia(ctx: DimenCallContext, value: Int): Float = value.isdpLhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun isdpPw(ctx: DimenCallContext, value: Int): Float = value.isdpPw(ctx)

    @JvmStatic
    fun isdpPwa(ctx: DimenCallContext, value: Int): Float = value.isdpPwa(ctx)

    @JvmStatic
    fun isdpPwi(ctx: DimenCallContext, value: Int): Float = value.isdpPwi(ctx)

    @JvmStatic
    fun isdpPwia(ctx: DimenCallContext, value: Int): Float = value.isdpPwia(ctx)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun isdpLw(ctx: DimenCallContext, value: Int): Float = value.isdpLw(ctx)

    @JvmStatic
    fun isdpLwa(ctx: DimenCallContext, value: Int): Float = value.isdpLwa(ctx)

    @JvmStatic
    fun isdpLwi(ctx: DimenCallContext, value: Int): Float = value.isdpLwi(ctx)

    @JvmStatic
    fun isdpLwia(ctx: DimenCallContext, value: Int): Float = value.isdpLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp).
     * PT Resolução rápida para Altura da Tela (hdp).
     */
    @JvmStatic
    fun ihdp(ctx: DimenCallContext, value: Int): Float = value.ihdp(ctx)

    @JvmStatic
    fun ihdpa(ctx: DimenCallContext, value: Int): Float = value.ihdpa(ctx)

    @JvmStatic
    fun ihdpi(ctx: DimenCallContext, value: Int): Float = value.ihdpi(ctx)

    @JvmStatic
    fun ihdpia(ctx: DimenCallContext, value: Int): Float = value.ihdpia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun ihdpLw(ctx: DimenCallContext, value: Int): Float = value.ihdpLw(ctx)

    @JvmStatic
    fun ihdpLwa(ctx: DimenCallContext, value: Int): Float = value.ihdpLwa(ctx)

    @JvmStatic
    fun ihdpLwi(ctx: DimenCallContext, value: Int): Float = value.ihdpLwi(ctx)

    @JvmStatic
    fun ihdpLwia(ctx: DimenCallContext, value: Int): Float = value.ihdpLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun ihdpPw(ctx: DimenCallContext, value: Int): Float = value.ihdpPw(ctx)

    @JvmStatic
    fun ihdpPwa(ctx: DimenCallContext, value: Int): Float = value.ihdpPwa(ctx)

    @JvmStatic
    fun ihdpPwi(ctx: DimenCallContext, value: Int): Float = value.ihdpPwi(ctx)

    @JvmStatic
    fun ihdpPwia(ctx: DimenCallContext, value: Int): Float = value.ihdpPwia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp).
     * PT Resolução rápida para Largura da Tela (wdp).
     */
    @JvmStatic
    fun iwdp(ctx: DimenCallContext, value: Int): Float = value.iwdp(ctx)

    @JvmStatic
    fun iwdpa(ctx: DimenCallContext, value: Int): Float = value.iwdpa(ctx)

    @JvmStatic
    fun iwdpi(ctx: DimenCallContext, value: Int): Float = value.iwdpi(ctx)

    @JvmStatic
    fun iwdpia(ctx: DimenCallContext, value: Int): Float = value.iwdpia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun iwdpLh(ctx: DimenCallContext, value: Int): Float = value.iwdpLh(ctx)

    @JvmStatic
    fun iwdpLha(ctx: DimenCallContext, value: Int): Float = value.iwdpLha(ctx)

    @JvmStatic
    fun iwdpLhi(ctx: DimenCallContext, value: Int): Float = value.iwdpLhi(ctx)

    @JvmStatic
    fun iwdpLhia(ctx: DimenCallContext, value: Int): Float = value.iwdpLhia(ctx)

    /**
     * EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun iwdpPh(ctx: DimenCallContext, value: Int): Float = value.iwdpPh(ctx)

    @JvmStatic
    fun iwdpPha(ctx: DimenCallContext, value: Int): Float = value.iwdpPha(ctx)

    @JvmStatic
    fun iwdpPhi(ctx: DimenCallContext, value: Int): Float = value.iwdpPhi(ctx)

    @JvmStatic
    fun iwdpPhia(ctx: DimenCallContext, value: Int): Float = value.iwdpPhia(ctx)

    // EN Qualifier-based conditional dynamic scaling.
    // PT Escalonamento condicional baseado em qualificador.

    /**
     * EN Quick resolution for Smallest Width (swDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun isdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.isdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ihdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ihdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun iwdpQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.iwdpQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

    /**
     * EN Quick resolution for Smallest Width (swDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun isdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.isdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ihdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ihdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun iwdpScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.iwdpScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicInterpolatedPx(ctx, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicInterpolatedDp(ctx, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DimenInterpolatedScaled from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DimenInterpolatedScaled a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DimenInterpolatedScaled = DimenInterpolatedScaled(initialBaseValue.toFloat())

    /**
     * EN Starts the build chain for the custom dimension DimenInterpolatedScaled from a base Float.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DimenInterpolatedScaled = DimenInterpolatedScaled(initialBaseValue)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun isdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.isdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun ihdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ihdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun iwdpRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.iwdpRotate(ctx, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun isdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.isdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun ihdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ihdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun iwdpMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.iwdpMode(ctx, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}