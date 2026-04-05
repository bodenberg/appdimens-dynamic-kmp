package com.appdimens.dynamic.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.platform.rememberIosScreenMetrics
import platform.UIKit.UIDevice
import platform.UIKit.UIUserInterfaceIdiom

@Composable
actual fun AppDimensProvider(content: @Composable () -> Unit) {
    val metrics = rememberIosScreenMetrics()
    val uiMode = remember {
        val idiom = UIDevice.currentDevice.userInterfaceIdiom
        if (idiom == UIUserInterfaceIdiom.UIUserInterfaceIdiomPad) UiModeType.DESK else UiModeType.NORMAL
    }
    CompositionLocalProvider(
        LocalUiModeType provides uiMode,
        LocalScreenMetrics provides metrics,
    ) {
        content()
    }
}

@Composable
internal actual fun getCurrentUiModeType(): UiModeType {
    val v = LocalUiModeType.current
    return if (v != UiModeType.UNDEFINED) v else UiModeType.NORMAL
}
