plugins {
    id("kotlin")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/gradle/jacoco.gradle")

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.core.kotlin.coroutines.core)
    implementation(project(path = ":core:cached-data"))

    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.test.mockito.inline)
    testImplementation(libs.test.kotest.runner)
    testImplementation(libs.test.kotest.assertions)
    testImplementation(libs.test.turbine)
}
