pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
        //id("org.jetbrains.compose").version(libs.)
}
rootProject.name = "cryptoapp"

include(":android")
 include(":android:app")
// include(":android:crypto:presentation")
// include(":android:core:test")
// include(":android:core:common")
// include(":android:core:injection")
// include(":android:core:designsystem")
include(":shared:data:cache")
include(":shared:data:network")
include(":shared:data:database")
include(":shared:crypto:cryptoDomain")
include(":shared:crypto:cryptoData")
include(":shared:core:formatter")
include(":shared:core:sharedModules")
include(":shared:core:coroutinesTools")
include(":shared:presentation")
include(":shared:designsystem")
include("shared:crypto:presentation")
findProject(":shared:crypto:presentation")?.name = "presentation"
