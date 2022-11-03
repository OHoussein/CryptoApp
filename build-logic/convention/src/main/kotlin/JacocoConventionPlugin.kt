import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.gradle.jacoco")
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            configure<JacocoPluginExtension> {
                toolVersion = libs.findVersion("jacoco").get().toString()
            }
            val androidLibExtension = extensions.findByType<LibraryAndroidComponentsExtension>()
            androidLibExtension?.let {
                configureJacocoForAndroidModule(it)
            } ?: configureJacocoForKotlinModule()
        }
    }
}

private val coverageExclusions = listOf(
    //app
    "**/model/**",
    "**/presentation/components/**",
    "**/activity/**",
    "**/debug/**",
    "**/R.class",
    "**/R$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*",
    "**/Lambda$*.class",
    "**/Lambda.class",
    "**/*Lambda.class",
    "**/*Lambda*.class",
    "**/*_MembersInjector.class",
    "**/Dagger*Component*.*",
    "**/*Module_*Factory.class",
    "**/di/module/*",
    "**/*_Factory*.*",
    "**/*_GeneratedInjector*.*",
    "**/*Module*.*",
    "**/*Dagger*.*",
    "**/*Hilt*.*",
    // kotlin
    "**/BuildConfig.*",
    "**/*Component*.*",
    "**/*BR*.*",
    "**/Manifest*.*",
    "**/*\$Lambda$*.*",
    "**/*Companion*.*",
    "**/*Module*.*",
    "**/*Dagger*.*",
    "**/*Hilt*.*",
    "**/*MembersInjector*.*",
    "**/*_MembersInjector.class",
    "**/*_Factory*.*",
    "**/*_Provide*Factory*.*",
    "**/*Extensions*.*"
)

private fun Project.configureJacocoForAndroidModule(
    androidComponentsExtension: AndroidComponentsExtension<*, *, *>,
) {
    val jacocoTestReport = tasks.create("jacocoTestReport")

    androidComponentsExtension.onVariants { variant ->
        val testTaskName = "test${variant.name.capitalize()}UnitTest"
        val reportTask = tasks.register("jacoco${testTaskName.capitalize()}Report", JacocoReport::class) {
            group = "Verification"
            dependsOn(testTaskName)
            configReport()
            classDirectories.setFrom(
                fileTree("$buildDir/tmp/kotlin-classes/${variant.name}") {
                    exclude(coverageExclusions)
                }
            )

            sourceDirectories.setFrom(sourceDirectoriesFiles())
            executionData.setFrom(file("$buildDir/jacoco/$testTaskName.exec"))
        }

        jacocoTestReport.dependsOn(reportTask)
    }

    tasks.withType<Test>().configureEach {
        configure<JacocoTaskExtension> {
            // Required for JaCoCo + Robolectric
            isIncludeNoLocationClasses = true
            excludes = listOf("jdk.internal.*")
        }
    }
}

private fun Project.configureJacocoForKotlinModule() {
    tasks.withType<JacocoReport> {
        dependsOn("test")
        group = "Verification"
        configReport()
        val from = files(classDirectories.files.map {
            fileTree(it) {
                exclude(coverageExclusions)
            }
        })
        classDirectories.setFrom(from)
        sourceDirectories.setFrom(sourceDirectoriesFiles())
        executionData.setFrom(file("$buildDir/jacoco/test.exec"))
    }
}

private fun JacocoReport.configReport() {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

private fun Project.sourceDirectoriesFiles() = files("$projectDir/src/main/java", "$projectDir/src/main/kotlin")
