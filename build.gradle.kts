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
                    useVersion(libs.versions.jacocoVersion.get())
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

/*tasks.named("dependencyUpdates").configure {
    resolutionStrategy {
        componentSelection { rules ->
            rules.all {
                ComponentSelection selection ->
                boolean rejected =['alpha', 'beta', 'rc', 'cr', 'm'].any { qualifier ->
                    selection.candidate.version == "/ (?i).*[.-]${ qualifier }[.\ d -]"
                }
                if (rejected) {
                    selection.reject('Release candidate')
                }
            }
        }
    }
}

task e2eTests (type: Exec) {
    dependsOn(":app:installRelease")
    workingDir "$projectDir/e2e_tests"
    commandLine "pipenv", "run", "python3", "crypto.py"
}

task generateScreenshots (type: Exec) {
    dependsOn(":app:installRelease")
    workingDir "$projectDir/e2e_tests"
    commandLine "pipenv", "run", "python3", "crypto_screenshot_generate.py"
}

 */