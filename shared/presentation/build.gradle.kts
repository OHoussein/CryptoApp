plugins {
    id("dev.ohoussein.cryptoapp.kmp.compose.library")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}
dependencies {
    implementation(project(":shared:core:sharedModules"))
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

            // export(libs.decompose.core)
            // export("com.arkivanov.essenty:lifecycle:2.0.0-alpha02")
            // // For state preservation on Darwin targets
            // export("com.arkivanov.essenty:state-keeper:2.0.0-alpha02")
            // export("com.arkivanov.parcelize.darwin:runtime:2.0.0-alpha02")
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
            // api(libs.decompose.core)
            // implementation(libs.decompose.compose)
            implementation(libs.koin.compose)
            implementation(libs.koin.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)

            implementation(project(":shared:crypto:presentation"))
            implementation(project(":shared:designsystem"))
            implementation(project(":shared:core:sharedModules"))
            // implementation(project(":shared:data"))
            // implementation(project(":shared:router"))
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
