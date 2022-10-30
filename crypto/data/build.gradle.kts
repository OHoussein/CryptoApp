plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/androidModule.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.crypto.data"
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(path = ":crypto:domain"))
    implementation(project(path = ":data:database"))
    implementation(project(path = ":core:injection:core"))
    implementation(project(path = ":core:cached-data"))
    implementation(project(path = ":data:cache"))

    implementation(CoreLibs.coroutinesCore)

    DataLibs.retrofit.forEach { implementation(it) }
    implementation(DataLibs.roomLib)
    implementation(DataLibs.roomRuntime)
    annotationProcessor(DataLibs.roomCompiler)
    kapt(DataLibs.roomCompiler)

    implementation(CoreLibs.hiltAndroid)
    implementation(AndroidTestLibs.hiltTesting)
    kapt(CoreLibs.hiltAndroidCompiler)

    testImplementation(project(path = ":core:test"))
    testImplementation(project(path = ":data:network"))
    testImplementation(project(path = ":data:database"))
    testImplementation(TestLibs.mockWebserver)

    testImplementation(TestLibs.coroutinesTest)
    TestLibs.kotest.forEach { testImplementation(it) }
    testImplementation(TestLibs.mockito)
    testImplementation(TestLibs.mockitoInline)
    testImplementation(TestLibs.turbine)
}