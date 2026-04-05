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
package com.appdimens.dynamic.code.logarithmic

import com.appdimens.dynamic.platform.DimenCallContext
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import com.appdimens.dynamic.common.ScreenOrientation


import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCalculationPlumbing
import com.appdimens.dynamic.core.DimenCache

// EN Rotation facilitator extensions for non-Compose (Views).
// PT Extensões facilitadoras para rotação em não-Compose (Views).

private const val BASE_RATIO_STEP = 300f

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device is in the specified [orientation],
 * it uses [rotationValue] scaled with the given [finalQualifierResolver].
 */
@JvmOverloads
fun Number.logsdpRotate(
    ctx: DimenCallContext,
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val metrics = ctx.screenMetrics
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> metrics.orientation == ScreenOrientation.LANDSCAPE
        Orientation.PORTRAIT -> metrics.orientation == ScreenOrientation.PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@JvmOverloads
fun Number.loghdpRotate(
    ctx: DimenCallContext,
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT,
    orientation: Orientation = Orientation.LANDSCAPE,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val metrics = ctx.screenMetrics
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> metrics.orientation == ScreenOrientation.LANDSCAPE
        Orientation.PORTRAIT -> metrics.orientation == ScreenOrientation.PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@JvmOverloads
fun Number.logwdpRotate(
    ctx: DimenCallContext,
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val metrics = ctx.screenMetrics
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> metrics.orientation == ScreenOrientation.LANDSCAPE
        Orientation.PORTRAIT -> metrics.orientation == ScreenOrientation.PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN UiModeType facilitator extensions for non-Compose.
// PT Extensões facilitadoras para UiModeType em não-Compose.

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device matches the specified [uiModeType],
 * it uses [modeValue] instead.
 */
@JvmOverloads
fun Number.logsdpMode(
    ctx: DimenCallContext,
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val currentUiModeType = ctx.currentUiMode() // In non-Compose we could try to find activity but usually context is enough
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@JvmOverloads
fun Number.loghdpMode(
    ctx: DimenCallContext,
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val currentUiModeType = ctx.currentUiMode()
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver ?: DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@JvmOverloads
fun Number.logwdpMode(
    ctx: DimenCallContext,
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val currentUiModeType = ctx.currentUiMode()
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver ?: DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}


/**
 * EN
 * Gets the actual value from the Configuration for the given DpQualifier.
 *
 * PT
 * Obtém o valor real da configuração (Configuration) para o DpQualifier dado.
 *
 * @param qualifier The type of qualifier (SMALL_WIDTH, HEIGHT, WIDTH).
 * @param configuration The current resource configuration.
 * @return The numeric value (in Dp) of the screen metric.
 */
internal fun getQualifierValue(qualifier: DpQualifier, metrics: ScreenMetricsSnapshot): Float {
    return when (qualifier) {
        DpQualifier.SMALL_WIDTH -> metrics.smallestWidthDp.toFloat()
        DpQualifier.HEIGHT -> metrics.heightDp.toFloat()
        DpQualifier.WIDTH -> metrics.widthDp.toFloat()
    }
}

// EN Standard Android extensions for quick dynamic scaling (View-based).
// PT Extensões Android padrão para dimensionamento dinâmico rápido (baseado em Views).

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Smallest Width (swDP)**.
 * Usage example: `16.sdp(ctx)`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Exemplo de uso: `16.sdp(ctx)`.
 */
fun Number.logsdp(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH)
fun Number.logsdpa(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
fun Number.logsdpi(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
fun Number.logsdpia(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sdpPh(ctx)`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação retrato atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.sdpPh(ctx)`.
 */
fun Number.logsdpPh(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
fun Number.logsdpPha(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
fun Number.logsdpPhi(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
fun Number.logsdpPhia(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sdpLh(ctx)`.
 */
fun Number.logsdpLh(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
fun Number.logsdpLha(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
fun Number.logsdpLhi(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
fun Number.logsdpLhia(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sdpPw(ctx)`.
 */
fun Number.logsdpPw(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
fun Number.logsdpPwa(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
fun Number.logsdpPwi(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
fun Number.logsdpPwia(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sdpLw(ctx)`.
 */
fun Number.logsdpLw(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
fun Number.logsdpLwa(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
fun Number.logsdpLwi(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
fun Number.logsdpLwia(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**.
 * Usage example: `32.hdp(ctx)`.
 */
fun Number.loghdp(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT)
fun Number.loghdpa(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, applyAspectRatio = true)
fun Number.loghdpi(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, ignoreMultiWindows = true)
fun Number.loghdpia(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hdpLw(ctx)`.
 */
fun Number.loghdpLw(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, Inverter.PH_TO_LW)
fun Number.loghdpLwa(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
fun Number.loghdpLwi(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
fun Number.loghdpLwia(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hdpPw(ctx)`.
 */
fun Number.loghdpPw(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, Inverter.LH_TO_PW)
fun Number.loghdpPwa(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
fun Number.loghdpPwi(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
fun Number.loghdpPwia(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**.
 * Usage example: `100.wdp(ctx)`.
 */
fun Number.logwdp(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH)
fun Number.logwdpa(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, applyAspectRatio = true)
fun Number.logwdpi(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, ignoreMultiWindows = true)
fun Number.logwdpia(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wdpLh(ctx)`.
 */
fun Number.logwdpLh(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, Inverter.PW_TO_LH)
fun Number.logwdpLha(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
fun Number.logwdpLhi(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
fun Number.logwdpLhia(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wdpPh(ctx)`.
 */
fun Number.logwdpPh(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, Inverter.LW_TO_PH)
fun Number.logwdpPha(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
fun Number.logwdpPhi(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
fun Number.logwdpPhia(ctx: DimenCallContext): Float = this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

// EN Qualifier-based conditional dynamic scaling.
// PT Escalonamento condicional baseado em qualificador.

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
fun Number.logsdpQualifier(ctx: DimenCallContext, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val metrics = ctx.screenMetrics
    val qualifierMatch = getQualifierValue(qualifierType, metrics) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        qualifiedValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
fun Number.loghdpQualifier(ctx: DimenCallContext, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val metrics = ctx.screenMetrics
    val qualifierMatch = getQualifierValue(qualifierType, metrics) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        qualifiedValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver ?: DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
fun Number.logwdpQualifier(ctx: DimenCallContext, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val metrics = ctx.screenMetrics
    val qualifierMatch = getQualifierValue(qualifierType, metrics) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        qualifiedValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver ?: DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN UiModeType + DpQualifier combined facilitator extensions.
// PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
fun Number.logsdpScreen(ctx: DimenCallContext, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val metrics = ctx.screenMetrics
    val currentUiModeType = ctx.currentUiMode()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, metrics) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
fun Number.loghdpScreen(ctx: DimenCallContext, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val metrics = ctx.screenMetrics
    val currentUiModeType = ctx.currentUiMode()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, metrics) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver ?: DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
fun Number.logwdpScreen(ctx: DimenCallContext, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val metrics = ctx.screenMetrics
    val currentUiModeType = ctx.currentUiMode()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, metrics) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicLogarithmicPx(ctx, finalQualifierResolver ?: DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicLogarithmicPx(ctx, DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN Dynamic scaling functions (Resource-based).
// PT Funções de dimensionamento dinâmico (baseadas em recursos).

/**
 * EN
 * Converts a [Number] (base Dp value) into a dynamically scaled pixel [Float] for View-based (non-Compose) code.
 *
 * The scaling logic:
 * 1. Builds a 64-bit packed cache key from all dimension parameters.
 * 2. **If [enableCache] is `true`** (default): checks [DimenCache] first. On a hit, returns the
 *    cached pixel value immediately. On a miss, calls [calculateLogarithmicDp] and converts Dp→px via
 *    `scaledDp * displayMetrics.density` (equivalent to [android.util.TypedValue.applyDimension]
 *    for `COMPLEX_UNIT_DIP`), then stores the result.
 * 3. **If [enableCache] is `false`**: computes directly via [calculateLogarithmicDp], bypassing cache.
 *
 * > ⚠️ **Bypass note**: when [applyAspectRatio] is `false` and [qualifier] is `SMALL_WIDTH`
 * > with `DEFAULT` inverter, the [DimenCache.getOrPut] call internally bypasses the hash lookup
 * > because a raw multiply (~2 ns) is faster than the cache access (~5 ns). Calls with these
 * > parameters measure raw math performance, NOT cache throughput.
 *
 * **Bulk resolution:** for many keys in one pass, prefer building [LongArray] keys with
 * [DimenCache.buildKey] and [DimenCache.getBatch]. **Early init:** call [DimenSdp.warmupCache]
 * (or [DimenSsp.warmupCache]) once with your [DimenCallContext] so persistence/DataStore
 * work does not land on the first hot-frame call.
 *
 * PT
 * Converte um [Number] (valor Dp base) em um [Float] em pixels dinamicamente escalado para código View-based.
 *
 * A lógica de escalonamento:
 * 1. Constrói uma chave de cache de 64 bits a partir de todos os parâmetros da dimensão.
 * 2. **Se [enableCache] for `true`** (padrão): consulta o [DimenCache] primeiro. No acerto,
 *    retorna o valor em pixels cacheado; no miss, calcula via [calculateLogarithmicDp] e armazena.
 * 3. **Se [enableCache] for `false`**: calcula diretamente via [calculateLogarithmicDp].
 *
 * @param context            Android [DimenCallContext] for configuration and density access.
 * @param qualifier          Screen dimension qualifier: [com.appdimens.dynamic.common.DpQualifier.SMALL_WIDTH],
 *                           [com.appdimens.dynamic.common.DpQualifier.HEIGHT], or [com.appdimens.dynamic.common.DpQualifier.WIDTH].
 * @param inverter           Orientation-based dimension swap rule (default: [Inverter.DEFAULT]).
 * @param ignoreMultiWindows If `true`, returns the base value in pixels unscaled when in split-screen.
 * @param applyAspectRatio   If `true`, applies the aspect-ratio multiplier.
 * @param customSensitivityK Override for the AR sensitivity constant (null = library default).
 * @return Dynamically scaled pixel value as [Float].
 */
@JvmOverloads
fun Number.toDynamicLogarithmicPx(
    ctx: DimenCallContext,
    qualifier: DpQualifier,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val base = this.toFloat()
    val metrics = ctx.screenMetrics
    val density = ctx.screenMetrics.density

    val cacheKey = DimenCache.buildKey(
        baseValue = base,
        isLandscape = metrics.orientation == ScreenOrientation.LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.LOGARITHMIC,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )

    return DimenCache.getOrPut(cacheKey, ctx.cachePersistence) {
        val scaledDp = calculateLogarithmicDp(base, metrics, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, ctx)
        scaledDp * density
    }
}

/**
 * EN
 * Shared pure-math scaling kernel used by [toDynamicLogarithmicPx] and [toDynamicLogarithmicDp].
 *
 * Algorithm summary:
 * 1. Applies [Inverter] rules to swap the effective [DpQualifier] based on screen orientation.
 * 2. If [ignoreMultiWindows] is `true`, detects split-screen mode via layout flags; if active,
 *    returns [baseValue] unchanged so the UI does not over-scale inside a small window.
 * 3. For the common path (`SMALL_WIDTH` + `DEFAULT` inverter + no custom sensitivity),
 *    delegates to [DimenCache.calculateRawScaling] which reads pre-computed factors from
 *    [DimenCache.ScreenFactors] — a single float multiply, zero extra allocations.
 * 4. For other qualifiers or a custom sensitivity constant, reads the screen dimension from
 *    [android.content.res.Configuration] and performs the scaling formula inline.
 *
 * > **Performance**: Simple paths without Aspect Ratio complete in ~2 ns (single multiply).
 * > Paths with Aspect Ratio require ~41 ns on Snapdragon 888 (includes ln() fallback).
 * > Results are memoized by the [DimenCache] shared across code and compose packages.
 *
 * > **Note**: Both `code/` and `compose/` packages intentionally maintain separate copies of this
 * > function because the `code/` variant operates on [android.content.res.Configuration] directly
 * > (no Compose runtime), while `compose/` reads it from [androidx.compose.ui.platform.LocalConfiguration].
 * > The math is identical; only the Context acquisition path differs.
 *
 * PT
 * Núcleo de escalonamento puro compartilhado por [toDynamicLogarithmicPx] e [toDynamicLogarithmicDp].
 *
 * Resumo do algoritmo:
 * 1. Aplica as regras de [Inverter] para trocar o [DpQualifier] efetivo conforme a orientação.
 * 2. Se [ignoreMultiWindows] for `true`, detecta split-screen via flags de layout;
 *    se ativo, retorna [baseValue] sem escalar.
 * 3. Para o caminho comum (SMALL_WIDTH + DEFAULT + sem sensibilidade customizada),
 *    delega para [DimenCache.calculateRawScaling] com os fatores pré-calculados.
 * 4. Para outros qualificadores ou sensibilidade customizada, lê a dimensão da tela
 *    da [android.content.res.Configuration] e executa a fórmula de escalonamento inline.
 *
 * > **Nota**: Os pacotes `code/` e `compose/` mantêm cópias separadas intencionalmente.
 * > A versão `code/` opera sobre [android.content.res.Configuration] diretamente,
 * > enquanto a versão `compose/` usa [androidx.compose.ui.platform.LocalConfiguration].
 * > A matemática é idêntica; apenas a obtenção do contexto difere.
 *
 * @param baseValue          Raw Dp value to scale (e.g. `16f` for 16 dp).
 * @param configuration      Current [android.content.res.Configuration] from the context.
 * @param qualifier          Original screen qualifier before inversion.
 * @param inverter           Orientation-swap rule.
 * @param ignoreMultiWindows Whether to suppress scaling in multi-window mode.
 * @param applyAspectRatio   Whether to apply the AR multiplier.
 * @param customSensitivityK Custom AR sensitivity constant, or `null` for the library default.
 * @return Scaled Dp value as a raw [Float].
 */
internal fun calculateLogarithmicDp(
    baseValue: Float,
    metrics: ScreenMetricsSnapshot,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?,
    ctx: DimenCallContext? = null
): Float {
    val isLandscape = metrics.orientation == ScreenOrientation.LANDSCAPE
    val isPortrait = metrics.orientation == ScreenOrientation.PORTRAIT
    val q = DimenCalculationPlumbing.effectiveQualifier(qualifier, inverter, isLandscape, isPortrait)
    if (DimenCalculationPlumbing.isMultiWindowConstrained(metrics, ignoreMultiWindows)) return baseValue
    val isDefaultSw = (qualifier == DpQualifier.SMALL_WIDTH) && (inverter == Inverter.DEFAULT)
    val scale = if (isDefaultSw) {
        DimenCache.currentLogScale
    } else {
        val dim = DimenCalculationPlumbing.readScreenDp(metrics, q)
        val sens = 0.4f
        val inv = DimenCache.INV_BASE_RATIO
        if (dim > DesignScaleConstants.BASE_WIDTH_DP) {
            1f + sens * kotlin.math.ln(dim * inv)
        } else {
            1f - sens * kotlin.math.ln(DesignScaleConstants.BASE_WIDTH_DP / dim)
        }
    }
    var out = baseValue * scale
    if (applyAspectRatio) {
        out *= if (customSensitivityK == null) {
            DimenCache.currentAspectRatioMul
        } else {
            1f + customSensitivityK * DimenCache.currentLogNormalizedAr
        }
    }
    return out
}

/**
 * EN
 * Converts a [Number] (base Dp value) into a dynamically scaled Dp [Float] for View-based (non-Compose) code.
 *
 * Unlike [toDynamicLogarithmicPx], the result is returned in Dp units — no density conversion is applied.
 * This is useful for APIs that accept logical Dp values directly (e.g. `View.setPadding` with a
 * custom Dp-aware layout engine).
 *
 * Same caching, validation, and bypass semantics as [toDynamicLogarithmicPx].
 *
 * PT
 * Converte um [Number] (valor Dp base) em um [Float] em Dp dinamicamente escalado para código View-based.
 *
 * Ao contrário de [toDynamicLogarithmicPx], o resultado é retornado em unidades Dp — sem conversão de densidade.
 * Útil para APIs que aceitam valores Dp lógicos diretamente.
 *
 * Mesma semântica de cache, validação e bypass de [toDynamicLogarithmicPx].
 *
 * @param context            Android [DimenCallContext] for configuration access.
 * @param qualifier          Screen dimension qualifier.
 * @param inverter           Orientation-based dimension swap rule (default: [Inverter.DEFAULT]).
 * @param ignoreMultiWindows If `true`, returns the base Dp value unscaled when in split-screen.
 * @param applyAspectRatio   If `true`, applies the aspect-ratio multiplier.
 * @param customSensitivityK Override for the AR sensitivity constant (null = library default).
 * @return Dynamically scaled Dp value as [Float].
 */
@JvmOverloads
fun Number.toDynamicLogarithmicDp(
    ctx: DimenCallContext,
    qualifier: DpQualifier,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val base = this.toFloat()
    val metrics = ctx.screenMetrics

    val cacheKey = DimenCache.buildKey(
        baseValue = base,
        isLandscape = metrics.orientation == ScreenOrientation.LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.LOGARITHMIC,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )

    return DimenCache.getOrPut(cacheKey, ctx.cachePersistence) {
        calculateLogarithmicDp(base, metrics, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, ctx)
    }
}