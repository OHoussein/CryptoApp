plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/androidModule.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.core.designsystem"

    buildFeatures {
        compose = true
    }
}

dependencies {
    api(libs.android.compose.ui.core)
    api(libs.android.compose.ui.tooling)
    api(libs.android.compose.material)
}
