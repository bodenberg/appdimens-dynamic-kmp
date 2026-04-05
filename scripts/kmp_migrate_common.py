#!/usr/bin/env python3
"""Mechanical migration helpers for commonMain Kotlin sources."""
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1] / "library" / "src" / "commonMain" / "kotlin"

IMPORT_ADD = "import com.appdimens.dynamic.platform.DimenCallContext\nimport com.appdimens.dynamic.platform.ScreenMetricsSnapshot\n"


def migrate_text(s: str) -> str:
    # Strip Android imports (handled per-file)
    lines = s.splitlines(keepends=True)
    out = []
    skip_next_blank = False
    for line in lines:
        if re.match(r"^import android\.", line):
            continue
        if re.match(r"^import androidx\.annotation\.", line):
            continue
        out.append(line)
    s = "".join(out)

    # Inject platform imports after package line if DimenCallContext used
    if "DimenCallContext" in s or "ctx: DimenCallContext" in s:
        if "import com.appdimens.dynamic.platform.DimenCallContext" not in s:
            s = re.sub(
                r"(package [^\n]+\n)",
                r"\1\n" + IMPORT_ADD,
                s,
                count=1,
            )

    # Common replacements
    s = s.replace("context.resources.configuration", "ctx.screenMetrics")
    s = s.replace("context.resources.displayMetrics.density", "ctx.screenMetrics.density")
    s = s.replace("resources.configuration", "ctx.screenMetrics")
    s = s.replace("resources.displayMetrics.density", "ctx.screenMetrics.density")

    s = re.sub(r"\bcontext:\s*Context\b", "ctx: DimenCallContext", s)
    s = re.sub(r"\bandroidContext:\s*Context\b", "ctx: DimenCallContext", s)

    # Rename parameter usage context. -> ctx.  (risky for other "context" identifiers)
    s = re.sub(r"\bcontext\.(resources|applicationContext)", r"ctx.\1", s)

    s = s.replace("val configuration = ctx.screenMetrics", "val metrics = ctx.screenMetrics")
    s = s.replace("val configuration = ctx.resources", "val metrics = ctx.screenMetrics")

    s = re.sub(
        r"\bval configuration = ctx\.screenMetrics\b",
        "val metrics = ctx.screenMetrics",
        s,
    )

    # If still "configuration" from old pattern
    s = re.sub(
        r"\bval configuration = ctx\.screenMetrics\b",
        "val metrics = ctx.screenMetrics",
        s,
    )

    # DimenCache / plumbing
    s = s.replace("DimenCache.getOrPut(", "DimenCache.getOrPut(")  # noop
    s = re.sub(
        r"DimenCache\.getOrPut\(([^,]+),\s*context\)",
        r"DimenCache.getOrPut(\1, ctx.cachePersistence)",
        s,
    )
    s = re.sub(
        r"DimenCache\.getOrPut\(([^,]+),\s*context\s*\)",
        r"DimenCache.getOrPut(\1, ctx.cachePersistence)",
        s,
    )
    s = re.sub(
        r"DimenCache\.getBatch\(([^,]+),\s*context\b",
        r"DimenCache.getBatch(\1, ctx.cachePersistence",
        s,
    )
    s = re.sub(
        r"DimenCache\.getOrPutAspectRatio\(([^,]+),\s*context\b",
        r"DimenCache.getOrPutAspectRatio(\1, ctx.cachePersistence",
        s,
    )
    s = re.sub(r"DimenCache\.init\(context\)", "DimenCache.init(ctx.cachePersistence!!, ctx.screenMetrics)", s)
    s = re.sub(
        r"DimenCache\.getCachedUiModeType\(context\)",
        "ctx.currentUiMode()",
        s,
    )
    s = re.sub(r"DimenCache\.saveToPersistence\(context\)", "ctx.cachePersistence?.let { DimenCache.saveToPersistence(it) }", s)
    s = re.sub(r"DimenCache\.clearAll\(context\)", "DimenCache.clearAll(ctx.cachePersistence)", s)
    s = re.sub(r"DimenCache\.clear\(context\)", "DimenCache.clear(ctx.cachePersistence)", s)

    # configuration -> metrics where qualifier / orientation checks
    s = re.sub(
        r"configuration\.orientation\s*==\s*Configuration\.ORIENTATION_LANDSCAPE",
        "metrics.orientation == com.appdimens.dynamic.common.ScreenOrientation.LANDSCAPE",
        s,
    )
    s = re.sub(
        r"configuration\.orientation\s*==\s*Configuration\.ORIENTATION_PORTRAIT",
        "metrics.orientation == com.appdimens.dynamic.common.ScreenOrientation.PORTRAIT",
        s,
    )
    s = re.sub(
        r"configuration\.screenLayout\s+and\s+Configuration\.SCREENLAYOUT_SIZE_MASK\s*!=\s*Configuration\.SCREENLAYOUT_SIZE_MASK",
        "(metrics.widthDp < metrics.smallestWidthDp || metrics.heightDp < metrics.smallestWidthDp)",
        s,
    )

    s = s.replace("getQualifierValue(qualifier, configuration)", "getQualifierValue(qualifier, metrics)")
    s = s.replace("getQualifierValue(qualifier, configuration)", "getQualifierValue(qualifier, metrics)")

    s = re.sub(
        r"DimenCalculationPlumbing\.readScreenDp\(configuration,",
        "DimenCalculationPlumbing.readScreenDp(metrics,",
        s,
    )
    s = re.sub(
        r"DimenCalculationPlumbing\.isMultiWindowConstrained\(configuration,\s*ignoreMultiWindows,\s*context\)",
        "DimenCalculationPlumbing.isMultiWindowConstrained(metrics, ignoreMultiWindows)",
        s,
    )
    s = re.sub(
        r"DimenCalculationPlumbing\.isMultiWindowConstrained\(configuration,\s*ignoreMultiWindows,\s*context\s*\)",
        "DimenCalculationPlumbing.isMultiWindowConstrained(metrics, ignoreMultiWindows)",
        s,
    )
    s = re.sub(
        r"DimenCalculationPlumbing\.aspectRatioMultiplier\(configuration,",
        "DimenCalculationPlumbing.aspectRatioMultiplier(metrics,",
        s,
    )

    s = re.sub(
        r"calculateScaledDp\(([^,]+),\s*configuration,",
        r"calculateScaledDp(\1, metrics,",
        s,
    )
    s = re.sub(
        r",\s*context\)",
        ", ctx)",
        s,
    )
    s = re.sub(
        r",\s*context\s*\)",
        ", ctx)",
        s,
    )

    s = re.sub(
        r"invalidateOnConfigChange\(old,\s*new\)",
        "invalidateOnMetricsChange(old?.let { it }, new)",
        s,
    )

    # UiModeType.fromConfiguration -> keep for android only — common should use ctx.currentUiMode()
    s = re.sub(
        r"UiModeType\.fromConfiguration\(context[^)]*\)",
        "ctx.currentUiMode()",
        s,
    )

    return s


def main():
    for path in sorted(ROOT.rglob("*.kt")):
        text = path.read_text(encoding="utf-8")
        if "android." in text or "Context" in text or "Configuration" in text:
            new = migrate_text(text)
            if new != text:
                path.write_text(new, encoding="utf-8")
                print("updated", path.relative_to(ROOT))


if __name__ == "__main__":
    main()
