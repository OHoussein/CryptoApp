plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.library")
}

android {
    namespace = "dev.ohoussein.cryptoapp.core.formatter"

    testOptions {
        tasks.withType<Test> {
            useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(libs.core.dagger.hilt)
    kapt(libs.core.hilt.compiler)
    kapt(libs.core.dagger.hilt.android.compiler)

    testImplementation(libs.test.kotest.runner)
    testImplementation(libs.test.kotest.assertions)
    testImplementation(libs.test.mockito.kotlin)
}
