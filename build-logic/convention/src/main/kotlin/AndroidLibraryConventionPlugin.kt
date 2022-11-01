import com.android.build.gradle.LibraryExtension
import dev.ohoussein.cryptoapp.SdkVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                with(defaultConfig) {
                    targetSdk = SdkVersion.TARGET_SDK_VERSION
                    testInstrumentationRunner("dev.ohoussein.core.test.MyTestRunner")
                }
                configureKotlinAndroid(this)
            }
        }
    }
}
