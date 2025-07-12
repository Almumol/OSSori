plugins {
    id("com.github.node-gradle.node") version "7.0.2"
}

node {
    version.set("18.18.0") // 원하는 Node.js 버전
    npmVersion.set("9.8.1")
    download.set(true)     // 시스템에 없어도 자동 설치
}

tasks.register<com.github.gradle.node.npm.task.NpmTask>("npmBuild") {
    dependsOn(tasks.npmInstall)
    workingDir.set(file("frontend"))
    args.set(listOf("run", "build"))
}
