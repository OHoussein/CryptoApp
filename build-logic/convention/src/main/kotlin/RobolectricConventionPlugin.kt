import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class RobolectricConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                testOptions.unitTests.isIncludeAndroidResources = true
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("testImplementation", libs.findLibrary("test.robolectric").get())
                add("testRuntimeOnly", libs.findLibrary("test.junitVintage.engine").get())
                add("testImplementation", libs.findLibrary("test.junit").get())
                add("testImplementation", libs.findLibrary("test.android.coreKtx").get())
            }
        }
    }
}