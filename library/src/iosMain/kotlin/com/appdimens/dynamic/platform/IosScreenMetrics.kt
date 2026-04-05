package com.appdimens.dynamic.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import com.appdimens.dynamic.common.ScreenOrientation
import kotlin.math.roundToInt

/**
 * Builds a [ScreenMetricsSnapshot] from Compose window size and [LocalDensity] (iOS).
 */
@Composable
fun rememberIosScreenMetrics(): ScreenMetricsSnapshot {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    return remember(density, windowInfo.containerSize) {
        val wPx = windowInfo.containerSize.width
        val hPx = windowInfo.containerSize.height
        val wDp = (wPx / density.density).roundToInt().coerceAtLeast(1)
        val hDp = (hPx / density.density).roundToInt().coerceAtLeast(1)
        val sw = minOf(wDp, hDp)
        val orientation = if (wDp >= hDp) ScreenOrientation.LANDSCAPE else ScreenOrientation.PORTRAIT
        val dpi = (density.density * 160f).roundToInt().coerceAtLeast(1)
        ScreenMetricsSnapshot(
            widthDp = wDp,
            heightDp = hDp,
            smallestWidthDp = sw,
            densityDpi = dpi,
            fontScale = density.fontScale,
            orientation = orientation,
            density = density.density,
            isInMultiWindow = false,
            layoutConfigHash = wDp xor (hDp shl 16),
        )
    }
}
