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

tasks.register("findDependentModules") {
    group = "dependency"
    description = "변경된 모듈에 의존하는 실행 모듈들을 출력합니다."

    val changedModules = project.findProperty("changedModules")
        ?.toString()
        ?.takeIf { it.isNotBlank() }
        ?.split(",")
        ?.map(String::trim)
        ?.map { if (it.startsWith(":")) it else ":$it" }
        ?: emptyList()

    doLast {
        val moduleGraph = mutableMapOf<String, MutableSet<String>>()

        rootProject.subprojects.forEach { proj ->
            proj.configurations
                .flatMap { it.dependencies }
                .filterIsInstance<ProjectDependency>()
                .forEach { dep ->
                    val dependents = moduleGraph.getOrPut(dep.path) { mutableSetOf() }
                    dependents.add(proj.path)
                }
        }

        println(moduleGraph)

        val visited = mutableSetOf<String>()
        val candidates = mutableSetOf<String>()

        fun traverse(module: String) {
            if (module in visited) return
            visited.add(module)
            moduleGraph[module]?.forEach {
                traverse(it)
            }
            candidates.add(module)
        }

        changedModules.forEach {
            traverse(it)
        }

        val executableModules = candidates.filter { module ->
            val project = rootProject.project(module)
            project.fileTree("src/main").any {
                it.name.endsWith("Application.kt")
            }
        }

        val candidatesString = candidates.joinToString(",")
        val executablesString = executableModules.joinToString(",")

        println("candidates=$candidatesString")
        println("executables=$executablesString")
    }
}
