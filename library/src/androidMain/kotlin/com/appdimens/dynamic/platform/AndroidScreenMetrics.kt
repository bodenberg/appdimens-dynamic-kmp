package com.appdimens.dynamic.platform

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import com.appdimens.dynamic.common.ScreenOrientation
import java.util.Objects

fun Context.findActivity(): Activity? {
    var ctx: Context = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}

fun Configuration.toScreenMetrics(isInMultiWindow: Boolean = false): ScreenMetricsSnapshot {
    val o = when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> ScreenOrientation.LANDSCAPE
        Configuration.ORIENTATION_PORTRAIT -> ScreenOrientation.PORTRAIT
        else -> ScreenOrientation.UNSPECIFIED
    }
    val dpi = densityDpi.coerceAtLeast(1)
    return ScreenMetricsSnapshot(
        widthDp = screenWidthDp,
        heightDp = screenHeightDp,
        smallestWidthDp = smallestScreenWidthDp,
        densityDpi = dpi,
        fontScale = fontScale,
        orientation = o,
        density = dpi / 160f,
        isInMultiWindow = isInMultiWindow,
        layoutConfigHash = Objects.hash(
            screenWidthDp,
            screenHeightDp,
            smallestScreenWidthDp,
            densityDpi,
            fontScale,
            orientation,
            isInMultiWindow,
        ),
    )
}

fun Context.toScreenMetrics(): ScreenMetricsSnapshot {
    val act = findActivity()
    return resources.configuration.toScreenMetrics(act?.isInMultiWindowMode == true)
}
