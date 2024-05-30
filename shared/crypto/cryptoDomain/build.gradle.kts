plugins {
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.test")
}

kotlin {
    cocoapods {
        summary = "Crypto shared domain module"
        version = "1.0"
        framework {
            baseName = "cryptoDomain"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared:core:coroutinesTools"))
            implementation(libs.core.kotlin.coroutines.core)
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.test.coroutines)
        }
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.crypto.shareddomain"
}
