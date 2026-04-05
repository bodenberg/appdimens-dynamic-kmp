/**
 * UI mode / form-factor classification for responsive dimensions.
 * Platform-specific resolution (foldables, TV, etc.) lives in androidMain / iosMain / jvmMain.
 */
package com.appdimens.dynamic.common

import com.appdimens.dynamic.platform.DimenCallContext
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import com.appdimens.dynamic.common.ScreenOrientation


/**
 * Stable integer ids align with Android [android.content.res.Configuration] UI_MODE_TYPE_* where applicable.
 */
enum class UiModeType(val stableId: Int) {
    UNDEFINED(0),
    NORMAL(1),
    DESK(2),
    CAR(3),
    TELEVISION(4),
    APPLIANCE(5),
    WATCH(6),
    VR_HEADSET(7),
    FOLD_OPEN(-101),
    FOLD_CLOSED(-102),
    FLIP_OPEN(-103),
    FLIP_CLOSED(-104),
    FOLD_HALF_OPENED(-105),
    FLIP_HALF_OPENED(-106);

    companion object {
        fun fromStableId(id: Int): UiModeType =
            entries.firstOrNull { it.stableId == id } ?: NORMAL
    }
}
