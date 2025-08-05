plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":module-core:module-domain"))
    implementation(project(":module-core:module-client"))
    implementation(project(":module-core:module-infra"))
    implementation(project(":module-batch"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("com.h2database:h2")
}

sourceSets {
    main {
        resources {
            srcDir(project(":module-core:module-client").file("src/main/resources"))
        }
    }
}


tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    mainClass.set("OssoriApplication")
}
