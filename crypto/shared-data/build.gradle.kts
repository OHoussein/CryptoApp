plugins {
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.test")
}

kotlin {
    cocoapods {
        summary = "Shared crypto data network"
        version = "1.0"
        framework {
            baseName = "shared-data"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.core.kotlin.coroutines.core)
                implementation(libs.koin.core)
                implementation(project(":data:shared-network"))
                implementation(project(":data:shared-database"))
                implementation(project(":data:shared-cache"))
                implementation(project(":crypto:shared-domain"))
            }
        }
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.crypto.data"
}
