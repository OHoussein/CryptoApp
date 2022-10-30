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
    implementation(CoreLibs.coroutinesCore)
    implementation(CoreLibs.coroutinesAndroid)
    implementation(CoreLibs.timber)

    implementation(CoreLibs.hiltAndroid)
    kapt(CoreLibs.hiltCompiler)
    kapt(CoreLibs.hiltAndroidCompiler)

    //Presentation
    implementation(AndroidLibs.appcompat)
    implementation(AndroidLibs.material)
    AndroidLibs.lifecycle.forEach { implementation(it) }
    AndroidLibs.compose.forEach { implementation(it) }

    //Data
    DataLibs.retrofit.forEach { implementation(it) }
    implementation(DataLibs.roomRuntime)

    //Debug tools
    AndroidLibs.debug.forEach { debugImplementation(it) }

    //Android tests;
    androidTestImplementation(AndroidTestLibs.hiltTesting)
    kaptAndroidTest(CoreLibs.hiltAndroidCompiler)
    AndroidTestLibs.androidJunit.forEach { androidTestImplementation(it) }
    androidTestImplementation(AndroidTestLibs.rules)
    androidTestImplementation(AndroidTestLibs.espresso)
    androidTestImplementation(AndroidTestLibs.mockitoAndroid)
    androidTestImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.coroutinesTest)
    androidTestImplementation(AndroidTestLibs.composeTesting)
    debugImplementation(AndroidTestLibs.composeTestingManifest)
    androidTestImplementation(project(path = ":core:test"))

    //Unit tests
    testImplementation(project(path = ":core:test"))
    testImplementation(TestLibs.mockito)
    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.archCoreTesting)
    testImplementation(TestLibs.coroutinesTest)
}
