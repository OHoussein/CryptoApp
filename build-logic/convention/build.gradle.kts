plugins {
    `kotlin-dsl`
}

group = "dev.ohoussein.cryptoapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_20
    targetCompatibility = JavaVersion.VERSION_20
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

        register("android-app-module") {
            id = "${namespace}.android.app"
            implementationClass = "AndroidAppConventionPlugin"
        }

        register("kotlinMultiplatformLibrary") {
            id = "${namespace}.kotlin.multiplatform.library"
            implementationClass = "KotlinMultiplatformLibraryConventionPlugin"
        }
    }
}