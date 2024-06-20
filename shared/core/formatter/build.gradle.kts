plugins {
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.core.kotlin.datetime)
        }
    }
}
