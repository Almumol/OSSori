plugins {
    kotlin("jvm") version "1.9.25"
}

allprojects {
    group = "com.almumol"
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

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

