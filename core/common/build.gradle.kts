plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.robolectric")
    id("dev.ohoussein.cryptoapp.android.compose")
    id("dev.ohoussein.cryptoapp.android.library")
}

android {
    namespace = "dev.ohoussein.cryptoapp.core.common"

    testOptions {
        tasks.withType<Test> {
            useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(project(path = ":core:cached-data"))

    implementation(libs.android.compose.material)
    implementation(libs.android.lifecycle.viewmodel)
    implementation(libs.core.timber)

    implementation(libs.core.dagger.hilt)
    kapt(libs.core.hilt.compiler)
    kapt(libs.core.dagger.hilt.android.compiler)

    testImplementation(project(path = ":core:test"))
    testImplementation(libs.test.mockito.inline)
}
