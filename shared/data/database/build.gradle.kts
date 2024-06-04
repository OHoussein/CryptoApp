plugins {
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
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

        androidMain.dependencies {
            implementation(libs.data.sqldelight.android)
        }

        androidUnitTest.dependencies {
            implementation(libs.data.sqldelight.desktop)
        }

        iosMain.dependencies {
            implementation(libs.data.sqldelight.native)
        }

        desktopMain.dependencies {
            implementation(libs.data.sqldelight.desktop)
        }
    }
}
