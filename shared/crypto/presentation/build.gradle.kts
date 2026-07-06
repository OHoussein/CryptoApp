plugins {
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.composeMp.runtime)
            implementation(libs.composeMp.foundation)
            implementation(libs.composeMp.material)
            implementation(libs.composeMp.ui)
            implementation(libs.composeMp.components.uiToolingPreview)
            implementation(libs.composeMp.materialIconsExtended)
            implementation(libs.composeMp.components.resources)
            implementation(libs.compose.navigation)
            implementation(libs.compose.lifecycle)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)
            implementation(libs.core.kotlin.datetime)

            implementation(project(":shared:designsystem"))
            implementation(project(":shared:crypto:domain"))
            implementation(project(":shared:crypto:data"))
            implementation(project(":shared:core:formatter"))
            implementation(project(":shared:core:router"))
        }

        commonTest.dependencies {
            implementation(libs.test.turbine)
        }
    }
}
