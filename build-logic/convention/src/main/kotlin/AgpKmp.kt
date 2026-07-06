import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * AGP 9 removed the `androidLibrary { }` convenience extension on [KotlinMultiplatformExtension].
 * This restores it by looking the android target up directly from the Kotlin extension, keeping
 * the call site identical to the one described in the AGP 9 KMP migration guide.
 */
internal fun KotlinMultiplatformExtension.androidLibrary(
    action: KotlinMultiplatformAndroidLibraryTarget.() -> Unit,
) = (this as ExtensionAware).extensions
    .getByType<KotlinMultiplatformAndroidLibraryTarget>()
    .action()
