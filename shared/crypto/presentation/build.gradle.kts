plugins {
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(libs.compose.navigation)
            implementation(libs.compose.lifecycle)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)

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
