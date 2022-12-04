pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "cryptoapp"

include(":android")
include(":android:app")
include(":android:crypto:presentation")
include(":shared:crypto:domain")
include(":android:core:test")
include(":android:core:common")
include(":android:core:injection")
include(":android:core:designsystem")
include(":shared:crypto:domain")
include(":shared:data:cache")
include(":shared:core:formatter")
include(":shared:data:network")
include(":shared:crypto:data")
include(":shared:data:database")
