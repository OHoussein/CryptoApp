pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}
rootProject.name = "cryptoapp"

include(":app-android")
include(":app-desktop")
include(":shared:data:cache")
include(":shared:data:network")
include(":shared:data:database")
include(":shared:crypto:domain")
include(":shared:crypto:data")
include(":shared:core:formatter")
include(":shared:core:router")
include(":shared:presentation")
include(":shared:designsystem")
include("shared:crypto:presentation")
findProject(":shared:crypto:presentation")?.name = "presentation"
