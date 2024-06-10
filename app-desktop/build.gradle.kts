import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
}

group = "dev.ohoussein.cryptoapp"
version = "1.0.0"

compose.desktop {
    application {
        mainClass = "dev.ohoussein.cryptoapp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.ohoussein.cryptoapp"
            packageVersion = "1.0.0"
            macOS {
                iconFile.set(project.file("src/main/resources/icon/app_icon.ico"))
            }
            windows {
                iconFile.set(project.file("src/main/resources/icon/app_icon.ico"))
            }
            linux {
                iconFile.set(project.file("src/main/resources/icon/app_icon.ico"))
            }
        }
    }
}

dependencies {
    implementation(project(":shared:presentation"))
    implementation(compose.runtime)
}
