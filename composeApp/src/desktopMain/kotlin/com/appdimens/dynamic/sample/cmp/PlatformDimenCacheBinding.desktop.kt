package com.appdimens.dynamic.sample.cmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.LocalScreenMetrics
import com.appdimens.dynamic.platform.createDesktopCachePersistence

@Composable
actual fun PlatformDimenCacheBinding() {
    val metrics = LocalScreenMetrics.current
    val persistence = remember { createDesktopCachePersistence() }
    LaunchedEffect(metrics.layoutConfigHash, persistence) {
        DimenCache.init(persistence, metrics)
    }
}
