import com.android.build.gradle.LibraryExtension
import dev.ohoussein.cryptoapp.IOSTargetVersions
import dev.ohoussein.cryptoapp.SdkVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.native.cocoapods")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()
                iosArm64()
                iosX64()
                iosSimulatorArm64()
                jvm("desktop")

                applyDefaultHierarchyTemplate()

                this.cocoapods {
                    homepage = "https://github.com/OHoussein/android-ios-kmm-crypto-app"
                    ios.deploymentTarget = IOSTargetVersions.DEPLOYMENT_TARGET
                    framework {
                        isStatic = false
                    }
                }
            }

            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = SdkVersion.TARGET_SDK_VERSION
                configureKotlinAndroid(this)
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

    private fun KotlinMultiplatformExtension.cocoapods(block: CocoapodsExtension.() -> Unit) {
        (this as ExtensionAware).extensions.configure("cocoapods", block)
    }
}
