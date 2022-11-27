import com.android.build.gradle.LibraryExtension
import dev.ohoussein.cryptoapp.IOSTargetVersions
import dev.ohoussein.cryptoapp.SdkVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.native.cocoapods")
                apply {
                    withPlugin("io.kotest.multiplatform") {
                        version = libs.findVersion("kotestMultiplatform").get()
                    }
                }
            }

            extensions.configure<KotlinMultiplatformExtension> {
                android()
                this.cocoapods {
                    homepage = "https://github.com/OHoussein/android-crypto-app"
                    ios.deploymentTarget = IOSTargetVersions.DEPLOYMENT_TARGET
                }
            }

            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = SdkVersion.TARGET_SDK_VERSION
                configureKotlinAndroid(this)
            }
        }
    }

    private fun KotlinMultiplatformExtension.cocoapods(block: CocoapodsExtension.() -> Unit) {
        (this as ExtensionAware).extensions.configure("cocoapods", block)
    }
}
