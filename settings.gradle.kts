pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "cryptoapp"

include(":app")
include(":crypto:data")
include(":crypto:shared-domain")
include(":crypto:presentation")
include(":core:test")
include(":core:common")
include(":core:injection")
include(":core:designsystem")
include(":data:network")
include(":data:database")
include(":crypto:shared-domain")
include(":data:shared-cache")
include(":core:shared-formatter")
