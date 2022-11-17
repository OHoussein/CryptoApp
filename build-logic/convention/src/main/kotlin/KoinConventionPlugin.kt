import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType

class KoinConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            if (extensions.findByType<ApplicationAndroidComponentsExtension>() != null ||
                extensions.findByType<LibraryAndroidComponentsExtension>() != null
            ) {
                configureKoinForAndroidModule(libs)
            } else {
                configureKoinForKotlinModule(libs)
            }
        }
    }

    private fun Project.configureKoinForAndroidModule(
        libs: VersionCatalog,
    ) {
        dependencies {
            add("implementation", libs.findLibrary("koin-android-core").get())
        }
    }

    private fun Project.configureKoinForKotlinModule(
        libs: VersionCatalog,
    ) {
        dependencies {
            add("implementation", libs.findLibrary("koin-core").get())
        }
    }
}