@file:Suppress("unused")

object AppVersion {
    const val applicationId = "dev.ohoussein.cryptoapp"
    const val versionCode = 1
    const val versionName = "1.0"
}

object BuildPlugins {
    object Versions {
        const val androidGradlePlugin = "7.3.0-beta04"
        const val kotlinVersion = "1.6.21"
        const val daggerHiltVersion = "2.42"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val hiltGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHiltVersion}"
    const val gradleVersionsTrackerPlugin = "com.github.ben-manes:gradle-versions-plugin:+"
    const val testLoggerPlugin = "com.adarshr:gradle-test-logger-plugin:2.1.1"
    const val detektPlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.17.1"
}

object AndroidSdk {
    const val minSdk = 21
    const val compileSdk = 32
    const val targetSdk = compileSdk
}

object Libs {
    object Versions {
        const val retrofit = "2.9.0"
        const val daggerHiltJetpack = "1.0.0"
        const val lifecycle = "2.3.1"
        const val coroutines = "1.5.1"
        const val fragment = "1.3.5"
        const val room = "2.4.2"

        const val stetho = "1.6.0"

        const val androidXTest = "1.4.0"
        const val androidJUnit = "1.1.3"
        const val espresso = "3.4.0"
        const val mockito = "2.2.0"

        const val compose = "1.2.0-rc02"
    }

    const val kotlinStdlib =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${BuildPlugins.Versions.kotlinVersion}"
    const val appcompat = "androidx.appcompat:appcompat:1.3.1"
    const val material = "com.google.android.material:material:1.4.0"
    const val annotation = "androidx.annotation:annotation:1.2.0"

    const val retofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retofitConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    const val hiltAndroid =
        "com.google.dagger:hilt-android:${BuildPlugins.Versions.daggerHiltVersion}"
    const val hiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${BuildPlugins.Versions.daggerHiltVersion}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.daggerHiltJetpack}"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val composeUI = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composeMaterialIconExtended =
        "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val composeUITooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeActivity = "androidx.activity:activity-compose:1.3.0"
    const val composeLivedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val composeHiltViewModel = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha02"
    const val composeNavigation = "androidx.navigation:navigation-compose:2.4.0-alpha05"
    const val composeCoil = "io.coil-kt:coil-compose:1.3.0"
    const val composeSwipeRefresh = "com.google.accompanist:accompanist-swiperefresh:0.15.0"

    const val room = "androidx.room:room-ktx:${Versions.room}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
}

object DebugLibs {
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:4.9.1"
    const val stetho = "com.facebook.stetho:stetho:${Libs.Versions.stetho}"
    const val stethoOkhttp = "com.facebook.stetho:stetho-okhttp3:${Libs.Versions.stetho}"
}

//Android and unit tests
object TestLibs {
    const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"
    const val hiltAndroid =
        "com.google.dagger:hilt-android-testing:${BuildPlugins.Versions.daggerHiltVersion}"
    const val hiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${BuildPlugins.Versions.daggerHiltVersion}"
    const val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Libs.Versions.mockito}"
    const val mockitoInline = "org.mockito:mockito-inline:3.11.2"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Libs.Versions.coroutines}"
    const val junit = "junit:junit:4.13.2"
    const val mockWebserver = "com.squareup.okhttp3:mockwebserver:4.9.1"
    const val robolectric = "org.robolectric:robolectric:4.6.1"
}

object AndroidTestLibs {
    const val testCore = "androidx.test:core:${Libs.Versions.androidXTest}"
    const val testCoreKtx = "androidx.test:core-ktx:${Libs.Versions.androidXTest}"

    const val androidJUnit = "androidx.test.ext:junit:${Libs.Versions.androidJUnit}"
    const val androidJUnitKtx = "androidx.test.ext:junit-ktx:${Libs.Versions.androidJUnit}"

    const val rules = "androidx.test:rules:1.4.0"
    const val espresso = "androidx.test.espresso:espresso-core:${Libs.Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Libs.Versions.espresso}"
    const val mockitoAndroid = "org.mockito:mockito-android:3.11.2"
    const val okhttpIdling = "com.jakewharton.espresso:okhttp3-idling-resource:1.0.0"

    const val composeTesting = "androidx.compose.ui:ui-test-junit4:${Libs.Versions.compose}"
    const val composeTestingManifest =
        "androidx.compose.ui:ui-test-manifest:${Libs.Versions.compose}"

    const val roomTestHelper = "androidx.room:room-testing:${Libs.Versions.room}"

}
