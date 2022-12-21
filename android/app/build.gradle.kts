plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.app")
    id("dev.ohoussein.cryptoapp.koin")
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
    implementation(project(path = ":android:crypto:presentation"))
    implementation(project(path = ":android:core:injection"))
    // Common
    implementation(libs.core.kotlin.coroutines.core)
    implementation(libs.core.kotlin.coroutines.android)
    implementation(libs.core.timber)

    // Presentation
    implementation(libs.android.appcompat)
    implementation(libs.android.material)

    testImplementation(libs.koin.test)
    testImplementation(libs.koin.junit4)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.test.mockito.inline)
    testImplementation(libs.test.android.arch.core)
    testImplementation(libs.test.coroutines)
}
