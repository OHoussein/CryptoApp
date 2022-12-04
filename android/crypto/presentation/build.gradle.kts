plugins {
    id("com.android.library")
    id("app.cash.paparazzi")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.android.compose")
    id("dev.ohoussein.cryptoapp.android.library")
    id("dev.ohoussein.cryptoapp.jacoco")
    id("dev.ohoussein.cryptoapp.feature")
    id("dev.ohoussein.cryptoapp.koin")
}

android {
    namespace = "dev.ohoussein.cryptoapp.crypto.presentation"

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":shared:crypto:domain"))
    implementation(project(":shared:crypto:data"))
    implementation(project(":android:core:common"))
    implementation(project(":android:core:designsystem"))
    implementation(project(":shared:core:formatter"))
    androidTestImplementation(project(":android:core:injection"))

    // Presentation
    implementation(libs.android.lifecycle.extensions)
    implementation(libs.core.kotlin.coroutines.core)

    implementation(libs.android.compose.material.icons)
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

    testImplementation(project(":android:core:test"))
    testImplementation(libs.test.mockito.inline)
    testRuntimeOnly(libs.test.junitVintage.engine)
    testImplementation(libs.test.parameterInjector)
    androidTestImplementation(project(":shared:crypto:data"))

    androidTestImplementation(project(":android:core:test"))

    androidTestImplementation(libs.test.android.junit)
    androidTestImplementation(libs.test.android.rules)
    androidTestImplementation(libs.test.mockito.android)
    androidTestImplementation(libs.test.bytebuddy)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.android.compose)
    androidTestImplementation(libs.test.android.composeManifest)

/*    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.koin.junit4)*/
}
