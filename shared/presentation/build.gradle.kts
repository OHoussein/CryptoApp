plugins {
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Presentation"
            isStatic = true
            binaryOption("bundleId", "dev.ohoussein.cryptoapp")
            binaryOption("bundleVersion", "1.0.0")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.android.compose.activity)

            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.compose.navigation)

            implementation(libs.koin.compose)
            implementation(libs.koin.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)

            implementation(project(":shared:crypto:presentation"))
            implementation(project(":shared:designsystem"))

            // For DI injection
            implementation(project(":shared:core:formatter"))
            implementation(project(":shared:core:router"))
            implementation(project(":shared:data:database"))
            implementation(project(":shared:data:network"))
            implementation(project(":shared:crypto:domain"))
            implementation(project(":shared:crypto:data"))
        }

        androidUnitTest.dependencies {
            implementation(libs.koin.test)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.core.kotlin.coroutines.swing)
        }

        desktopTest.dependencies {
            implementation(libs.koin.test)
        }
    }
}
