plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.robolectric")
    id("dev.ohoussein.cryptoapp.android.library")
    id("dev.ohoussein.cryptoapp.jacoco")
}

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
    testImplementation(libs.test.android.arch.core)
}
