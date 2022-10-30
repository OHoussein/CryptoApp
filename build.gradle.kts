apply(plugin = "com.github.ben-manes.versions")

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${BuildLibs.androidGradlePlugin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${CoreLibs.kotlinVersion}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${BuildLibs.daggerHiltVersion}")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.42.0")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${BuildLibs.detektVersion}")
        classpath("io.gitlab.arturbosch.detekt:detekt-formatting:${BuildLibs.detektVersion}")
        classpath("org.jacoco:org.jacoco.core:${BuildLibs.jacocoVersion}")
        classpath("app.cash.paparazzi:paparazzi-gradle-plugin:1.0.0")
    }
}

subprojects {
    configurations.all {
        resolutionStrategy {
            eachDependency {
                if (requested.group == "org.jacoco") {
                    useVersion(BuildLibs.jacocoVersion)
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