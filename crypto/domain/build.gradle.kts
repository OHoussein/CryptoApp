plugins {
    id("kotlin")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.jacoco")
    id("dev.ohoussein.cryptoapp.koin")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.core.kotlin.coroutines.core)

    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.test.mockito.inline)
    testImplementation(libs.test.kotest.runner)
    testImplementation(libs.test.kotest.assertions)
}
