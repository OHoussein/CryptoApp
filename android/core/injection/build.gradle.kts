plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.library")
}

android {
    namespace = "dev.ohoussein.cryptoapp.core.injection"

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":android:crypto:presentation"))
    implementation(project(":android:core:common"))
    implementation(project(":shared:core:sharedModules"))
    implementation(libs.koin.core)

    testImplementation(libs.test.kotest.runner)
    testImplementation(libs.test.kotest.assertions)
    testImplementation(libs.test.mockito.kotlin)
}
