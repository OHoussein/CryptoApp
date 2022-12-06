plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.robolectric")
    id("dev.ohoussein.cryptoapp.android.compose")
    id("dev.ohoussein.cryptoapp.android.library")
    id("dev.ohoussein.cryptoapp.koin")
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
    implementation(libs.android.compose.material)
    implementation(libs.android.lifecycle.viewmodel)
    implementation(libs.core.timber)

    testImplementation(project(":android:core:test"))
    testImplementation(libs.test.mockito.inline)
}
