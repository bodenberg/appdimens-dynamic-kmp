package com.appdimens.dynamic.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.common.UiModeTypeAndroid
import com.appdimens.dynamic.platform.toScreenMetrics
import com.appdimens.dynamic.platform.findActivity

@Composable
actual fun AppDimensProvider(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val uiModeType = remember(context, foldingFeature) {
        UiModeTypeAndroid.resolve(context, foldingFeature)
    }
    val metrics = remember(context) { context.toScreenMetrics() }
    CompositionLocalProvider(
        LocalUiModeType provides uiModeType,
        LocalScreenMetrics provides metrics,
    ) {
        content()
    }
}

@Composable
internal actual fun getCurrentUiModeType(): UiModeType {
    val provided = LocalUiModeType.current
    if (provided != UiModeType.UNDEFINED) return provided
    val context = LocalContext.current
    return remember(context) { UiModeTypeAndroid.resolve(context, null) }
}
