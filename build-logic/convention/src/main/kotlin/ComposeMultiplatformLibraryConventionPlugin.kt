import com.android.build.gradle.LibraryExtension
import dev.ohoussein.cryptoapp.getAndroidNameSpaceFromPath
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

const val PACKAGE = "com.ohoussein"

class ComposeMultiplatformLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()
                iosArm64()
                iosX64()
                iosSimulatorArm64()
                jvm("desktop")

                applyDefaultHierarchyTemplate()
            }

            extensions.configure<LibraryExtension> {
                namespace = getAndroidNameSpaceFromPath(PACKAGE, path)
                configureKotlinAndroid(this)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

                sourceSets.getByName("commonMain").dependencies {
                    implementation(libs.findLibrary("core.kotlin.coroutines.core").get())
                }
                sourceSets.getByName("commonTest").dependencies {
                    implementation(kotlin("test"))
                    implementation(kotlin("test-annotations-common"))
                    implementation(kotlin("test-common"))
                    implementation(libs.findLibrary("test.coroutines").get())
                }
            }

            task("unitTestAll") {
                setDependsOn(
                    listOf(
                        "cleanTestReleaseUnitTest", "testReleaseUnitTest",
                        "cleanDesktopTest", "desktopTest",
                        "cleanIosSimulatorArm64Test", "iosSimulatorArm64Test"
                    )
                )
            }
        }
    }
}
