plugins {
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.test")
    id("app.cash.sqldelight")
}

sqldelight {
    databases {
        create("CryptoDB") {
            packageName.set("dev.ohoussein.cryptoapp.database")
        }
    }
}

kotlin {
    cocoapods {
        summary = "Database module"
        version = "1.0"
        framework {
            baseName = "database"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared:crypto:cryptoDomain"))
            implementation(libs.core.kotlin.coroutines.core)
            implementation(libs.data.sqldelight.ext.coroutines)
            implementation(libs.koin.core)
        }

        commonTest.dependencies {
            implementation(libs.test.coroutines)
        }

        androidUnitTest.dependencies {
            implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
        }

        androidMain.dependencies {
            implementation(libs.data.sqldelight.android)
        }

        iosMain.dependencies {
            implementation(libs.data.sqldelight.native)
        }

        desktopMain.dependencies {
            implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
        }
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.data.database"
}
