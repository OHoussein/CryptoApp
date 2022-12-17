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
            baseName = "cryptoData"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.core.kotlin.coroutines.core)
                implementation(libs.koin.core)
                implementation(project(":shared:data:network"))
                implementation(project(":shared:data:database"))
                implementation(project(":shared:data:cache"))
                implementation(project(":shared:crypto:cryptoDomain"))
            }
        }
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.crypto.data"
}
