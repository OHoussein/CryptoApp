plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dev.ohoussein.cryptoapp.android.library")
    id("dev.ohoussein.cryptoapp.jacoco")
    id("dev.ohoussein.cryptoapp.koin")
}

android {
    namespace = "dev.ohoussein.cryptoapp.data.network"
}

dependencies {
    implementation(libs.core.kotlin.coroutines.core)
    implementation(libs.data.retrofit.lib)
    implementation(libs.data.retrofit.moshi)

    implementation(libs.data.room.ktx)
    kapt(libs.data.room.compiler)
    annotationProcessor(libs.data.room.compiler)

    testImplementation(libs.test.robolectric)

    implementation(libs.debug.okhttp.logging)
}
