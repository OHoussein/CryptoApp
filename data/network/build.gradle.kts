plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/androidModule.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.data.network"
}

dependencies {
    implementation(CoreLibs.coroutinesCore)
    DataLibs.retrofit.forEach { implementation(it) }

    implementation(CoreLibs.hiltAndroid)
    implementation(AndroidTestLibs.hiltTesting)
    kapt(CoreLibs.hiltAndroidCompiler)

    implementation(DataLibs.roomLib)
    kapt(DataLibs.roomCompiler)
    annotationProcessor(DataLibs.roomCompiler)

    testImplementation(TestLibs.robolectric)
}
