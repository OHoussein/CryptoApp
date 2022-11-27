plugins {
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.test")
    id("dev.ohoussein.cryptoapp.koin")
}

kotlin {
    cocoapods {
        summary = "Crypto shared domain module"
        version = "1.0"
        framework {
            baseName = "shared-domain"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.core.kotlin.coroutines.core)
            }
        }
        val commonTest by getting
        val androidMain by getting
        val androidTest by getting
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.crypto.shareddomain"
}
