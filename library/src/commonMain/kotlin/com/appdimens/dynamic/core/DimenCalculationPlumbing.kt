/**
 * Strategy-agnostic screen plumbing: inverter resolution, multi-window detection, dp reads.
 */
package com.appdimens.dynamic.core

import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import kotlin.math.ln

object DimenCalculationPlumbing {

    fun effectiveQualifier(
        qualifier: DpQualifier,
        inverter: Inverter,
        isLandscape: Boolean,
        isPortrait: Boolean,
    ): DpQualifier {
        var actual = qualifier
        when (inverter) {
            Inverter.PH_TO_LW -> if (isLandscape && qualifier == DpQualifier.HEIGHT) actual = DpQualifier.WIDTH
            Inverter.PW_TO_LH -> if (isLandscape && qualifier == DpQualifier.WIDTH) actual = DpQualifier.HEIGHT
            Inverter.LH_TO_PW -> if (isPortrait && qualifier == DpQualifier.HEIGHT) actual = DpQualifier.WIDTH
            Inverter.LW_TO_PH -> if (isPortrait && qualifier == DpQualifier.WIDTH) actual = DpQualifier.HEIGHT
            Inverter.SW_TO_LH -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actual = DpQualifier.HEIGHT
            Inverter.SW_TO_LW -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actual = DpQualifier.WIDTH
            Inverter.SW_TO_PH -> if (isPortrait && qualifier == DpQualifier.SMALL_WIDTH) actual = DpQualifier.HEIGHT
            Inverter.SW_TO_PW -> if (isPortrait && qualifier == DpQualifier.SMALL_WIDTH) actual = DpQualifier.WIDTH
            Inverter.DEFAULT -> Unit
        }
        return actual
    }

    /**
     * When [ignoreMultiWindows] is true, scaling is suppressed in multi-window (split-screen, etc.).
     * [ScreenMetricsSnapshot.isInMultiWindow] is set by the platform (e.g. Android Activity);
     * when false, a dp-based heuristic approximates split-screen.
     */
    fun isMultiWindowConstrained(
        metrics: ScreenMetricsSnapshot,
        ignoreMultiWindows: Boolean,
    ): Boolean {
        if (!ignoreMultiWindows) return false
        if (metrics.isInMultiWindow) return true
        val swDp = metrics.smallestWidthDp.toFloat()
        if (swDp <= 0f) return false
        val cwDp = metrics.widthDp.toFloat()
        return (swDp - cwDp) >= (swDp * 0.1f)
    }

    fun readScreenDp(metrics: ScreenMetricsSnapshot, actualQualifier: DpQualifier): Float =
        when (actualQualifier) {
            DpQualifier.HEIGHT -> metrics.heightDp.toFloat()
            DpQualifier.WIDTH -> metrics.widthDp.toFloat()
            DpQualifier.SMALL_WIDTH -> metrics.smallestWidthDp.toFloat()
        }

    fun smallestSideDp(metrics: ScreenMetricsSnapshot): Float =
        minOf(metrics.widthDp.toFloat(), metrics.heightDp.toFloat())

    fun largestSideDp(metrics: ScreenMetricsSnapshot): Float =
        maxOf(metrics.widthDp.toFloat(), metrics.heightDp.toFloat())

    fun aspectRatioMultiplier(metrics: ScreenMetricsSnapshot, sensitivity: Float): Float {
        val sm = smallestSideDp(metrics)
        val lg = largestSideDp(metrics)
        if (sm <= 0f) return 1f
        val ar = lg / sm
        if (!ar.isFinite()) return 1f
        return 1f + sensitivity * ln(ar * DesignScaleConstants.INV_REFERENCE_ASPECT_RATIO)
    }
}
