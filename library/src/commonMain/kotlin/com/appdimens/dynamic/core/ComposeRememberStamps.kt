/**
 * Author & Developer: Jean Bodenberg
 *
 * EN Packed [Long] stamps for [androidx.compose.runtime.remember] keys — zero allocation.
 * PT Carimbos [Long] empacotados para chaves de [remember] — zero alocação.
 */
package com.appdimens.dynamic.core

import com.appdimens.dynamic.platform.DimenCallContext
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import com.appdimens.dynamic.common.ScreenOrientation


import androidx.compose.ui.unit.Density

/**
 * EN Layout stamp for [androidx.compose.runtime.remember] keys.
 * Uses [Configuration.hashCode] which reflects actual content (densityDpi, locale, fontScale,
 * orientation, etc.), ensuring correct recomposition when any configuration field changes.
 *
 * PT Carimbo de layout para chaves de [remember].
 * Usa [Configuration.hashCode] que reflete o conteúdo real, garantindo recomposição correta
 * quando qualquer campo da configuração muda.
 */
@Suppress("UNUSED_PARAMETER")
internal fun layoutRememberStamp(metrics: ScreenMetricsSnapshot, ctx: DimenCallContext? = null): Long {
    val sw = metrics.smallestWidthDp.toLong() and 0xFFFFFL
    val w = metrics.widthDp.toLong() and 0xFFFFFL
    val h = metrics.heightDp.toLong() and 0xFFFFFL
    val o = metrics.orientation.ordinal.toLong() and 0xFL
    val id = metrics.layoutConfigHash.toLong()
    return ((o shl 60) or (sw shl 40) or (w shl 20) or h) xor id
}

/**
 * EN [layoutRememberStamp] xor raw bits of [Density.density] and [Density.fontScale] for Px composable paths.
 * PT [layoutRememberStamp] xor bits brutos de densidade e escala de fonte para caminhos Px.
 */
internal fun pxRememberStamp(layoutStamp: Long, density: Density): Long {
    val d = density.density.toRawBits().toLong() and 0xFFFFFFFFL
    val f = density.fontScale.toRawBits().toLong() and 0xFFFFFFFFL
    return layoutStamp xor (d shl 32) xor f
}

/**
 * EN Stamp equivalent to the former multi-key [remember] for [com.appdimens.dynamic.compose.DimenScaled] / [com.appdimens.dynamic.compose.ScaledSp] custom entry resolution.
 * PT Carimbo equivalente ao antigo [remember] multi-chave para resolução de entradas customizadas.
 */
internal fun scaledEntryRememberStamp(
    uiModeOrdinal: Int,
    metrics: ScreenMetricsSnapshot,
    aspectRatio: Float,
    ignoreMultiWindows: Boolean
): Long {
    val sw = metrics.smallestWidthDp.toLong() and 0xFFFFFL
    val w = metrics.widthDp.toLong() and 0xFFFFFL
    val h = metrics.heightDp.toLong() and 0xFFFFFL
    val o = metrics.orientation.ordinal.toLong() and 0xFL
    val packed = (o shl 60) or (sw shl 40) or (w shl 20) or h
    val ar = aspectRatio.toRawBits().toLong()
    val imw = if (ignoreMultiWindows) 0x13579BDFL else 0L
    return packed xor ar xor uiModeOrdinal.toLong() xor imw
}
