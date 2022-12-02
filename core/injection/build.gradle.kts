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
    implementation(project(path = ":crypto:presentation"))
    implementation(project(":crypto:shared-domain"))
    implementation(project(path = ":crypto:data"))
    implementation(project(path = ":core:shared-formatter"))
    implementation(project(path = ":core:common"))
    implementation(project(path = ":data:database"))
    implementation(project(path = ":data:network"))
    implementation(libs.koin.core)

    testImplementation(libs.test.kotest.runner)
    testImplementation(libs.test.kotest.assertions)
    testImplementation(libs.test.mockito.kotlin)
}
