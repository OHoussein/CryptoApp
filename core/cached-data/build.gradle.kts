plugins {
    id("kotlin")
    id("dev.ohoussein.cryptoapp.jacoco")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
