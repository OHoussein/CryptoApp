plugins {
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.core.kotlin.coroutines.core)
            implementation(libs.koin.core)
            implementation(project(":shared:data:network"))
            implementation(project(":shared:data:database"))
            implementation(project(":shared:data:cache"))
            implementation(project(":shared:crypto:cryptoDomain"))
        }
    }
}
