import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeSimulatorTest

private const val iosDeviceId = "iPhone 15 Pro"

class KotlinMultiplatformTestConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.getByName("commonTest").dependencies {
                    implementation(kotlin("test"))
                    implementation(kotlin("test-annotations-common"))
                    implementation(kotlin("test-common"))
                    implementation(libs.findLibrary("test-turbine").get())
                }

                sourceSets.getByName("androidUnitTest").dependencies {
                    implementation(libs.findLibrary("test-mockk-core").get())
                    implementation(libs.findLibrary("test-junit").get())
                    implementation(libs.findLibrary("test-mockk-common").get())
                }
            }

            afterEvaluate {
                tasks.withType<KotlinNativeSimulatorTest>() {
                    device.set(iosDeviceId)
                }
            }
        }
    }
}
