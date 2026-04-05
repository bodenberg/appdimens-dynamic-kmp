package com.appdimens.dynamic.sample.cmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.LocalScreenMetrics
import com.appdimens.dynamic.platform.createAndroidCachePersistence

@Composable
actual fun PlatformDimenCacheBinding() {
    val context = LocalContext.current
    val metrics = LocalScreenMetrics.current
    val persistence = remember(context) { createAndroidCachePersistence(context) }
    LaunchedEffect(metrics.layoutConfigHash, persistence) {
        DimenCache.init(persistence, metrics)
    }
}
