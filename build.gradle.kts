plugins {
    id("org.jetbrains.kotlinx.kover") version libs.versions.kover
}

apply(plugin = "com.github.ben-manes.versions")

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.build.android.gradlePlugin)
        classpath(libs.build.kotlinPlugin)
        classpath(libs.build.gradleVersionsPlugin)
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
    delete("build")
}

tasks.register<Copy>("installGitHook") {
    from(File(rootProject.rootDir, "scripts/pre-commit.sh")) {
        rename { it.removeSuffix(".sh") }
    }
    into(File(rootProject.rootDir, ".git/hooks"))
    fileMode = 0b111_111_111
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        val version = candidate.version
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        isStable.not()
    }
}

task("e2eTests", Exec::class) {
    group = "Verification"
    dependsOn(":android:app:installRelease")
    workingDir = file("$projectDir/e2e_tests")
    commandLine = listOf("pipenv", "run", "python3", "crypto.py")
}

task("generateScreenshots", Exec::class) {
    group = "Tool"
    dependsOn(":android:app:installRelease")
    workingDir = file("$projectDir/e2e_tests")
    commandLine = listOf("pipenv", "run", "python3", "crypto_screenshot_generate.py")
}


val ignoredCoverageModules = listOf(
    ":android:core:test",
)

allprojects {
    apply(plugin = "kover")
    if (ignoredCoverageModules.contains(project.name))
        return@allprojects
    if (extensions.findByType<kotlinx.kover.api.KoverProjectConfig>() != null) {
        extensions.configure<kotlinx.kover.api.KoverProjectConfig> {
            isDisabled.set(false)
        }
    }
}


koverMerged {
    enable()

    filters {
        classes {
            excludes += listOf(
                "*.ui.components.*",
                "*.designsystem.*",
                "*.activity.*",
                "**Activity",
                "**App",
                "*Module*",
                "*.model.*",
                "*.debug.*",
                "*.BuildConfig",
                "*.R",
                "*.mock",
                "*.mocks",
            )
        }

        annotations {
            excludes += listOf(
                "*Generated",
                "Composable",
            )
        }

        projects {
            excludes += ignoredCoverageModules
        }
    }

    verify {
        onCheck.set(true)
        rule {
            isEnabled = true
            target = kotlinx.kover.api.VerificationTarget.ALL

            bound {
                minValue = 70
                counter = kotlinx.kover.api.CounterType.LINE
                valueType = kotlinx.kover.api.VerificationValueType.COVERED_PERCENTAGE
            }
        }
    }
}