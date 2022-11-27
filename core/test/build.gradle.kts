plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.library")
}

android {
    namespace = "dev.ohoussein.cryptoapp.core.test"
}

dependencies {
    implementation(project(":crypto:shared-domain"))
    implementation(project(path = ":core:injection"))
    implementation(libs.android.appcompat)
    implementation(libs.core.kotlin.coroutines.core)

    implementation(libs.data.retrofit.lib)
    implementation(libs.test.okhttp.mockServer)

    implementation(libs.test.junit)
    api(libs.test.coroutines)
    api(libs.test.mockito.kotlin)
    api(libs.test.kotest.runner)
    api(libs.test.kotest.assertions)
    api(libs.test.turbine)
    implementation(libs.test.android.arch.core)

    implementation(libs.test.android.rules)

    implementation(libs.koin.core)
    implementation(libs.koin.android.core)
}
