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
include(":crypto:shared-domain")
include(":crypto:presentation")
include(":core:test")
include(":core:common")
include(":core:injection")
include(":core:designsystem")
include(":crypto:shared-domain")
include(":data:shared-cache")
include(":core:shared-formatter")
include(":data:shared-network")
include(":crypto:shared-data")
include(":data:shared-database")
