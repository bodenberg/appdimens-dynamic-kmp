/**
 * Literal percentage of screen or reference length (e.g. 10 → 10%).
 * Used by percent `space*` APIs; separate from [calculatePercentDp] sdp-style scaling.
 */
package com.appdimens.dynamic.core

import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot

fun literalPercentOfScreenDp(
    percent: Float,
    qualifier: DpQualifier,
    metrics: ScreenMetricsSnapshot,
    ignoreMultiWindows: Boolean,
): Float {
    val dim = DimenCalculationPlumbing.readScreenDp(metrics, qualifier)
    return (percent / 100f) * dim
}

fun literalPercentOfReferenceDp(
    percent: Float,
    referenceDp: Float,
    metrics: ScreenMetricsSnapshot,
    ignoreMultiWindows: Boolean,
): Float {
    return (percent / 100f) * referenceDp
}
