plugins {
    id("dev.ohoussein.cryptoapp.kmp.compose.library")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
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
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.android.compose.activity)

            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.koin.android.core)
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
            implementation(project(":shared:crypto:cryptoDomain"))
            implementation(project(":shared:crypto:cryptoData"))
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.core.kotlin.coroutines.swing)
        }
    }
}

compose.experimental {
    web.application {}
}
