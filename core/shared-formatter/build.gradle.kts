plugins {
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.test")
}

kotlin {
    cocoapods {
        summary = "Some description for the Shared Module"
        version = "1.0"
        framework {
            baseName = "shared-formatter"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)
            }
        }
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.core.formatter"
}
