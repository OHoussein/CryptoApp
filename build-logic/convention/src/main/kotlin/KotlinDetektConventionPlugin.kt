import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

class KotlinDetektConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("io.gitlab.arturbosch.detekt")
            }

            extensions.configure<DetektExtension> {
                source.from(projectDir)
                config.from("$rootDir/config/detekt.yml")
                buildUponDefaultConfig = true
                autoCorrect = project.hasProperty("detektAutoFix")
            }

            tasks.withType<Detekt>().configureEach {
                reports {
                    html.required.set(true)
                    txt.required.set(true)
                    sarif.required.set(false)
                    xml.required.set(false)
                    html.outputLocation.set(file("build/reports/detekt.html"))
                    txt.outputLocation.set(file("build/reports/detekt.txt"))
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("detektPlugins", libs.findLibrary("build.detekt.formatting").get())
            }
        }
    }
}
