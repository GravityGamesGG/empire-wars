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
    jvmToolchain(24)
}

tasks.test {
    useJUnitPlatform()
}

val copyResources by tasks.registering(Sync::class) {
    from(layout.buildDirectory.dir("resources/main"))
    into(layout.projectDirectory.dir("src/main/resources"))
    exclude("manifest.json")
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
    autoUpdateManifest.set(true)

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

    manifest {
        group = "GravityGames"
        name = "Empire"
        version = project.version.toString() // Auto-syncs with project version
        description = "A persistent war server pack"

        // Add authors
        author {
            name = "JohnyBro"
            email = "johnydu10@pm.me"
            url = "https://your-website.com"
        }

        // Or simply by name
        //author("AnotherContributor")

        website = "https://my-pack.com"
        serverVersion = "*"

        // Dependencies
        // dependency("RequiredPack", "1.0.0")
        // optionalDependency("NiceToHavePack", "*")

        // Plugin-specific
        main = "gravitygames.Empire"
        includesAssetPack = true
        disabledByDefault = false
    }
}
