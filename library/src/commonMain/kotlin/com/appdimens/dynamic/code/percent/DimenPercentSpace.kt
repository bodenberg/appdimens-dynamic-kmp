/**
 * EN Literal percent of screen width / smallest width / height, or of a reference length (dp).
 * PT Percentual literal da largura da tela, smallest width, altura ou de um comprimento de referência (dp).
 */
package com.appdimens.dynamic.code.percent

import com.appdimens.dynamic.platform.DimenCallContext
import com.appdimens.dynamic.platform.ScreenMetricsSnapshot
import com.appdimens.dynamic.common.ScreenOrientation


import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.core.literalPercentOfReferenceDp
import com.appdimens.dynamic.core.literalPercentOfScreenDp

// ─── Screen fraction → px (same idea as psdp / pwdp returning px) ─────────────

@JvmOverloads
fun Number.spaceW(ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float {
    val c = ctx.screenMetrics
    val dp = literalPercentOfScreenDp(toFloat(), DpQualifier.WIDTH, c, ignoreMultiWindows)
    return dp * ctx.screenMetrics.density
}

@JvmOverloads
fun Number.spaceSw(ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float {
    val c = ctx.screenMetrics
    val dp = literalPercentOfScreenDp(toFloat(), DpQualifier.SMALL_WIDTH, c, ignoreMultiWindows)
    return dp * ctx.screenMetrics.density
}

@JvmOverloads
fun Number.spaceH(ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float {
    val c = ctx.screenMetrics
    val dp = literalPercentOfScreenDp(toFloat(), DpQualifier.HEIGHT, c, ignoreMultiWindows)
    return dp * ctx.screenMetrics.density
}

/** PT Igual a [spaceW] com `ignoreMultiWindows = true` (split-screen / multi-janela). */
fun Number.spaceWi(ctx: DimenCallContext): Float = spaceW(ctx, ignoreMultiWindows = true)

/** PT Igual a [spaceSw] com `ignoreMultiWindows = true`. */
fun Number.spaceSwi(ctx: DimenCallContext): Float = spaceSw(ctx, ignoreMultiWindows = true)

/** PT Igual a [spaceH] com `ignoreMultiWindows = true`. */
fun Number.spaceHi(ctx: DimenCallContext): Float = spaceH(ctx, ignoreMultiWindows = true)

// ─── Mesmo valor em px com sufixo explícito (paridade com Compose: spaceWPx, psdpPx, etc.) ─

@JvmOverloads
fun Number.spaceWPx(ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float =
    spaceW(ctx, ignoreMultiWindows)

/** PT Igual a [spaceWPx] com `ignoreMultiWindows = true`. */
fun Number.spaceWPxi(ctx: DimenCallContext): Float = spaceWPx(ctx, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceSwPx(ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float =
    spaceSw(ctx, ignoreMultiWindows)

/** PT Igual a [spaceSwPx] com `ignoreMultiWindows = true`. */
fun Number.spaceSwPxi(ctx: DimenCallContext): Float = spaceSwPx(ctx, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceHPx(ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float =
    spaceH(ctx, ignoreMultiWindows)

/** PT Igual a [spaceHPx] com `ignoreMultiWindows = true`. */
fun Number.spaceHPxi(ctx: DimenCallContext): Float = spaceHPx(ctx, ignoreMultiWindows = true)

// ─── Screen fraction → dp (raw) ─────────────────────────────────────────────

@JvmOverloads
fun Number.spaceWDp(ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float =
    literalPercentOfScreenDp(toFloat(), DpQualifier.WIDTH, ctx.screenMetrics, ignoreMultiWindows)

@JvmOverloads
fun Number.spaceSwDp(ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float =
    literalPercentOfScreenDp(toFloat(), DpQualifier.SMALL_WIDTH, ctx.screenMetrics, ignoreMultiWindows)

@JvmOverloads
fun Number.spaceHDp(ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float =
    literalPercentOfScreenDp(toFloat(), DpQualifier.HEIGHT, ctx.screenMetrics, ignoreMultiWindows)

/** PT Igual a [spaceWDp] com `ignoreMultiWindows = true`. */
fun Number.spaceWDpi(ctx: DimenCallContext): Float = spaceWDp(ctx, ignoreMultiWindows = true)

/** PT Igual a [spaceSwDp] com `ignoreMultiWindows = true`. */
fun Number.spaceSwDpi(ctx: DimenCallContext): Float = spaceSwDp(ctx, ignoreMultiWindows = true)

/** PT Igual a [spaceHDp] com `ignoreMultiWindows = true`. */
fun Number.spaceHDpi(ctx: DimenCallContext): Float = spaceHDp(ctx, ignoreMultiWindows = true)

// ─── Reference length (dp) ──────────────────────────────────────────────────

@JvmOverloads
fun Number.space(referenceDp: Number, ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float {
    val c = ctx.screenMetrics
    val dp = literalPercentOfReferenceDp(toFloat(), referenceDp.toFloat(), c, ignoreMultiWindows)
    return dp * ctx.screenMetrics.density
}

@JvmOverloads
fun Number.spaceDp(referenceDp: Number, ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float =
    literalPercentOfReferenceDp(toFloat(), referenceDp.toFloat(), ctx.screenMetrics, ignoreMultiWindows)

/** PT Igual a [space] com `ignoreMultiWindows = true`. */
fun Number.spaceI(referenceDp: Number, ctx: DimenCallContext): Float =
    space(referenceDp, ctx, ignoreMultiWindows = true)

/** PT Igual a [spaceDp] com `ignoreMultiWindows = true`. */
fun Number.spaceDpi(referenceDp: Number, ctx: DimenCallContext): Float =
    spaceDp(referenceDp, ctx, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spacePx(referenceDp: Number, ctx: DimenCallContext, ignoreMultiWindows: Boolean = false): Float =
    space(referenceDp, ctx, ignoreMultiWindows)

/** PT Igual a [spacePx] com `ignoreMultiWindows = true`. */
fun Number.spacePxi(referenceDp: Number, ctx: DimenCallContext): Float =
    spacePx(referenceDp, ctx, ignoreMultiWindows = true)

// ─── Sp: numeric sp for View APIs; SpPx matches dp pixel size when fontScale respected ─

@JvmOverloads
fun Number.spaceWSp(ctx: DimenCallContext, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val resultDp = spaceWDp(ctx, ignoreMultiWindows)
    return literalPercentSpValue(ctx, resultDp, fontScale)
}

@JvmOverloads
fun Number.spaceSwSp(ctx: DimenCallContext, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val resultDp = spaceSwDp(ctx, ignoreMultiWindows)
    return literalPercentSpValue(ctx, resultDp, fontScale)
}

@JvmOverloads
fun Number.spaceHSp(ctx: DimenCallContext, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val resultDp = spaceHDp(ctx, ignoreMultiWindows)
    return literalPercentSpValue(ctx, resultDp, fontScale)
}

@JvmOverloads
fun Number.spaceWSpPx(ctx: DimenCallContext, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val sp = spaceWSp(ctx, fontScale, ignoreMultiWindows)
    val density = ctx.screenMetrics.density
    val fs = ctx.screenMetrics.fontScale.coerceAtLeast(1e-6f)
    return if (fontScale) sp * density * fs else sp * density
}

@JvmOverloads
fun Number.spaceSwSpPx(ctx: DimenCallContext, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val sp = spaceSwSp(ctx, fontScale, ignoreMultiWindows)
    val density = ctx.screenMetrics.density
    val fs = ctx.screenMetrics.fontScale.coerceAtLeast(1e-6f)
    return if (fontScale) sp * density * fs else sp * density
}

@JvmOverloads
fun Number.spaceHSpPx(ctx: DimenCallContext, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val sp = spaceHSp(ctx, fontScale, ignoreMultiWindows)
    val density = ctx.screenMetrics.density
    val fs = ctx.screenMetrics.fontScale.coerceAtLeast(1e-6f)
    return if (fontScale) sp * density * fs else sp * density
}

@JvmOverloads
fun Number.spaceSp(referenceDp: Number, ctx: DimenCallContext, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val resultDp = spaceDp(referenceDp, ctx, ignoreMultiWindows)
    return literalPercentSpValue(ctx, resultDp, fontScale)
}

@JvmOverloads
fun Number.spaceSpPx(referenceDp: Number, ctx: DimenCallContext, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val sp = spaceSp(referenceDp, ctx, fontScale, ignoreMultiWindows)
    val density = ctx.screenMetrics.density
    val fs = ctx.screenMetrics.fontScale.coerceAtLeast(1e-6f)
    return if (fontScale) sp * density * fs else sp * density
}

@JvmOverloads
fun Number.spaceWSpi(ctx: DimenCallContext, fontScale: Boolean = true): Float =
    spaceWSp(ctx, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceSwSpi(ctx: DimenCallContext, fontScale: Boolean = true): Float =
    spaceSwSp(ctx, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceHSpi(ctx: DimenCallContext, fontScale: Boolean = true): Float =
    spaceHSp(ctx, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceWSpiPx(ctx: DimenCallContext, fontScale: Boolean = true): Float =
    spaceWSpPx(ctx, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceSwSpiPx(ctx: DimenCallContext, fontScale: Boolean = true): Float =
    spaceSwSpPx(ctx, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceHSpiPx(ctx: DimenCallContext, fontScale: Boolean = true): Float =
    spaceHSpPx(ctx, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceSpi(referenceDp: Number, ctx: DimenCallContext, fontScale: Boolean = true): Float =
    spaceSp(referenceDp, ctx, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceSpiPx(referenceDp: Number, ctx: DimenCallContext, fontScale: Boolean = true): Float =
    spaceSpPx(referenceDp, ctx, fontScale, ignoreMultiWindows = true)

/** Sp value for [android.util.TypedValue.COMPLEX_UNIT_SP] when [fontScale] is true; else dp-like value for COMPLEX_UNIT_DIP. */
private fun literalPercentSpValue(ctx: DimenCallContext, resultDp: Float, fontScale: Boolean): Float {
    if (!fontScale) return resultDp
    val fs = ctx.screenMetrics.fontScale
    return if (fs > 0f) resultDp / fs else resultDp
}
