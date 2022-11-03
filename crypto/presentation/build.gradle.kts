plugins {
    id("com.android.library")
    id("app.cash.paparazzi")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.compose")
    id("dev.ohoussein.cryptoapp.android.library")
    id("dev.ohoussein.cryptoapp.jacoco")
}

android {
    namespace = "dev.ohoussein.cryptoapp.crypto.presentation"

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(path = ":crypto:domain"))
    implementation(project(path = ":core:common"))
    implementation(project(path = ":core:designsystem"))
    implementation(project(path = ":core:formatter"))
    implementation(project(path = ":core:cached-data"))
    androidTestImplementation(project(path = ":core:injection"))

    // Presentation
    implementation(libs.android.lifecycle.extensions)
    implementation(libs.core.kotlin.coroutines.core)

    implementation(libs.android.compose.material.icons)
    implementation(libs.android.compose.hilt.navigation)
    implementation(libs.android.compose.activity)
    implementation(libs.android.compose.coil)
    implementation(libs.android.compose.accompanist.swiperefresh)
    implementation(libs.android.compose.navigation)

    implementation(libs.android.lifecycle.viewmodel)
    implementation(libs.android.lifecycle.compose)
    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.process)
    implementation(libs.android.lifecycle.extensions)

    implementation(libs.core.timber)
    implementation(project(path = ":core:injection:core"))

    implementation(libs.core.dagger.hilt)
    kapt(libs.core.hilt.compiler)
    kapt(libs.core.dagger.hilt.android.compiler)

    testImplementation(project(path = ":core:test"))
    testImplementation(libs.test.mockito.inline)
    testRuntimeOnly(libs.test.junitVintage.engine)
    testImplementation(libs.test.parameterInjector)

    androidTestImplementation(project(path = ":crypto:data"))
    androidTestImplementation(project(path = ":core:test"))
    androidTestImplementation(libs.test.hilt)
    kaptAndroidTest(libs.core.hilt.compiler)

    androidTestImplementation(libs.test.android.junit)
    androidTestImplementation(libs.test.android.rules)
    androidTestImplementation(libs.test.mockito.android)
    androidTestImplementation(libs.test.bytebuddy)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.android.compose)
    androidTestImplementation(libs.test.android.composeManifest)
}
