plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":module-core:module-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("software.amazon.awssdk:s3:2.32.30")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}
