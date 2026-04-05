package com.appdimens.dynamic.code.auto

import android.content.Context
import com.appdimens.dynamic.platform.asDimenCallContext

fun DimenAuto.warmupCache(context: Context) = warmupCache(context.asDimenCallContext())
