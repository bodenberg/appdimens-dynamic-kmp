package com.appdimens.dynamic.core

import androidx.compose.runtime.staticCompositionLocalOf
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot

/**
 * Current [ScreenMetricsSnapshot] for Compose Multiplatform (provide at the root of your UI tree).
 */
val LocalScreenMetrics = staticCompositionLocalOf<ScreenMetricsSnapshot> {
    error("LocalScreenMetrics not provided — wrap with CompositionLocalProvider(LocalScreenMetrics provides ...)")
}
