import com.android.build.api.dsl.ApplicationExtension
import dev.ohoussein.cryptoapp.SdkVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidAppConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("dev.ohoussein.cryptoapp.kotlin.detekt")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = SdkVersion.TARGET_SDK_VERSION
                configureKotlinAndroid(this)
            }

            allprojects.forEach {
                it.configurations.all {
                    resolutionStrategy {
                        force("org.objenesis:objenesis:2.6")
                    }
                }
            }
        }
    }
}