package com.appdimens.dynamic.platform

/** Android-compatible mm → dp (see [android.util.TypedValue.COMPLEX_UNIT_MM]). */
private const val MM_PER_INCH = 25.4f

fun mmToDp(mm: Float): Float = mm * 160f / MM_PER_INCH

fun cmToDp(cm: Float): Float = mmToDp(cm * 10f)

fun inchToDp(inch: Float): Float = inch * 160f
