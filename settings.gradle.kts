plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "OSSori"
include(
    ":module-api",
    ":module-batch",
    ":module-core:module-domain",
    ":module-core:module-client",
    ":module-core:module-infra"
)
