import dev.ohoussein.cryptoapp.SdkVersion
import dev.ohoussein.cryptoapp.getAndroidNameSpaceFromPath
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeSimulatorTest
import org.gradle.api.tasks.compile.JavaCompile
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
                androidLibrary {
                    namespace = getAndroidNameSpaceFromPath(PACKAGE, target.path)
                    compileSdk = SdkVersion.COMPILE_SDK_VERSION
                    minSdk = SdkVersion.MIN_SDK_VERSION
                    androidResources {
                        enable = true
                    }
                    // Creates the Android host (unit test) compilation so `androidUnitTest`
                    // source sets are connected. Without this the new KMP android library
                    // plugin leaves them unused.
                    withHostTest { }
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

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.getByName("commonTest").dependencies {
                    implementation(kotlin("test"))
                    implementation(libs.findLibrary("test.coroutines").get())
                    implementation(libs.findLibrary("test-turbine").get())
                }
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                    freeCompilerArgs.addAll(
                        "-Xexpect-actual-classes",
                    )
                }
            }

            tasks.withType<JavaCompile>().configureEach {
                sourceCompatibility = JavaVersion.VERSION_21.toString()
                targetCompatibility = JavaVersion.VERSION_21.toString()
            }

            afterEvaluate {
                tasks.withType<KotlinNativeSimulatorTest> {
                    device.set(iosDeviceId)
                }
            }

            tasks.register("unitTestAll") {
                dependsOn(
                    "cleanTestAndroidHostTest", "testAndroidHostTest",
                    "cleanDesktopTest", "desktopTest",
                    "cleanIosSimulatorArm64Test", "iosSimulatorArm64Test",
                )
            }
        }
    }
}
