package com.appdimens.dynamic.platform

import android.content.Context
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.common.UiModeTypeAndroid

private class AndroidDimenCallContextImpl(private val context: Context) : DimenCallContext {
    private val persistence by lazy { createAndroidCachePersistence(context) }
    override val screenMetrics: ScreenMetricsSnapshot get() = context.toScreenMetrics()
    override val cachePersistence: CachePersistence get() = persistence
    override fun currentUiMode(): UiModeType = UiModeTypeAndroid.resolve(context, null)
}

fun Context.asDimenCallContext(): DimenCallContext = AndroidDimenCallContextImpl(this)
