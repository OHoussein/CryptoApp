plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.compose")
}

apply(from = "$rootDir/androidModule.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.core.designsystem"
}

dependencies {
    api(libs.android.compose.ui.core)
    api(libs.android.compose.ui.tooling)
    api(libs.android.compose.material)
}
