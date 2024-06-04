plugins {
    id("com.android.application")
    id("kotlin-android")

    id("dev.ohoussein.cryptoapp.android.app")
}

android {
    namespace = "dev.ohoussein.cryptoapp"

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // TO-DO signing keys
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(project(":shared:presentation"))
    // Common
    implementation(libs.core.kotlin.coroutines.core)
    implementation(libs.koin.android)
    implementation(libs.core.kotlin.coroutines.android)

    // Presentation
    implementation(libs.android.appcompat)
    implementation(libs.android.compose.activity)
    implementation(libs.android.material)
}
