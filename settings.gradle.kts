pluginManagement {
    repositories {
        maven("https://repo.smolder.fr/public/")
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "Empire"