package com.appdimens.dynamic.core

import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.ScreenOrientation
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Platform-agnostic plumbing tests using fabricated [ScreenMetricsSnapshot] (no Android Configuration).
 */
class DimenCalculationPlumbingCommonTest {

    private fun metrics(
        sw: Int = 400,
        w: Int = sw,
        h: Int = 800,
        multiWindow: Boolean = false,
    ): ScreenMetricsSnapshot = ScreenMetricsSnapshot(
        widthDp = w,
        heightDp = h,
        smallestWidthDp = sw,
        densityDpi = 420,
        fontScale = 1f,
        orientation = ScreenOrientation.PORTRAIT,
        density = 2.625f,
        isInMultiWindow = multiWindow,
    )

    @Test
    fun readScreenDp_matchesSnapshot() {
        val m = metrics(sw = 300, w = 400, h = 800)
        assertEquals(400f, DimenCalculationPlumbing.readScreenDp(m, DpQualifier.WIDTH), 0f)
        assertEquals(800f, DimenCalculationPlumbing.readScreenDp(m, DpQualifier.HEIGHT), 0f)
        assertEquals(300f, DimenCalculationPlumbing.readScreenDp(m, DpQualifier.SMALL_WIDTH), 0f)
    }

    @Test
    fun isMultiWindowConstrained_flagTrue() {
        val m = metrics(w = 400, multiWindow = true)
        assertTrue(DimenCalculationPlumbing.isMultiWindowConstrained(m, ignoreMultiWindows = true))
    }

    @Test
    fun isMultiWindowConstrained_ignoreFalse() {
        val m = metrics(w = 200)
        assertFalse(DimenCalculationPlumbing.isMultiWindowConstrained(m, ignoreMultiWindows = false))
    }

    @Test
    fun effectiveQualifier_phToLw_landscapeSwaps() {
        assertEquals(
            DpQualifier.WIDTH,
            DimenCalculationPlumbing.effectiveQualifier(
                DpQualifier.HEIGHT,
                Inverter.PH_TO_LW,
                isLandscape = true,
                isPortrait = false,
            ),
        )
    }
}
