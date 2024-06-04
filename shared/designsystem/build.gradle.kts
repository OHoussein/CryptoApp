plugins {
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
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
