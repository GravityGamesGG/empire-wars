import fr.smolder.hytale.gradle.Patchline

plugins {
    kotlin("jvm") version "2.3.0"
    idea
    // https://github.com/GhostRider584/hytale-gradle-plugin
    id("fr.smolder.hytale.dev") version "0.1.0"
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}

val copyResources by tasks.registering(Sync::class) {
    from(layout.buildDirectory.dir("resources/main"))
    into(layout.projectDirectory.dir("src/main/resources"))
}

tasks.named("runServer") {
    finalizedBy(copyResources)
}

hytale {
    // Optional: Override Hytale installation path (defaults to OS-specific standard location)
    // hytalePath.set("...")

    // Optional: patch line (defaults to "release")
    patchLine.set(Patchline.RELEASE)

    // Optional: game version (defaults to "latest")
    gameVersion.set("latest")

    // Auto-update manifest.json during build? (defaults to true)
    autoUpdateManifest.set(false)

    // Memory configuration
    minMemory.set("2G")
    maxMemory.set("4G")

    // Use AOT cache for faster startup (defaults to true)
    useAotCache.set(true)

    // You can add extra server arguments
    // serverArgs.add("--allow-op")

    // Decompilation settings
    vineflowerVersion.set("1.11.2")
    decompileFilter.set(listOf("com/hypixel/**"))
    decompilerHeapSize.set("6G")

    // Automatically attach decompiled sources to IDE (defaults to true)
    includeDecompiledSources.set(true)
}
