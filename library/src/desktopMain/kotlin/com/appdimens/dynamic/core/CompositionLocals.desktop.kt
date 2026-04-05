package com.appdimens.dynamic.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import com.appdimens.dynamic.common.ScreenOrientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import java.util.Objects
import kotlin.math.roundToInt

@Composable
actual fun AppDimensProvider(content: @Composable () -> Unit) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val metrics = remember(density.density, density.fontScale, windowInfo.containerSize) {
        val wPx = windowInfo.containerSize.width
        val hPx = windowInfo.containerSize.height
        val wDp = (wPx / density.density).roundToInt().coerceAtLeast(1)
        val hDp = (hPx / density.density).roundToInt().coerceAtLeast(1)
        val sw = minOf(wDp, hDp)
        val dpi = (density.density * 160f).roundToInt().coerceAtLeast(1)
        val orientation = if (wDp >= hDp) ScreenOrientation.LANDSCAPE else ScreenOrientation.PORTRAIT
        ScreenMetricsSnapshot(
            widthDp = wDp,
            heightDp = hDp,
            smallestWidthDp = sw,
            densityDpi = dpi,
            fontScale = density.fontScale,
            orientation = orientation,
            density = density.density,
            isInMultiWindow = false,
            layoutConfigHash = Objects.hash(wDp, hDp, sw, dpi, density.fontScale, orientation.ordinal),
        )
    }
    CompositionLocalProvider(
        LocalUiModeType provides UiModeType.NORMAL,
        LocalScreenMetrics provides metrics,
    ) {
        content()
    }
}

@Composable
internal actual fun getCurrentUiModeType(): UiModeType {
    val v = LocalUiModeType.current
    return if (v != UiModeType.UNDEFINED) v else UiModeType.NORMAL
}
