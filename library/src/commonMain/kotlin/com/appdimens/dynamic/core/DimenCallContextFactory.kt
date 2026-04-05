package com.appdimens.dynamic.core

import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.platform.CachePersistence
import com.appdimens.dynamic.platform.DimenCallContext
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot

/** Compose / tests: [DimenCallContext] without Android Context. */
fun staticDimenCallContext(
    metrics: ScreenMetricsSnapshot,
    persistence: CachePersistence? = null,
): DimenCallContext = object : DimenCallContext {
    override val screenMetrics: ScreenMetricsSnapshot get() = metrics
    override val cachePersistence: CachePersistence? get() = persistence
    override fun currentUiMode(): UiModeType = DimenCache.getCachedUiModeForMetrics(metrics)
}
