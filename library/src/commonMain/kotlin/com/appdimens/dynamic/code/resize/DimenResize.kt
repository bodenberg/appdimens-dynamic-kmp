/**
 * EN View / Context API: resolve auto-resize bounds to px and pick the largest fitting size.
 * PT API baseada em Context: limites em px e escolha da maior medida que ainda cabe.
 */
package com.appdimens.dynamic.code.resize

import com.appdimens.dynamic.platform.DimenCallContext
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import com.appdimens.dynamic.common.ScreenOrientation


import com.appdimens.dynamic.core.ResizeBound
import com.appdimens.dynamic.core.ResizeRangePx
import com.appdimens.dynamic.core.resolveToPx

fun interface ResizeFitPredicate {
    fun fits(candidatePx: Float): Boolean
}

object DimenResize {

    @JvmStatic
    fun rangePx(ctx: DimenCallContext, min: ResizeBound, max: ResizeBound, step: ResizeBound): ResizeRangePx {
        val metrics = ctx.screenMetrics
        val d = metrics.density
        val fs = metrics.fontScale.coerceAtLeast(1e-6f)
        return ResizeRangePx(
            minPx = min.resolveToPx(metrics, d, fs),
            maxPx = max.resolveToPx(metrics, d, fs),
            stepPx = step.resolveToPx(metrics, d, fs),
        )
    }

    @JvmStatic
    fun fittingPx(range: ResizeRangePx, predicate: ResizeFitPredicate): Float =
        range.resolveFitting { predicate.fits(it) }
}

fun ResizeRangePx.fittingPx(fits: (candidatePx: Float) -> Boolean): Float =
    resolveFitting(fits)
