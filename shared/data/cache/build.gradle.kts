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
            baseName = "cache"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.core.kotlin.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.test.coroutines)
        }
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.data.cache"
}
