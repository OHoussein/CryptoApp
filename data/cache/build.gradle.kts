plugins {
    id("kotlin")
}

apply(from = "$rootDir/gradle/scripts/detekt.gradle")
apply(from = "$rootDir/gradle/jacoco.gradle")

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation(CoreLibs.coroutinesCore)
    implementation(project(path = ":core:cached-data"))

    testImplementation(TestLibs.mockito)
    testImplementation(TestLibs.mockitoInline)
    TestLibs.kotest.forEach { testImplementation(it) }
    testImplementation(TestLibs.turbine)
}
