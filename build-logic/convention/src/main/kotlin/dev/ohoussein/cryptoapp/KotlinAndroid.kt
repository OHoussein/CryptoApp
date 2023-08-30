import com.android.build.api.dsl.CommonExtension
import dev.ohoussein.cryptoapp.SdkVersion
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) = commonExtension.apply {

    compileSdk = SdkVersion.COMPILE_SDK_VERSION
    with(defaultConfig) {
        minSdk = SdkVersion.MIN_SDK_VERSION
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<KotlinJvmCompile>().configureEach {
        kotlinOptions {
            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()

            jvmTarget = JavaVersion.VERSION_17.toString()

            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            )
        }
    }

    packaging {
        resources.excludes += arrayOf(
            "**/attach_hotspot_windows.dll",
            "META-INF/*",
            "META-INF/licenses/**",
        )
    }
}
