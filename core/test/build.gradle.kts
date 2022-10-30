plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/androidModule.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.core.test"
}

dependencies {
    implementation(project(path = ":crypto:domain"))
    //TODO check those deps
    implementation(AndroidLibs.appcompat)
    implementation(CoreLibs.coroutinesCore)

    DataLibs.retrofit.forEach { implementation(it) }

    implementation(CoreLibs.hiltAndroid)
    implementation(AndroidTestLibs.hiltTesting)
    kapt(CoreLibs.hiltCompiler)
    kapt(CoreLibs.hiltAndroidCompiler)

    implementation(TestLibs.coroutinesTest)
    implementation(TestLibs.junit)
    implementation(TestLibs.mockWebserver)
    implementation(TestLibs.mockito)
    TestLibs.kotest.forEach { implementation(it) }
    implementation(TestLibs.archCoreTesting)

    implementation(AndroidTestLibs.rules)
}