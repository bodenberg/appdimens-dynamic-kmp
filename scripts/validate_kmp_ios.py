#!/usr/bin/env python3
"""
Static checks for KMP iosMain (Linux/macOS) and optional Swift typecheck on macOS.
Exit 0 = no blocking issues; exit 1 = Kotlin iosMain violations.
"""
from __future__ import annotations

import re
import subprocess
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parents[1]

PBXPROJ_GLOB = "iosApp/**/*.pbxproj"
REQUIRED_XCODE_EMBED_SNIPPET = "embedAndSignAppleFrameworkForXcode"

FORBIDDEN_KT_IMPORTS = (
    "import android.",
    "import java.",
)

CINTEROP_MARKERS = ("kotlinx.cinterop", "platform.posix")


def iter_ios_main_kt() -> list[Path]:
    paths: list[Path] = []
    for p in REPO_ROOT.rglob("iosMain"):
        if p.is_dir():
            paths.extend(sorted(p.rglob("*.kt")))
    return paths


def check_ios_xcode_gradle_embed() -> list[str]:
    """Ensure checked-in Xcode projects wire Kotlin embed (static sanity, no swiftc)."""
    errors: list[str] = []
    for pbx in sorted(REPO_ROOT.glob(PBXPROJ_GLOB)):
        text = pbx.read_text(encoding="utf-8", errors="replace")
        if REQUIRED_XCODE_EMBED_SNIPPET not in text:
            errors.append(
                f"{pbx.relative_to(REPO_ROOT)}: missing shell script snippet "
                f"{REQUIRED_XCODE_EMBED_SNIPPET!r} (Gradle / Compose framework)"
            )
        if "ComposeApp" not in text:
            errors.append(
                f"{pbx.relative_to(REPO_ROOT)}: expected references to ComposeApp framework / linker flags"
            )
    return errors


def check_kotlin_ios_main() -> list[str]:
    errors: list[str] = []
    for path in iter_ios_main_kt():
        text = path.read_text(encoding="utf-8", errors="replace")
        for line in text.splitlines():
            s = line.strip()
            for bad in FORBIDDEN_KT_IMPORTS:
                if s.startswith(bad):
                    errors.append(f"{path.relative_to(REPO_ROOT)}: forbidden import ({bad.strip()})")
        if any(m in text for m in CINTEROP_MARKERS):
            if "@OptIn(ExperimentalForeignApi::class)" not in text and "ExperimentalForeignApi" not in text:
                errors.append(
                    f"{path.relative_to(REPO_ROOT)}: uses cinterop/posix; add @OptIn(ExperimentalForeignApi::class) at file or call site"
                )
    return errors


def check_swift() -> None:
    swift_files = sorted(REPO_ROOT.rglob("*.swift"))
    if not swift_files:
        print("validate_kmp_ios: no .swift files; skipping swiftc")
        return
    framework_root = REPO_ROOT / "composeApp" / "build" / "xcode-frameworks"
    has_compose_framework = framework_root.is_dir() and any(framework_root.rglob("ComposeApp.framework"))
    swiftc = "swiftc"
    try:
        subprocess.run([swiftc, "-version"], check=True, capture_output=True, text=True)
    except (OSError, subprocess.CalledProcessError):
        print("validate_kmp_ios: swiftc not available; skipping Swift typecheck")
        return
    for sf in swift_files:
        rel = sf.relative_to(REPO_ROOT)
        if not has_compose_framework:
            r = subprocess.run([swiftc, "-parse", str(sf)], capture_output=True, text=True)
            label = "swiftc -parse (syntax only; run :composeApp:embedAndSignAppleFrameworkForXcode for full typecheck)"
        else:
            r = subprocess.run([swiftc, "-typecheck", str(sf)], capture_output=True, text=True)
            label = "swiftc -typecheck"
        if r.returncode != 0:
            print(f"validate_kmp_ios: {label} failed for {rel}:\n{r.stderr or r.stdout}", file=sys.stderr)
            sys.exit(1)
        print(f"validate_kmp_ios: OK {rel} ({label})")


def main() -> None:
    kt_errors = check_kotlin_ios_main()
    if kt_errors:
        print("validate_kmp_ios: iosMain Kotlin issues:", file=sys.stderr)
        for e in kt_errors:
            print(f"  {e}", file=sys.stderr)
        sys.exit(1)
    print("validate_kmp_ios: iosMain Kotlin checks OK")
    xcode_errors = check_ios_xcode_gradle_embed()
    if xcode_errors:
        print("validate_kmp_ios: Xcode / pbxproj issues:", file=sys.stderr)
        for e in xcode_errors:
            print(f"  {e}", file=sys.stderr)
        sys.exit(1)
    print("validate_kmp_ios: iosApp Xcode project embed checks OK")
    check_swift()


if __name__ == "__main__":
    main()
