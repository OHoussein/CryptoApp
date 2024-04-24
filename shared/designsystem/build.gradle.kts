plugins {
    id("dev.ohoussein.cryptoapp.kmp.compose.library")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "designSystem"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)

            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.ui.tooling.preview)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.koin.compose)
            implementation(libs.koin.core)
            implementation(libs.coil.compose)
        }
    }
}

compose.experimental {
    web.application {}
}
