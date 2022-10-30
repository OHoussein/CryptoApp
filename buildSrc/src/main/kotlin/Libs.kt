@file:Suppress("unused")

object CoreLibs {
    //Kotlin
    const val kotlinVersion = "1.7.10"
    const val coroutinesVersion = "1.6.4"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

    //hilt
    const val daggerHiltVersion = "2.43.2"
    private const val daggerHiltJetpackVersion = "1.0.0"
    const val hiltAndroid = "com.google.dagger:hilt-android:$daggerHiltVersion"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:$daggerHiltJetpackVersion"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$daggerHiltVersion"

    const val timber = "com.jakewharton.timber:timber:5.0.1"
}

object BuildLibs {
    const val androidGradlePlugin = "7.2.2"
    const val kotlinVersion = "1.7.10"
    const val daggerHiltVersion = "2.43.2"
    const val detektVersion = "1.21.0"
    const val jacocoVersion = "0.8.8"
}

object AndroidLibs {
    private const val lifecycleVersion = "2.6.0-alpha01"
    const val appcompat = "androidx.appcompat:appcompat:1.5.0"
    const val material = "com.google.android.material:material:1.6.1"
    const val composeVersion = "1.2.1"
    val lifecycle = listOf(
        "androidx.lifecycle:lifecycle-extensions:2.2.0",
        "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion",
        "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion",
        "androidx.lifecycle:lifecycle-process:$lifecycleVersion",
    )

    val compose = listOf(
        "androidx.compose.ui:ui:$composeVersion",
        "androidx.compose.material:material:$composeVersion",
        "androidx.compose.material:material-icons-extended:$composeVersion",
        "androidx.compose.ui:ui-tooling:$composeVersion",
        "androidx.activity:activity-compose:1.5.1",
        "androidx.hilt:hilt-navigation-compose:1.0.0",
        "androidx.navigation:navigation-compose:2.5.1",
        "io.coil-kt:coil-compose:2.1.0",
        "com.google.accompanist:accompanist-swiperefresh:0.25.1",
    )

    val debug = listOf(
        "com.facebook.stetho:stetho:1.6.0",
        "com.facebook.stetho:stetho-okhttp3:1.6.0",
        "com.squareup.okhttp3:logging-interceptor:4.10.0",
    )
}

object DataLibs {
    private const val retrofitVersion = "2.9.0"
    val retrofit = listOf(
        "com.squareup.retrofit2:retrofit:$retrofitVersion",
        "com.squareup.retrofit2:converter-moshi:$retrofitVersion",
    )

    //Room
    const val roomVersion = "2.4.3"
    const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
    const val roomLib = "androidx.room:room-ktx:$roomVersion"
    const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
}

object TestLibs {
    const val mockitoVersion = "4.0.0"
    const val koTestVersion = "5.4.2"

    const val junit = "junit:junit:4.13.2"
    const val mockito = "org.mockito.kotlin:mockito-kotlin:$mockitoVersion"
    const val mockitoInline = "org.mockito:mockito-inline:$mockitoVersion"
    const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${CoreLibs.coroutinesVersion}"
    const val mockWebserver = "com.squareup.okhttp3:mockwebserver:4.10.0"
    const val byteBuddy = "net.bytebuddy:byte-buddy:1.12.12"
    const val robolectric = "org.robolectric:robolectric:4.8.1"
    const val junitVintage = "org.junit.vintage:junit-vintage-engine:5.9.0"
    const val testParameterInjector = "com.google.testparameterinjector:test-parameter-injector:1.8"
    val kotest = listOf(
        "io.kotest:kotest-runner-junit5:$koTestVersion",
        "io.kotest:kotest-assertions-core:$koTestVersion",
        "org.jetbrains.kotlin:kotlin-reflect:${CoreLibs.kotlinVersion}",
    )
    const val turbine = "app.cash.turbine:turbine:0.9.0"
}

object AndroidTestLibs {
    private const val androidXTestVersion = "1.4.0"

    const val hiltTesting = "com.google.dagger:hilt-android-testing:${CoreLibs.daggerHiltVersion}"
    const val testCore = "androidx.test:core:$androidXTestVersion"
    const val testCoreKtx = "androidx.test:core-ktx:$androidXTestVersion"
    val androidJunit = listOf(
        "androidx.test.ext:junit:1.1.3",
        "androidx.test.ext:junit-ktx:1.1.3",
    )
    const val rules = "androidx.test:rules:1.4.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    const val mockitoAndroid = "org.mockito:mockito-android:${TestLibs.mockitoVersion}"
    const val composeTesting = "androidx.compose.ui:ui-test-junit4:${AndroidLibs.composeVersion}"
    const val composeTestingManifest = "androidx.compose.ui:ui-test-manifest:${AndroidLibs.composeVersion}"
    const val roomTestHelper = "androidx.room:room-testing:${DataLibs.roomVersion}"
}