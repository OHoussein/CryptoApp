plugins {
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.test")
}

kotlin {
    cocoapods {
        summary = "Formatter module"
        version = "1.0"
        framework {
            baseName = "formatter"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.core.formatter"
}
