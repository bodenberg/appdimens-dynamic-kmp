/**
 * Multiplatform snapshot of screen metrics (replaces Android [android.content.res.Configuration] in core APIs).
 */
package com.appdimens.dynamic.platform

import com.appdimens.dynamic.common.ScreenOrientation

/**
 * Immutable screen metrics used by all dimension calculations.
 *
 * @param isInMultiWindow When true (e.g. Android split-screen), multi-window suppression uses this flag;
 *                       other platforms typically keep false and rely on the dp heuristic.
 */
data class ScreenMetricsSnapshot(
    val widthDp: Int,
    val heightDp: Int,
    val smallestWidthDp: Int,
    val densityDpi: Int,
    val fontScale: Float,
    val orientation: ScreenOrientation,
    val density: Float,
    val isInMultiWindow: Boolean = false,
    val layoutConfigHash: Int = 0,
) {
    val isLandscape: Boolean get() = orientation == ScreenOrientation.LANDSCAPE
    val isPortrait: Boolean get() = orientation == ScreenOrientation.PORTRAIT
}
