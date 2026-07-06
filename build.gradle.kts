plugins {
    id("org.jetbrains.kotlinx.kover") version libs.versions.kover
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.build.android.gradlePlugin)
        classpath(libs.build.kotlinPlugin)
        classpath(libs.build.detekt.plugin)
        classpath(libs.build.detekt.formatting)
        classpath(libs.build.paparazzi)
        classpath(libs.build.sqldelight)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

tasks.register("clean").configure {
    group = "build"
    description = "Deletes the root project build directory."
    delete("build")
}

tasks.register<Exec>("e2eTests") {
    group = "Verification"
    description = "Run e2e test on a connected device"
    workingDir = file("$projectDir/e2e_tests")
    commandLine = listOf("bash", "test_android.sh")
}

tasks.register<Exec>("generateScreenshots") {
    group = "Tool"
    description = "Generate screenshots on a connected device"
    workingDir = file("$projectDir/e2e_tests")
    commandLine = listOf("bash", "generate_screenshots.sh")
}



subprojects {
    pluginManager.apply("org.jetbrains.kotlinx.kover")
}

// Aggregate coverage of every module into the root project's merged report.
dependencies {
    subprojects.forEach { kover(project(it.path)) }
}

kover {
    reports {
        filters {
            excludes {
                classes(
                    "*.ui.components.*",
                    "*.designsystem.*",
                    "*.activity.*",
                    "*Activity",
                    "*App",
                    "*Module*",
                    "*.model.*",
                    "*.debug.*",
                    "*.BuildConfig",
                    "*.R",
                    "*.mock",
                    "*.mocks",
                )
                annotatedBy("*Generated", "*Composable")
            }
        }

        verify {
            // LINE unit and COVERED_PERCENTAGE aggregation are the defaults.
            rule {
                minBound(60)
            }
        }
    }
}

tasks.register("check") {
    group = "verification"
    description = "Runs the aggregated Kover coverage verification (koverVerify)."
    dependsOn("koverVerify")
}