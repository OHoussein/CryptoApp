plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/androidModule.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.core.formatter"

    testOptions {
        tasks.withType<Test> {
            useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(CoreLibs.hiltAndroid)
    kapt(CoreLibs.hiltCompiler)
    kapt(CoreLibs.hiltAndroidCompiler)

    TestLibs.kotest.forEach { testImplementation(it) }
    testImplementation(TestLibs.mockito)
}