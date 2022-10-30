plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/androidModule.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.core.injection"
}

dependencies {
    implementation(project(path = ":crypto:domain"))
    implementation(project(path = ":crypto:data"))
    implementation(project(path = ":core:formatter"))
    implementation(project(path = ":core:injection:core"))

    implementation(CoreLibs.hiltAndroid)
    kapt(CoreLibs.hiltCompiler)
    kapt(CoreLibs.hiltAndroidCompiler)
}