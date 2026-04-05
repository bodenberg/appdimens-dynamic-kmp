import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose)
}

val enableIosTargets = OperatingSystem.current().isMacOsX

kotlin {
    android {
        namespace = "com.appdimens.dynamic.sample.cmp.shared"
        compileSdk = 36
        minSdk = 25
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    if (enableIosTargets) {
        iosArm64 {
            binaries.framework {
                baseName = "ComposeApp"
                isStatic = true
            }
        }
        iosSimulatorArm64 {
            binaries.framework {
                baseName = "ComposeApp"
                isStatic = true
            }
        }
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(project(":library"))
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        if (enableIosTargets) {
            getByName("iosMain").dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.appdimens.dynamic.sample.cmp.MainKt"
    }
}
