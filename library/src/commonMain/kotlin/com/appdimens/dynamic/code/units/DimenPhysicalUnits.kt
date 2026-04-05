/**
 * Physical units conversion (KMP): uses [ScreenMetricsSnapshot] for density / font scale.
 */
package com.appdimens.dynamic.code.units

import com.appdimens.dynamic.common.UnitType
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import com.appdimens.dynamic.platform.cmToDp
import com.appdimens.dynamic.platform.inchToDp
import com.appdimens.dynamic.platform.mmToDp
import kotlin.math.PI

object DimenPhysicalUnits {

    fun toDpFromMm(mm: Float, metrics: ScreenMetricsSnapshot): Float = mmToDp(mm)

    fun toDpFromCm(cm: Float, metrics: ScreenMetricsSnapshot): Float = cmToDp(cm)

    fun toDpFromInch(inch: Float, metrics: ScreenMetricsSnapshot): Float = inchToDp(inch)

    fun toPxFromMm(mm: Float, metrics: ScreenMetricsSnapshot): Float = mmToDp(mm) * metrics.density

    fun toPxFromCm(cm: Float, metrics: ScreenMetricsSnapshot): Float = cmToDp(cm) * metrics.density

    fun toPxFromInch(inch: Float, metrics: ScreenMetricsSnapshot): Float = inchToDp(inch) * metrics.density

    fun toSpFromMm(mm: Float, metrics: ScreenMetricsSnapshot): Float =
        toPxFromMm(mm, metrics) / (metrics.density * metrics.fontScale.coerceAtLeast(1e-4f))

    fun toSpFromCm(cm: Float, metrics: ScreenMetricsSnapshot): Float =
        toPxFromCm(cm, metrics) / (metrics.density * metrics.fontScale.coerceAtLeast(1e-4f))

    fun toSpFromInch(inch: Float, metrics: ScreenMetricsSnapshot): Float =
        toPxFromInch(inch, metrics) / (metrics.density * metrics.fontScale.coerceAtLeast(1e-4f))

    fun radiusFromDiameter(diameter: Float, unitType: UnitType, metrics: ScreenMetricsSnapshot): Float {
        val diameterInDp = when (unitType) {
            UnitType.MM -> toDpFromMm(diameter, metrics)
            UnitType.CM -> toDpFromCm(diameter, metrics)
            UnitType.INCH -> toDpFromInch(diameter, metrics)
            UnitType.DP -> diameter
            UnitType.SP -> diameter * metrics.fontScale
            UnitType.PX -> diameter / metrics.density.coerceAtLeast(1e-4f)
        }
        return diameterInDp / 2.0f
    }

    fun radiusFromCircumference(circumference: Float, unitType: UnitType, metrics: ScreenMetricsSnapshot): Float {
        val circumferenceInDp = when (unitType) {
            UnitType.MM -> toDpFromMm(circumference, metrics)
            UnitType.CM -> toDpFromCm(circumference, metrics)
            UnitType.INCH -> toDpFromInch(circumference, metrics)
            UnitType.DP -> circumference
            UnitType.SP -> circumference * metrics.fontScale
            UnitType.PX -> circumference / metrics.density.coerceAtLeast(1e-4f)
        }
        return circumferenceInDp / (2.0f * PI.toFloat())
    }

    fun Float.mmToCm(): Float = this / 10.0f
    fun Float.mmToInch(): Float = this / 25.4f
    fun Float.cmToMm(): Float = this * 10.0f
    fun Float.cmToInch(): Float = this / 2.54f
    fun Float.inchToMm(): Float = this * 25.4f
    fun Float.inchToCm(): Float = this * 2.54f
}
