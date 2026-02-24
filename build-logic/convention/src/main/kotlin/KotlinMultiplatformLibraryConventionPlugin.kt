import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.androidLibrary
import dev.ohoussein.cryptoapp.SdkVersion
import dev.ohoussein.cryptoapp.getAndroidNameSpaceFromPath
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeSimulatorTest
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private const val PACKAGE = "com.ohoussein"
private const val iosDeviceId = "iPhone 15 Pro"

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val warningsAsErrors = findProperty("warningsAsErrors") as? String

            with(pluginManager) {
                apply("com.android.kotlin.multiplatform.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("dev.ohoussein.cryptoapp.kotlin.detekt")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                // targets.withType<KotlinMultiplatformAndroidLibraryTarget>().configureEach {
                //     namespace = getAndroidNameSpaceFromPath(PACKAGE, target.path)
                //     compileSdk = SdkVersion.COMPILE_SDK_VERSION
                //     minSdk = SdkVersion.MIN_SDK_VERSION
                //     compilerOptions {
                //         allWarningsAsErrors.set(warningsAsErrors.toBoolean())
                //         jvmTarget.set(JvmTarget.JVM_20)
                //         freeCompilerArgs.addAll(
                //             "-Xexpect-actual-classes",
                //             "-opt-in=kotlin.RequiresOptIn",
                //             "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                //         )
                //     }
                // }

                androidLibrary {
                    namespace = getAndroidNameSpaceFromPath(PACKAGE, target.path)
                    compileSdk = SdkVersion.COMPILE_SDK_VERSION
                    minSdk = SdkVersion.MIN_SDK_VERSION
                }

                iosArm64()
                iosX64()
                iosSimulatorArm64()
                jvm("desktop")

                applyDefaultHierarchyTemplate()
                compilerOptions {
                    allWarningsAsErrors.set(warningsAsErrors.toBoolean())
                    freeCompilerArgs.addAll(
                        "-Xexpect-actual-classes",
                        "-opt-in=kotlin.RequiresOptIn",
                        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    )
                }
            }

            // extensions.configure<LibraryExtension> {
            //     namespace = getAndroidNameSpaceFromPath(PACKAGE, path)
            //     configureKotlinAndroid(this)
            // }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.getByName("commonTest").dependencies {
                    implementation(kotlin("test"))
                    implementation(kotlin("test-annotations-common"))
                    implementation(kotlin("test-common"))
                    implementation(libs.findLibrary("test.coroutines").get())
                    implementation(libs.findLibrary("test-turbine").get())
                }
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    freeCompilerArgs.addAll(
                        "-Xexpect-actual-classes",
                    )
                }
            }

            afterEvaluate {
                tasks.withType<KotlinNativeSimulatorTest> {
                    device.set(iosDeviceId)
                }
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
}
