plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dev.ohoussein.cryptoapp.android.library")
    id("dev.ohoussein.cryptoapp.jacoco")
    id("dev.ohoussein.cryptoapp.koin")
}

android {
    namespace = "dev.ohoussein.cryptoapp.crypto.data"
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(path = ":crypto:domain"))
    implementation(project(path = ":data:database"))
    implementation(project(path = ":core:formatter"))
    implementation(project(path = ":data:cache"))

    implementation(libs.core.kotlin.coroutines.core)

    implementation(libs.data.retrofit.lib)
    implementation(libs.data.retrofit.moshi)
    implementation(libs.data.room.ktx)
    implementation(libs.data.room.runtime)
    annotationProcessor(libs.data.room.compiler)
    kapt(libs.data.room.compiler)

    testImplementation(project(path = ":core:test"))
    testImplementation(project(path = ":data:network"))
    testImplementation(project(path = ":data:database"))
    testImplementation(libs.test.okhttp.mockServer)
    testImplementation(libs.test.mockito.inline)
    testImplementation(libs.core.kotlin.reflect)
}