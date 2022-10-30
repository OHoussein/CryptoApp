plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/androidModule.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.core.test"
}

dependencies {
    implementation(project(path = ":crypto:domain"))
    //TODO check those deps
    implementation(libs.android.appcompat)
    implementation(libs.core.kotlin.coroutines.core)

    implementation(libs.data.retrofit.lib)
    implementation(libs.test.okhttp.mockServer)

    implementation(libs.core.dagger.hilt)
    implementation(libs.test.hilt)
    kapt(libs.core.hilt.compiler)
    kapt(libs.core.dagger.hilt.android.compiler)

    implementation(libs.test.junit)
    api(libs.test.coroutines)
    api(libs.test.mockito.kotlin)
    api(libs.test.kotest.runner)
    api(libs.test.kotest.assertions)
    api(libs.test.turbine)
    implementation(libs.test.android.arch.core)

    implementation(libs.test.android.rules)
}