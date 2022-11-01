plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.app")
}

android {
    buildTypes {
        @Suppress("UNUSED_VARIABLE")
        val debug by getting {
            applicationIdSuffix = ".debug"
        }

        @Suppress("UNUSED_VARIABLE")
        val release by getting {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // TO-DO signing keys
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(path = ":data:network"))
    implementation(project(path = ":data:database"))
    implementation(project(path = ":crypto:presentation"))
    implementation(project(path = ":core:injection"))

    // Common
    implementation(libs.core.kotlin.coroutines.core)
    implementation(libs.core.kotlin.coroutines.android)
    implementation(libs.core.timber)

    implementation(libs.core.dagger.hilt)
    kapt(libs.core.hilt.compiler)
    kapt(libs.core.dagger.hilt.android.compiler)

    // Presentation
    implementation(libs.android.appcompat)
    implementation(libs.android.material)

    // Debug tools
    implementation(libs.debug.stetho)
    implementation(libs.debug.stethoOkhttp)
    implementation(libs.debug.okhttp.logging)
}
