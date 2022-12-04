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

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":android:crypto:presentation"))
    implementation(project(":shared:crypto:domain"))
    implementation(project(":shared:crypto:data"))
    implementation(project(":shared:core:formatter"))
    implementation(project(":android:core:common"))
    implementation(project(":shared:data:database"))
    implementation(project(":shared:data:network"))
    implementation(libs.koin.core)

    testImplementation(libs.test.kotest.runner)
    testImplementation(libs.test.kotest.assertions)
    testImplementation(libs.test.mockito.kotlin)
}
