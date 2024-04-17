plugins {
    id("dev.ohoussein.cryptoapp.kmp.compose.library")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.koin.core)
            implementation(libs.coil.compose)
        }
    }
}
