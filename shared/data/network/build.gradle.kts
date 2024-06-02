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
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.data.ktor.core)
            implementation(libs.data.ktor.logging)
            implementation(libs.data.ktor.content.negotiation)
            implementation(libs.data.ktor.json)
        }

        commonTest.dependencies {
            implementation(libs.data.ktor.mock)
        }

        androidMain.dependencies {
            implementation(libs.data.ktor.android)
        }

        iosMain.dependencies {
            implementation(libs.data.ktor.ios)
        }

        desktopMain.dependencies {
            implementation(libs.data.ktor.apache)
        }
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.data.network"
}
