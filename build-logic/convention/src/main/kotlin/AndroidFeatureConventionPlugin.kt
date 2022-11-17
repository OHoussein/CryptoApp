import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", libs.findLibrary("koin-android-compose").get())
                add("implementation", libs.findLibrary("koin-android-navigation").get())
                add("androidTestImplementation", libs.findLibrary("koin-test").get())
                add("androidTestImplementation", libs.findLibrary("koin-junit4").get())
            }
        }
    }
}