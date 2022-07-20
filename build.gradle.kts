// Top-level build file where you can add configuration options common to all sub-projects/modules.
apply("checkDependencies.gradle")

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath(BuildPlugins.hiltGradlePlugin)
        classpath(BuildPlugins.gradleVersionsTrackerPlugin)
        classpath(BuildPlugins.testLoggerPlugin)
        classpath(BuildPlugins.detektPlugin)
        classpath(BuildPlugins.detektFormatting)
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

tasks {
    withType<io.gitlab.arturbosch.detekt.Detekt> {
        this.jvmTarget = "1.8"
    }
}

tasks.register<Copy>("installGitHook") {
    from(File(rootProject.rootDir, "scripts/pre-commit.sh")) {
        rename { it.removeSuffix(".sh") }
    }
    into(File(rootProject.rootDir, ".git/hooks"))
    fileMode = 0b111_111_111
}

tasks.getByPath(":app:preBuild").dependsOn(":installGitHook")

