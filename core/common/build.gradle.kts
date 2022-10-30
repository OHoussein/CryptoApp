plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/androidModule.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.core.common"

    buildFeatures {
        compose = true
    }

    testOptions {
        tasks.withType<Test> {
            useJUnitPlatform()
        }

        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(path = ":core:cached-data"))
    AndroidLibs.lifecycle.forEach { implementation(it) }
    AndroidLibs.compose.forEach { implementation(it) }
    implementation(CoreLibs.timber)

    implementation(CoreLibs.hiltAndroid)
    kapt(CoreLibs.hiltCompiler)
    kapt(CoreLibs.hiltAndroidCompiler)

    testImplementation(project(path = ":core:test"))
    testImplementation(TestLibs.turbine)
    TestLibs.kotest.forEach { testImplementation(it) }
    testImplementation(TestLibs.mockito)
    testImplementation(TestLibs.mockitoInline)

    //Robolectric
    testImplementation(TestLibs.robolectric)
    testImplementation(TestLibs.junitVintage)
    testImplementation(TestLibs.junit)
    testImplementation(AndroidTestLibs.testCore)
}