plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply(from = "$rootDir/androidModule.gradle")

android {
    namespace = "dev.ohoussein.cryptoapp.crypto.data"
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(path = ":crypto:domain"))
    implementation(project(path = ":data:database"))
    implementation(project(path = ":core:injection:core"))
    implementation(project(path = ":core:cached-data"))
    implementation(project(path = ":data:cache"))

    implementation(libs.core.kotlin.coroutines.core)

    implementation(libs.data.retrofit.lib)
    implementation(libs.data.retrofit.moshi)
    implementation(libs.data.room.ktx)
    implementation(libs.data.room.runtime)
    annotationProcessor(libs.data.room.compiler)
    kapt(libs.data.room.compiler)

    implementation(libs.core.dagger.hilt)
    implementation(libs.test.hilt)
    kapt(libs.core.dagger.hilt.android.compiler)

    testImplementation(project(path = ":core:test"))
    testImplementation(project(path = ":data:network"))
    testImplementation(project(path = ":data:database"))
    testImplementation(libs.test.okhttp.mockServer)
    testImplementation(libs.test.mockito.inline)
}