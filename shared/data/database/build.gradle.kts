plugins {
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.test")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("CryptoDB") {
        packageName = "dev.ohoussein.cryptoapp.database"
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
        val commonMain by getting {
            dependencies {
                implementation(project(":shared:crypto:domain"))
                implementation(libs.core.kotlin.coroutines.core)
                implementation(libs.data.sqldelight.ext.coroutines)
                implementation(libs.koin.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.test.coroutines)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation("com.squareup.sqldelight:sqlite-driver:1.5.3")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.data.sqldelight.android)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.data.sqldelight.native)
            }
        }
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.data.database"
}
