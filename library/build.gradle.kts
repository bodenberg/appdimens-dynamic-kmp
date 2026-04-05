import com.vanniktech.maven.publish.KotlinMultiplatform
import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlinx.atomicfu)
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.dokka.jetbrains)
}

val isJitPack = System.getenv("JITPACK") == "true"
        || System.getenv("jitpack") == "true"
        || System.getenv("CI") == "true"
        || System.getenv("ci") == "true"

val enableIosTargets = OperatingSystem.current().isMacOsX

mavenPublishing {
    coordinates("io.github.bodenberg", "appdimens-dynamic", "4.0.0")
    configure(KotlinMultiplatform())
    pom {
        name.set("AppDimens Dynamic: KMP dp, sp, px")
        description.set("Kotlin Multiplatform responsive dimensions (Android, iOS, Desktop).")
        url.set("https://github.com/bodenberg/appdimens-dynamic")
        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("bodenberg")
                name.set("Jean Bodenberg")
                email.set("jean.bodenberg2@outlook.com")
            }
        }
        scm {
            connection.set("scm:git:github.com/bodenberg/appdimens-dynamic.git")
            developerConnection.set("scm:git:ssh://github.com/bodenberg/appdimens-dynamic.git")
            url.set("https://github.com/bodenberg/appdimens-dynamic")
        }
    }
    if (!isJitPack) {
        publishToMavenCentral()
        signAllPublications()
    }
}

kotlin {
    android {
        namespace = "com.appdimens.dynamic"
        compileSdk = 36
        minSdk = 24
        withHostTest {
            isIncludeAndroidResources = true
        }
        withDeviceTest {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
        optimization {
            consumerKeepRules.apply {
                publish = true
                file("consumer-rules.pro")
            }
        }
    }
    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    if (enableIosTargets) {
        iosArm64()
        iosSimulatorArm64()
    }
    applyDefaultHierarchyTemplate()

    sourceSets {
        all {
            languageSettings {
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlinx.coroutines.FlowPreview")
            }
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material3)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.window)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.androidx.compose.material.core)
            implementation(libs.androidx.compose.ui)
            implementation(libs.androidx.compose.runtime)
        }
        getByName("androidHostTest") {
            dependencies {
                implementation(libs.junit)
                implementation(libs.mockito.core)
                implementation(libs.mockito.kotlin)
            }
        }
        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.junit)
                implementation(libs.androidx.espresso.core)
            }
        }
        val desktopMain = getByName("desktopMain")
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

dependencies {
    dokkaPlugin(libs.android.documentation.plugin)
    add(
        "androidRuntimeClasspath",
        enforcedPlatform("androidx.compose:compose-bom:${libs.versions.composeBom.get()}"),
    )
}

dokka {
    dokkaPublications.html {
        moduleName.set("AppDimens Dynamic KMP")
        outputDirectory.set(layout.projectDirectory.dir("DOCUMENTATION_KMP"))
        suppressInheritedMembers.set(true)
    }
}
