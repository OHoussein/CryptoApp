plugins {
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.test")
}

kotlin {
    cocoapods {
        summary = "Data cache module"
        version = "1.0"
        framework {
            baseName = "shared-cache"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.core.kotlin.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.test.coroutines)
            }
        }
        val androidMain by getting
        val androidTest by getting
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.data.cache"
}
