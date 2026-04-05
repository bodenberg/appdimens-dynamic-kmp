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
package com.appdimens.dynamic.code.density

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
 * Utility object for handling SSP (Scalable Sp) dimensions from Java.
 *
 * PT
 * Objeto utilitário para manipulação de dimensões SSP (Scalable Sp) no Java.
 */
object DimenDensitySp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(ctx: DimenCallContext) {
        DimenCache.init(ctx.cachePersistence!!, ctx.screenMetrics)
    }

    /**
     * EN Quick resolution for Smallest Width (dssp).
     * PT Resolução rápida para Smallest Width (dssp).
     */
    @JvmStatic
    fun dssp(ctx: DimenCallContext, value: Int): Float = value.dssp(ctx)

    @JvmStatic
    fun sspa(ctx: DimenCallContext, value: Int): Float = value.sspa(ctx)

    @JvmStatic
    fun sspi(ctx: DimenCallContext, value: Int): Float = value.sspi(ctx)

    @JvmStatic
    fun sspia(ctx: DimenCallContext, value: Int): Float = value.sspia(ctx)

    /**
     * EN Quick resolution for Smallest Width (dssp), but in portrait orientation it acts as Screen Height (dhsp).
     */
    @JvmStatic
    fun sspPh(ctx: DimenCallContext, value: Int): Float = value.sspPh(ctx)

    @JvmStatic
    fun sspPha(ctx: DimenCallContext, value: Int): Float = value.sspPha(ctx)

    @JvmStatic
    fun sspPhi(ctx: DimenCallContext, value: Int): Float = value.sspPhi(ctx)

    @JvmStatic
    fun sspPhia(ctx: DimenCallContext, value: Int): Float = value.sspPhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (dssp), but in landscape orientation it acts as Screen Height (dhsp).
     */
    @JvmStatic
    fun sspLh(ctx: DimenCallContext, value: Int): Float = value.sspLh(ctx)

    @JvmStatic
    fun sspLha(ctx: DimenCallContext, value: Int): Float = value.sspLha(ctx)

    @JvmStatic
    fun sspLhi(ctx: DimenCallContext, value: Int): Float = value.sspLhi(ctx)

    @JvmStatic
    fun sspLhia(ctx: DimenCallContext, value: Int): Float = value.sspLhia(ctx)

    /**
     * EN Quick resolution for Smallest Width (dssp), but in portrait orientation it acts as Screen Width (dwsp).
     */
    @JvmStatic
    fun sspPw(ctx: DimenCallContext, value: Int): Float = value.sspPw(ctx)

    @JvmStatic
    fun sspPwa(ctx: DimenCallContext, value: Int): Float = value.sspPwa(ctx)

    @JvmStatic
    fun sspPwi(ctx: DimenCallContext, value: Int): Float = value.sspPwi(ctx)

    @JvmStatic
    fun sspPwia(ctx: DimenCallContext, value: Int): Float = value.sspPwia(ctx)

    /**
     * EN Quick resolution for Smallest Width (dssp), but in landscape orientation it acts as Screen Width (dwsp).
     */
    @JvmStatic
    fun sspLw(ctx: DimenCallContext, value: Int): Float = value.sspLw(ctx)

    @JvmStatic
    fun sspLwa(ctx: DimenCallContext, value: Int): Float = value.sspLwa(ctx)

    @JvmStatic
    fun sspLwi(ctx: DimenCallContext, value: Int): Float = value.sspLwi(ctx)

    @JvmStatic
    fun sspLwia(ctx: DimenCallContext, value: Int): Float = value.sspLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (dhsp).
     * PT Resolução rápida para Altura da Tela (dhsp).
     */
    @JvmStatic
    fun dhsp(ctx: DimenCallContext, value: Int): Float = value.dhsp(ctx)

    @JvmStatic
    fun hspa(ctx: DimenCallContext, value: Int): Float = value.hspa(ctx)

    @JvmStatic
    fun hspi(ctx: DimenCallContext, value: Int): Float = value.hspi(ctx)

    @JvmStatic
    fun hspia(ctx: DimenCallContext, value: Int): Float = value.hspia(ctx)

    /**
     * EN Quick resolution for Screen Height (dhsp), but in landscape orientation it acts as Screen Width (dwsp).
     */
    @JvmStatic
    fun hspLw(ctx: DimenCallContext, value: Int): Float = value.hspLw(ctx)

    @JvmStatic
    fun hspLwa(ctx: DimenCallContext, value: Int): Float = value.hspLwa(ctx)

    @JvmStatic
    fun hspLwi(ctx: DimenCallContext, value: Int): Float = value.hspLwi(ctx)

    @JvmStatic
    fun hspLwia(ctx: DimenCallContext, value: Int): Float = value.hspLwia(ctx)

    /**
     * EN Quick resolution for Screen Height (dhsp), but in portrait orientation it acts as Screen Width (dwsp).
     */
    @JvmStatic
    fun hspPw(ctx: DimenCallContext, value: Int): Float = value.hspPw(ctx)

    @JvmStatic
    fun hspPwa(ctx: DimenCallContext, value: Int): Float = value.hspPwa(ctx)

    @JvmStatic
    fun hspPwi(ctx: DimenCallContext, value: Int): Float = value.hspPwi(ctx)

    @JvmStatic
    fun hspPwia(ctx: DimenCallContext, value: Int): Float = value.hspPwia(ctx)

    /**
     * EN Quick resolution for Screen Width (dwsp).
     * PT Resolução rápida para Largura da Tela (dwsp).
     */
    @JvmStatic
    fun dwsp(ctx: DimenCallContext, value: Int): Float = value.dwsp(ctx)

    @JvmStatic
    fun wspa(ctx: DimenCallContext, value: Int): Float = value.wspa(ctx)

    @JvmStatic
    fun wspi(ctx: DimenCallContext, value: Int): Float = value.wspi(ctx)

    @JvmStatic
    fun wspia(ctx: DimenCallContext, value: Int): Float = value.wspia(ctx)

    /**
     * EN Quick resolution for Screen Width (dwsp), but in landscape orientation it acts as Screen Height (dhsp).
     */
    @JvmStatic
    fun wspLh(ctx: DimenCallContext, value: Int): Float = value.wspLh(ctx)

    @JvmStatic
    fun wspLha(ctx: DimenCallContext, value: Int): Float = value.wspLha(ctx)

    @JvmStatic
    fun wspLhi(ctx: DimenCallContext, value: Int): Float = value.wspLhi(ctx)

    @JvmStatic
    fun wspLhia(ctx: DimenCallContext, value: Int): Float = value.wspLhia(ctx)

    /**
     * EN Quick resolution for Screen Width (dwsp), but in portrait orientation it acts as Screen Height (dhsp).
     */
    @JvmStatic
    fun wspPh(ctx: DimenCallContext, value: Int): Float = value.wspPh(ctx)

    @JvmStatic
    fun wspPha(ctx: DimenCallContext, value: Int): Float = value.wspPha(ctx)

    @JvmStatic
    fun wspPhi(ctx: DimenCallContext, value: Int): Float = value.wspPhi(ctx)

    @JvmStatic
    fun wspPhia(ctx: DimenCallContext, value: Int): Float = value.wspPhia(ctx)


    // EN WITHOUT FONT SCALE variants
    // PT Variantes SEM ESCALA DE FONTE

    /**
     * EN Quick resolution for Smallest Width (sei) - Without font scale.
     * PT Resolução rápida para Smallest Width (sei) - Sem escala de fonte.
     */
    @JvmStatic
    fun sei(ctx: DimenCallContext, value: Int): Float = value.sei(ctx)

    @JvmStatic
    fun seia(ctx: DimenCallContext, value: Int): Float = value.seia(ctx)

    @JvmStatic
    fun seii(ctx: DimenCallContext, value: Int): Float = value.seii(ctx)

    @JvmStatic
    fun seiia(ctx: DimenCallContext, value: Int): Float = value.seiia(ctx)

    /**
     * EN Quick resolution for Smallest Width without font scale, portrait is Screen Height.
     */
    @JvmStatic
    fun semPh(ctx: DimenCallContext, value: Int): Float = value.semPh(ctx)

    @JvmStatic
    fun semPha(ctx: DimenCallContext, value: Int): Float = value.semPha(ctx)

    @JvmStatic
    fun semPhi(ctx: DimenCallContext, value: Int): Float = value.semPhi(ctx)

    @JvmStatic
    fun semPhia(ctx: DimenCallContext, value: Int): Float = value.semPhia(ctx)

    /**
     * EN Quick resolution for Smallest Width without font scale, landscape is Screen Height.
     */
    @JvmStatic
    fun semLh(ctx: DimenCallContext, value: Int): Float = value.semLh(ctx)

    @JvmStatic
    fun semLha(ctx: DimenCallContext, value: Int): Float = value.semLha(ctx)

    @JvmStatic
    fun semLhi(ctx: DimenCallContext, value: Int): Float = value.semLhi(ctx)

    @JvmStatic
    fun semLhia(ctx: DimenCallContext, value: Int): Float = value.semLhia(ctx)

    /**
     * EN Quick resolution for Smallest Width without font scale, portrait is Screen Width.
     */
    @JvmStatic
    fun semPw(ctx: DimenCallContext, value: Int): Float = value.semPw(ctx)

    @JvmStatic
    fun semPwa(ctx: DimenCallContext, value: Int): Float = value.semPwa(ctx)

    @JvmStatic
    fun semPwi(ctx: DimenCallContext, value: Int): Float = value.semPwi(ctx)

    @JvmStatic
    fun semPwia(ctx: DimenCallContext, value: Int): Float = value.semPwia(ctx)

    /**
     * EN Quick resolution for Smallest Width without font scale, landscape is Screen Width.
     */
    @JvmStatic
    fun semLw(ctx: DimenCallContext, value: Int): Float = value.semLw(ctx)

    @JvmStatic
    fun semLwa(ctx: DimenCallContext, value: Int): Float = value.semLwa(ctx)

    @JvmStatic
    fun semLwi(ctx: DimenCallContext, value: Int): Float = value.semLwi(ctx)

    @JvmStatic
    fun semLwia(ctx: DimenCallContext, value: Int): Float = value.semLwia(ctx)

    /**
     * EN Quick resolution for Screen Height without font scale.
     */
    @JvmStatic
    fun hei(ctx: DimenCallContext, value: Int): Float = value.hei(ctx)

    @JvmStatic
    fun heia(ctx: DimenCallContext, value: Int): Float = value.heia(ctx)

    @JvmStatic
    fun heii(ctx: DimenCallContext, value: Int): Float = value.heii(ctx)

    @JvmStatic
    fun heiia(ctx: DimenCallContext, value: Int): Float = value.heiia(ctx)

    /**
     * EN Quick resolution for Screen Height without font scale, landscape is Screen Width.
     */
    @JvmStatic
    fun hemLw(ctx: DimenCallContext, value: Int): Float = value.hemLw(ctx)

    @JvmStatic
    fun hemLwa(ctx: DimenCallContext, value: Int): Float = value.hemLwa(ctx)

    @JvmStatic
    fun hemLwi(ctx: DimenCallContext, value: Int): Float = value.hemLwi(ctx)

    @JvmStatic
    fun hemLwia(ctx: DimenCallContext, value: Int): Float = value.hemLwia(ctx)

    /**
     * EN Quick resolution for Screen Height without font scale, portrait is Screen Width.
     */
    @JvmStatic
    fun hemPw(ctx: DimenCallContext, value: Int): Float = value.hemPw(ctx)

    @JvmStatic
    fun hemPwa(ctx: DimenCallContext, value: Int): Float = value.hemPwa(ctx)

    @JvmStatic
    fun hemPwi(ctx: DimenCallContext, value: Int): Float = value.hemPwi(ctx)

    @JvmStatic
    fun hemPwia(ctx: DimenCallContext, value: Int): Float = value.hemPwia(ctx)

    /**
     * EN Quick resolution for Screen Width without font scale.
     */
    @JvmStatic
    fun wei(ctx: DimenCallContext, value: Int): Float = value.wei(ctx)

    @JvmStatic
    fun weia(ctx: DimenCallContext, value: Int): Float = value.weia(ctx)

    @JvmStatic
    fun weii(ctx: DimenCallContext, value: Int): Float = value.weii(ctx)

    @JvmStatic
    fun weiia(ctx: DimenCallContext, value: Int): Float = value.weiia(ctx)

    /**
     * EN Quick resolution for Screen Width without font scale, landscape is Screen Height.
     */
    @JvmStatic
    fun wemLh(ctx: DimenCallContext, value: Int): Float = value.wemLh(ctx)

    @JvmStatic
    fun wemLha(ctx: DimenCallContext, value: Int): Float = value.wemLha(ctx)

    @JvmStatic
    fun wemLhi(ctx: DimenCallContext, value: Int): Float = value.wemLhi(ctx)

    @JvmStatic
    fun wemLhia(ctx: DimenCallContext, value: Int): Float = value.wemLhia(ctx)

    /**
     * EN Quick resolution for Screen Width without font scale, portrait is Screen Height.
     */
    @JvmStatic
    fun wemPh(ctx: DimenCallContext, value: Int): Float = value.wemPh(ctx)

    @JvmStatic
    fun wemPha(ctx: DimenCallContext, value: Int): Float = value.wemPha(ctx)

    @JvmStatic
    fun wemPhi(ctx: DimenCallContext, value: Int): Float = value.wemPhi(ctx)

    @JvmStatic
    fun wemPhia(ctx: DimenCallContext, value: Int): Float = value.wemPhia(ctx)

    /**
     * EN Generic scaling function for Java (PX).
     * PT Função de escala genérica para Java (PX).
     */
    @JvmStatic
    @JvmOverloads
    fun getDimensionInPx(
        ctx: DimenCallContext,
        qualifier: DpQualifier,
        value: Int,
        fontScale: Boolean = true,
        inverter: Inverter = Inverter.DEFAULT,
        ignoreMultiWindows: Boolean = false,
        applyAspectRatio: Boolean = false,
        customSensitivityK: Float? = null
    ): Float = value.toDynamicDensitySpPx(ctx, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Generic scaling function for Java (SP value).
     * PT Função de escala genérica para Java (valor SP).
     */
    @JvmStatic
    @JvmOverloads
    fun getDimensionInSp(
        ctx: DimenCallContext,
        qualifier: DpQualifier,
        value: Int,
        fontScale: Boolean = true,
        inverter: Inverter = Inverter.DEFAULT,
        ignoreMultiWindows: Boolean = false,
        applyAspectRatio: Boolean = false,
        customSensitivityK: Float? = null
    ): Float = value.toDynamicDensitySp(ctx, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DensitySp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DensitySp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DensitySp = DensitySp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension DensitySp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada DensitySp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DensitySp = DensitySp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun sspQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sspQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun hspQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hspQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun wspQualifier(ctx: DimenCallContext, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wspQualifier(ctx, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun sspScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sspScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun hspScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hspScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun wspScreen(ctx: DimenCallContext, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wspScreen(ctx, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (dssp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun sspRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sspRotate(ctx, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (dhsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun hspRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hspRotate(ctx, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (dwsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun wspRotate(ctx: DimenCallContext, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wspRotate(ctx, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (dssp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun sspMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sspMode(ctx, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (dhsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun hspMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hspMode(ctx, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (dwsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun wspMode(ctx: DimenCallContext, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wspMode(ctx, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}