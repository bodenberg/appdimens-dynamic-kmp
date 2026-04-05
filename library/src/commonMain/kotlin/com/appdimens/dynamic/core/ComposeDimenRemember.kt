/**
 * Shared Compose remember + [DimenCache] wiring (no scaling formulas).
 */
package com.appdimens.dynamic.core

import com.appdimens.dynamic.platform.DimenCallContext
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import com.appdimens.dynamic.common.ScreenOrientation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun rememberDimenDp(
    cacheKey: Long,
    layoutStamp: Long,
    ctx: DimenCallContext,
    compute: () -> Float,
): Dp = remember(cacheKey, layoutStamp) {
    DimenCache.getOrPut(cacheKey, ctx.cachePersistence, compute).dp
}

@Composable
internal fun rememberDimenPxFromDp(
    cacheKey: Long,
    pxStamp: Long,
    ctx: DimenCallContext,
    density: Density,
    compute: () -> Float,
): Float = remember(cacheKey, pxStamp) {
    DimenCache.getOrPut(cacheKey, ctx.cachePersistence) {
        val scaledDp = compute()
        density.run { scaledDp.dp.toPx() }
    }
}
