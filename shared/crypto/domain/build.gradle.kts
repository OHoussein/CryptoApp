plugins {
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.core.kotlin.coroutines.core)
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.test.coroutines)
        }
    }
}
