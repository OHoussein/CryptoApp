plugins {
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.library")
    id("dev.ohoussein.cryptoapp.kotlin.multiplatform.test")
}

kotlin {
    cocoapods {
        summary = "dependency injection module"
        version = "1.0"
        framework {
            baseName = "sharedModules"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)
                implementation(project(":shared:core:formatter"))
                implementation(project(":shared:core:router"))
                implementation(project(":shared:data:database"))
                implementation(project(":shared:data:network"))
                implementation(project(":shared:crypto:cryptoDomain"))
                implementation(project(":shared:crypto:cryptoData"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.koin.test)
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.koin.junit4)
                implementation(libs.test.mockk.common)
            }
        }
    }

    targets.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().forEach {
        it.binaries.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>()
            .forEach { lib ->
                lib.isStatic = false
                lib.linkerOpts.add("-lsqlite3")
            }
    }
}

android {
    namespace = "dev.ohoussein.cryptoapp.core.sharedmodules"
}
