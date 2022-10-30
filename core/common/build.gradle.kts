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

    implementation(libs.android.compose.material)
    implementation(libs.android.lifecycle.viewmodel)
    implementation(libs.core.timber)

    implementation(libs.core.dagger.hilt)
    kapt(libs.core.hilt.compiler)
    kapt(libs.core.dagger.hilt.android.compiler)

    testImplementation(project(path = ":core:test"))
    testImplementation(libs.test.mockito.inline)

    //Robolectric
    testImplementation(libs.test.robolectric)
    testImplementation(libs.test.junitVintage.engine)
    testImplementation(libs.test.junit)
    testImplementation(libs.test.android.coreKtx)
}