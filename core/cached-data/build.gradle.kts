plugins {
    id("kotlin")
}

apply(from= "$rootDir/gradle/scripts/detekt.gradle")
apply(from= "$rootDir/gradle/jacoco.gradle")

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
