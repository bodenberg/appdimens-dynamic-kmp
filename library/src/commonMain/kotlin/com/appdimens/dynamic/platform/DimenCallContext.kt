package com.appdimens.dynamic.platform

import com.appdimens.dynamic.common.UiModeType

/**
 * Platform-agnostic call context: screen metrics, optional disk cache, and UI mode for mode facilitators.
 */
interface DimenCallContext {
    val screenMetrics: ScreenMetricsSnapshot
    val cachePersistence: CachePersistence?
    fun currentUiMode(): UiModeType
}
