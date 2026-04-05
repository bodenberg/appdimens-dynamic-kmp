package com.appdimens.dynamic.common

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.hardware.SensorManager
import android.os.Build
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowMetricsCalculator

object UiModeTypeAndroid {

    fun resolve(context: Context, foldingFeature: FoldingFeature?): UiModeType {
        val config = context.resources.configuration

        if (foldingFeature != null) {
            val isFold = foldingFeature.orientation == FoldingFeature.Orientation.VERTICAL
            return if (isFold) {
                when (foldingFeature.state) {
                    FoldingFeature.State.FLAT -> UiModeType.FOLD_OPEN
                    FoldingFeature.State.HALF_OPENED -> UiModeType.FOLD_HALF_OPENED
                    else -> UiModeType.FOLD_CLOSED
                }
            } else {
                when (foldingFeature.state) {
                    FoldingFeature.State.FLAT -> UiModeType.FLIP_OPEN
                    FoldingFeature.State.HALF_OPENED -> UiModeType.FLIP_HALF_OPENED
                    else -> UiModeType.FLIP_CLOSED
                }
            }
        }

        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        val hingeSensor = sensorManager?.getDefaultSensor(36)
        val isFoldable = hingeSensor != null

        if (isFoldable) {
            val maxMetrics = WindowMetricsCalculator.getOrCreate().computeMaximumWindowMetrics(context)
            val maxBounds = maxMetrics.bounds
            val density = context.resources.displayMetrics.density
            val maxSwPx = kotlin.math.min(maxBounds.width(), maxBounds.height())
            val maxSwDp = maxSwPx / density
            val isFold = maxSwDp >= 600f
            val currentSwDp = config.smallestScreenWidthDp
            return if (isFold) {
                val unfoldedThreshold = (maxSwDp * 0.85f).toInt()
                when {
                    currentSwDp >= unfoldedThreshold -> UiModeType.FOLD_OPEN
                    currentSwDp >= (unfoldedThreshold * 0.6f).toInt() -> UiModeType.FOLD_HALF_OPENED
                    else -> UiModeType.FOLD_CLOSED
                }
            } else {
                val area = config.screenWidthDp * config.screenHeightDp
                val unfoldedArea = (maxSwDp * maxSwDp * 0.7f).toInt()
                when {
                    area >= unfoldedArea -> UiModeType.FLIP_OPEN
                    area >= (unfoldedArea * 0.5f).toInt() -> UiModeType.FLIP_HALF_OPENED
                    else -> UiModeType.FLIP_CLOSED
                }
            }
        }

        val type = config.uiMode and Configuration.UI_MODE_TYPE_MASK
        if (type == Configuration.UI_MODE_TYPE_TELEVISION ||
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
        ) {
            return UiModeType.TELEVISION
        }

        return when (type) {
            Configuration.UI_MODE_TYPE_NORMAL -> UiModeType.NORMAL
            Configuration.UI_MODE_TYPE_DESK -> UiModeType.DESK
            Configuration.UI_MODE_TYPE_CAR -> UiModeType.CAR
            Configuration.UI_MODE_TYPE_TELEVISION -> UiModeType.TELEVISION
            Configuration.UI_MODE_TYPE_APPLIANCE -> UiModeType.APPLIANCE
            Configuration.UI_MODE_TYPE_WATCH -> UiModeType.WATCH
            Configuration.UI_MODE_TYPE_UNDEFINED -> UiModeType.UNDEFINED
            else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && type == Configuration.UI_MODE_TYPE_VR_HEADSET) {
                UiModeType.VR_HEADSET
            } else {
                UiModeType.NORMAL
            }
        }
    }
}
