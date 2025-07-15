plugins {
    kotlin("jvm") version "1.9.25" apply false
    kotlin("plugin.jpa") version "1.9.25" apply false
    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.4.5" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
    group = "almumol.ossori"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    dependencies {
        "compileOnly"("org.projectlombok:lombok")
        "annotationProcessor"("org.projectlombok:lombok")
        "implementation"("org.springframework.boot:spring-boot-starter-actuator")
        "testImplementation"("org.springframework.boot:spring-boot-starter-test")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test-junit5")
        "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
    }
}
