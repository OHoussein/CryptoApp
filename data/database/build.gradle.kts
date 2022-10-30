plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/androidModule.gradle")


android {
    namespace = "dev.ohoussein.cryptoapp.data.database"

    testOptions {
        tasks.withType<Test> {
            useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(project(path = ":crypto:domain"))

    implementation(CoreLibs.hiltAndroid)
    implementation(AndroidTestLibs.hiltTesting)
    kapt(CoreLibs.hiltAndroidCompiler)

    implementation(DataLibs.roomLib)
    kapt(DataLibs.roomCompiler)
    annotationProcessor(DataLibs.roomCompiler)

    testImplementation(project(path = ":core:test"))
    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.coroutinesTest)
    testImplementation(TestLibs.mockito)
    testImplementation(TestLibs.mockitoInline)

    testImplementation(TestLibs.robolectric)
    testImplementation(AndroidTestLibs.roomTestHelper)
    testImplementation(AndroidTestLibs.testCoreKtx)
    testImplementation(TestLibs.archCoreTesting)
}
