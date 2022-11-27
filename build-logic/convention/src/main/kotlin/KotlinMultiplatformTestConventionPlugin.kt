import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformTestConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.getByName("commonTest").dependencies {
                    implementation(libs.findLibrary("test-kotest-multiplatform").get())
                    implementation(libs.findLibrary("test-kotest-assertions").get())
                    implementation(libs.findLibrary("test-turbine").get())
                    implementation(libs.findLibrary("test-mockk-common").get())
                }

                sourceSets.getByName("androidTest").dependencies {
                    implementation(libs.findLibrary("test-mockk-core").get())
                    implementation(libs.findLibrary("test-kotest-runner").get())
                }
            }

            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
            }
        }
    }
}
