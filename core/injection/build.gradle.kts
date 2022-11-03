plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.library")
    id("dev.ohoussein.cryptoapp.jacoco")
}

android {
    namespace = "dev.ohoussein.cryptoapp.core.injection"
}

dependencies {
    implementation(project(path = ":crypto:domain"))
    implementation(project(path = ":crypto:data"))
    implementation(project(path = ":core:formatter"))
    implementation(project(path = ":core:injection:core"))

    api(libs.core.dagger.hilt)
    kapt(libs.core.hilt.compiler)
    kapt(libs.core.dagger.hilt.android.compiler)
}
