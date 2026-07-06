plugins {
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling)
            implementation(libs.composeMp.uiToolingPreview)
        }
        commonMain.dependencies {
            implementation(libs.composeMp.runtime)
            implementation(libs.composeMp.foundation)
            implementation(libs.composeMp.material)
            implementation(libs.composeMp.materialIconsExtended)
            implementation(libs.composeMp.ui)
            implementation(libs.composeMp.components.resources)
            implementation(libs.composeMp.components.uiToolingPreview)
            implementation(libs.koin.compose)
            implementation(libs.koin.core)
            implementation(libs.coil.compose)
        }
    }
}
