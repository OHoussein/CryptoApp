plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
}

apply(from = "$rootDir/androidModule.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.data.database"

    testOptions {
        tasks.withType<Test> {
            useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(project(path = ":crypto:domain"))

    implementation(libs.core.dagger.hilt)
    implementation(libs.test.hilt)
    kapt(libs.core.dagger.hilt.android.compiler)

    implementation(libs.data.room.ktx)
    kapt(libs.data.room.compiler)
    annotationProcessor(libs.data.room.compiler)

    testImplementation(project(path = ":core:test"))
    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito.inline)

    testImplementation(libs.test.robolectric)
    testImplementation(libs.test.android.room)
    testImplementation(libs.test.android.coreKtx)
    testImplementation(libs.test.android.arch.core)
}
