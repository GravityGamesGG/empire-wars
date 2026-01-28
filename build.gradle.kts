plugins {
    id("com.gradleup.shadow") version "9.3.0"
    kotlin("jvm") version "2.3.0"
    idea
    // https://github.com/MrMineO5/HytaleGradlePlugin
    id("app.ultradev.hytalegradle") version "2.0.1"
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(25)
}

hytale {
    // Add `--allow-op` to server args (allows you to run `/op self` in-game)
    allowOp.set(true)

    // Set the patchline to use, currently there are "release" and "pre-release"
    patchline.set("pre-release")

    // Load mods from the local Hytale installation
    includeLocalMods.set(false)

    // Replace the version in the manifest with the project version
    manifest {
        version.set(project.version.toString())
    }
}

tasks.named("build") {
    dependsOn(tasks.shadowJar)
}