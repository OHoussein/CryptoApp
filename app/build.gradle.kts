plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/androidModule.gradle")

android {
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            //TO-DO signing keys
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources.excludes += arrayOf(
            "**/attach_hotspot_windows.dll",
            "META-INF/*", "META-INF/licenses/**",
        )
    }
}

allprojects {
    configurations.all {
        resolutionStrategy {
            force("org.objenesis:objenesis:2.6")
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(path = ":data:network"))
    implementation(project(path = ":data:database"))
    implementation(project(path = ":crypto:presentation"))
    implementation(project(path = ":core:injection"))

    //Common
    implementation(libs.core.kotlin.coroutines.core)
    implementation(libs.core.kotlin.coroutines.android)
    implementation(libs.core.timber)

    implementation(libs.core.dagger.hilt)
    kapt(libs.core.hilt.compiler)
    kapt(libs.core.dagger.hilt.android.compiler)

    //Presentation
    implementation(libs.android.appcompat)
    implementation(libs.android.material)

    //Debug tools
    implementation(libs.debug.stetho)
    implementation(libs.debug.stethoOkhttp)
    implementation(libs.debug.okhttp.logging)

}
