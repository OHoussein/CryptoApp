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
    private val iosTargets = setOf(
        "iosArm64",
        "iosX64",
        "iosSimulatorArm64"
    )

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

                this.cocoapods {
                    homepage = "https://github.com/OHoussein/android-ios-kmm-crypto-app"
                    ios.deploymentTarget = IOSTargetVersions.DEPLOYMENT_TARGET
                    framework {
                        isStatic = false
                    }
                }

                val commonMain = sourceSets.getByName("commonMain")
                val commonTest = sourceSets.getByName("commonTest")

                val iosMain = sourceSets.create("iosMain") {
                    dependsOn(commonMain)
                }
                val iosTest = sourceSets.create("iosTest") {
                    dependsOn(commonTest)
                }

                iosTargets.map { "${it}Main" }.forEach { sourceSets.getByName(it).dependsOn(iosMain) }
                iosTargets.map { "${it}Test" }.forEach { sourceSets.getByName(it).dependsOn(iosTest) }
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
