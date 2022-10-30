plugins {
    id("com.android.library")
    id("app.cash.paparazzi")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply(from = "$rootDir/androidModule.gradle")
apply(from = "$rootDir/gradle/scripts/detekt.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.crypto.presentation"

    buildFeatures {
        compose = true
    }

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

    //Presentation
    implementation(CoreLibs.coroutinesCore)
    AndroidLibs.compose.forEach { implementation(it) }
    AndroidLibs.lifecycle.forEach {
        implementation(it)
    }
    implementation(CoreLibs.timber)
    implementation(project(path = ":core:injection:core"))

    implementation(CoreLibs.hiltAndroid)
    kapt(CoreLibs.hiltCompiler)
    kapt(CoreLibs.hiltAndroidCompiler)

    testImplementation(project(path= ":core:test"))
    testImplementation(TestLibs.mockito)
    testImplementation(TestLibs.mockitoInline)
    testImplementation(TestLibs.coroutinesTest)
    TestLibs.kotest.forEach {
        testImplementation(it)
    }
    testImplementation(TestLibs.turbine)
    testRuntimeOnly(TestLibs.junitVintage)
    testImplementation(TestLibs.testParameterInjector)

    androidTestImplementation(project(path = ":crypto:data"))
    androidTestImplementation(project(path = ":core:test"))
    androidTestImplementation(AndroidTestLibs.hiltTesting)
    kaptAndroidTest(CoreLibs.hiltCompiler)

    AndroidTestLibs.androidJunit.forEach {
        androidTestImplementation(it)
    }
    androidTestImplementation(AndroidTestLibs.rules)
    androidTestImplementation(AndroidTestLibs.espresso)
    androidTestImplementation(TestLibs.mockito)
    androidTestImplementation(AndroidTestLibs.mockitoAndroid)
    androidTestImplementation(TestLibs.byteBuddy)
    androidTestImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.coroutinesTest)
    androidTestImplementation(AndroidTestLibs.composeTesting)
    debugImplementation(AndroidTestLibs.composeTestingManifest)
}