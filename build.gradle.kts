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
        classpath(libs.build.hilt)
        classpath(libs.build.gradleVersionsPlugin)
        classpath(libs.build.detekt.plugin)
        classpath(libs.build.detekt.formatting)
        classpath(libs.build.jacoco)
        classpath(libs.build.paparazzi)
    }
}

subprojects {
    configurations.all {
        resolutionStrategy {
            eachDependency {
                if (requested.group == "org.jacoco") {
                    useVersion(libs.versions.jacoco.get())
                }
            }
        }
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

tasks.getByPath(":app:preBuild").dependsOn(":installGitHook")

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
    dependsOn(":app:installRelease")
    workingDir = file("$projectDir/e2e_tests")
    commandLine = listOf("pipenv", "run", "python3", "crypto.py")
}

task("generateScreenshots", Exec::class) {
    group = "Tool"
    dependsOn(":app:installRelease")
    workingDir = file("$projectDir/e2e_tests")
    commandLine = listOf("pipenv", "run", "python3", "crypto_screenshot_generate.py")
}
