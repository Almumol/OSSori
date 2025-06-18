plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "OSSori"
include(
    ":api",
    ":batch",
    ":core:domain",
    ":core:client",
    ":core:infra"
)
