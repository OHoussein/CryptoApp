plugins {
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.test")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

kotlin {
    cocoapods {
        summary = "Network module"
        version = "1.0"
        framework {
            baseName = "network"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.data.ktor.core)
                implementation(libs.data.ktor.logging)
                implementation(libs.data.ktor.content.negotiation)
                implementation(libs.data.ktor.json)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.data.ktor.mock)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.data.ktor.android)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.data.ktor.ios)
            }
        }
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.data.network"
}
