plugins {
    id("kotlin")
    id("dev.ohoussein.cryptoapp.kotlin.detekt")
    id("dev.ohoussein.cryptoapp.jacoco")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.core.kotlin.coroutines.core)

    testImplementation(libs.test.mockk.core)
    testImplementation(libs.test.kotest.runner)
    testImplementation(libs.test.kotest.assertions)
    testImplementation(libs.test.turbine)
}
