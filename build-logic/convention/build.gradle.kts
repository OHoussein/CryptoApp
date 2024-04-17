plugins {
    `kotlin-dsl`
}

group = "dev.ohoussein.cryptoapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.build.kotlinPlugin)
    compileOnly(libs.build.android.gradlePlugin)
    compileOnly(libs.build.detekt.plugin)
}

gradlePlugin {
    val namespace = "dev.ohoussein.cryptoapp"

    plugins {
        register("detekt") {
            id = "${namespace}.kotlin.detekt"
            implementationClass = "KotlinDetektConventionPlugin"
        }

        register("robolectric") {
            id = "${namespace}.android.robolectric"
            implementationClass = "RobolectricConventionPlugin"
        }

        register("compose") {
            id = "${namespace}.android.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("android-module") {
            id = "${namespace}.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("android-app-module") {
            id = "${namespace}.android.app"
            implementationClass = "AndroidAppConventionPlugin"
        }

        register("koin") {
            id = "${namespace}.koin"
            implementationClass = "KoinConventionPlugin"
        }

        register("feature-module") {
            id = "${namespace}.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("kotlinMultiplatformLibrary") {
            id = "${namespace}.kotlin.multiplatform.library"
            implementationClass = "KotlinMultiplatformLibraryConventionPlugin"
        }

        register("kotlinMultiplatformTest") {
            id = "${namespace}.kotlin.multiplatform.test"
            implementationClass = "KotlinMultiplatformTestConventionPlugin"
        }

        register("ComposeMultiplatformLibraryConventionPlugin") {
            id = "${namespace}.kmp.compose.library"
            implementationClass = "ComposeMultiplatformLibraryConventionPlugin"
        }
    }
}