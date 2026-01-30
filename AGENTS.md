# AGENTS.md

Build/lint/test commands and coding style guidelines for the Empire Hytale mod.

## Overview

This is a Hytale server mod written in Kotlin using the Hytale SDK's ECS architecture. The codebase follows component-based patterns with registries for organizing functionality.

## Build & Run Commands

```bash
# Build the server pack (creates jar in build/libs/)
./gradlew build

# Build and run the development server
./gradlew runServer

# Clean build artifacts
./gradlew clean
```

**Note**: No test infrastructure currently exists. Tests should be added to `src/test/kotlin/`.

## Project Structure

```
src/main/kotlin/
├── gravitygames/
│   ├── commands/          # Command implementations
│   ├── component/          # Entity and block components
│   ├── listeners/          # Event listeners
│   ├── registries/         # Registration logic
│   ├── systems/            # ECS systems and event handlers
│   └── Interactions/       # Block interaction types
└── resources/
    └── Server/             # JSON configuration files
```

## Code Style Guidelines

### Formatting

- **Brace style**: Allman (opening braces on new lines)
- **Indentation**: 4 spaces (no tabs)
- **Line length**: ~120 characters
- **No trailing whitespace**

### Import Organization

Group imports in this order (separated by blank lines):
1. Hytale SDK imports
2. Project package imports
3. Java standard library imports

Example:
```kotlin
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.Message

import gravitygames.component.FactionEntityComponent
import gravitygames.registries.EmpireComponentRegistry

import java.util.Timer
```

### Naming Conventions

- **Classes/Objects**: PascalCase (e.g., `FactionAttitudeSystem`, `EmpireCommandRegistry`)
- **Methods/Properties**: camelCase (e.g., `registerCommands`, `executeAsync`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `LOGGER`, `CODEC`)
- **Companion objects**: Descriptive names in PascalCase (e.g., `companion object Registry`)
- **Suffix patterns**: `System`, `Component`, `Registry`, `Command`, `Listener`, `Interaction`

## Architecture Patterns

### Plugin Entry Point

```kotlin
class Empire(@Nonnull init: JavaPluginInit) : JavaPlugin(init) {
    companion object {
        val Logger = HytaleLogger.forEnclosingClass()
    }

    override fun setup() {
        EmpireCommandRegistry.registerCommands(commandRegistry)
        EmpireEventRegistry.registerEvents(eventRegistry)
        EmpireComponentRegistry.registerEntityComponents(entityStoreRegistry)
        EmpireEntityStoreRegistry.registerSystems(entityStoreRegistry)
        EmpireComponentRegistry.registerBlockComponents(chunkStoreRegistry)
        EmpireCodecRegistry.registerCodecs(this)
    }
}
```

### Registry Pattern

Create singleton objects for registration in separate files:

```kotlin
object EmpireComponentRegistry {
    lateinit var factionEntityComponentType: ComponentType<EntityStore?, FactionEntityComponent?>
    
    fun registerEntityComponents(registry: ComponentRegistryProxy<EntityStore>) {
        factionEntityComponentType = registry.registerComponent(
            FactionEntityComponent::class.java, ::FactionEntityComponent
        )
    }
}
```

### Component Example

```kotlin
package gravitygames.component

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import gravitygames.Faction

data class FactionEntityComponent(val faction: Faction = Faction.NONE) : Component<EntityStore?> {
    override fun clone(): Component<EntityStore?> = this.copy()
}
```

**Requirements for components**:
- Extend `Component<StoreType>` with appropriate store type
- Implement `clone()` returning a copy
- Use `data class`

### System Example

```kotlin
package gravitygames.systems

import com.hypixel.hytale.component.*
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap
import javax.annotation.Nonnull

class CarryWeightSystem : EntityTickingSystem<EntityStore>() {
    private val carryWeightStatIndex: Int by lazy { EntityStatType.getAssetMap().getIndex("CarryWeight") }

    override fun tick(dt: Float, index: Int, archetypeChunk: ArchetypeChunk<EntityStore>, 
                      store: Store<EntityStore>, commandBuffer: CommandBuffer<EntityStore>) {
        val statMap = archetypeChunk.getComponent(index, EntityStatMap.getComponentType()) ?: return
        val carryWeightStat = statMap.get(carryWeightStatIndex) ?: return
        
        // Process logic here
    }

    @Nonnull
    override fun getQuery(): Query<EntityStore> {
        return Query.and(Player.getComponentType(), EntityStatMap.getComponentType())
    }
}
```

**System types**:
- `EntityTickingSystem<EntityStore>` - ticks every frame
- `EntityEventSystem<EntityStore, EventType>` - handles events
- `StoreSystem<EntityStore>` - system-level logic

### Interaction Example

```kotlin
package gravitygames.Interactions

import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.inventory.ItemStack
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.math.vector.Vector3i

class SpawnGuardArcherInteraction : SimpleBlockInteraction() {
    companion object {
        val CODEC: BuilderCodec<SpawnGuardArcherInteraction> = BuilderCodec.builder(
            SpawnGuardArcherInteraction::class.java, ::SpawnGuardArcherInteraction, 
            SimpleBlockInteraction.CODEC
        ).build()
    }

    override fun interactWithBlock(
        world: World,
        commandBuffer: CommandBuffer<EntityStore?>,
        interactionType: InteractionType,
        interactionContext: InteractionContext,
        itemStack: ItemStack?,
        blockPosition: Vector3i,
        cooldownHandler: CooldownHandler
    ) {
        // Spawn or interact logic here
    }

    override fun simulateInteractWithBlock(
        var1: InteractionType, var2: InteractionContext, var3: ItemStack?, 
        var4: World, var5: Vector3i
    ) {
        // Implementation required by abstract parent
    }
}
```

### Command Pattern

Individual commands extend `AbstractAsyncPlayerCommand`:

```kotlin
class JoinFactionCommand : AbstractAsyncPlayerCommand("fjoin", "Join a faction", false) {
    private val factionNumber: RequiredArg<Int?> = this.withRequiredArg<Int?>(
        "factionNumber", "The faction number to join 1, 2 or 3", ArgTypes.INTEGER
    )

    override fun executeAsync(ctx: CommandContext, store: Store<EntityStore?>, 
                             ref: Ref<EntityStore?>, playerRefComponent: PlayerRef, world: World): CompletableFuture<Void?> {
        val faction = ctx.get<Int?>(factionNumber) ?: return CompletableFuture.completedFuture(null)
        
        if (faction !in 1..3) {
            ctx.sendMessage(Message.raw("Invalid faction number"))
            return CompletableFuture.completedFuture(null)
        }
        
        // Command logic
        return CompletableFuture.completedFuture(null)
    }
}
```

Command collections extend `AbstractCommandCollection`:

```kotlin
class FamilyCommands : AbstractCommandCollection("family", "Family management commands") {
    init {
        addSubCommand(FamilyCreateCommand())
        addSubCommand(FamilyJoinCommand())
    }
}
```

### Listener Pattern

Listeners are objects with static methods:

```kotlin
package gravitygames.listeners

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent

object PlayerReadyListener {
    fun onPlayerReady(event: PlayerReadyEvent) {
        val player = event.player
        player.sendMessage(Message.raw("Welcome " + player.displayName))
    }
}
```

## Nullability & Error Handling

- **Prefer early returns** over nested if/else
- Use Kotlin's safe call operator (`?.`) and Elvis operator (`?:`)
- Validate input and return early with error messages
- Use `?.let` blocks for operations on nullable references

```kotlin
store.getComponent(ref, componentType)?.let { component ->
    // Use component safely
    return CompletableFuture.completedFuture(null)
}

// Or early return pattern
val component = store.getComponent(ref, componentType) ?: return
```

## Resource Files

JSON resources follow Hytale's asset structure in `src/main/resources/Server/`:

- `Entity/` - Entity components, effects, stats
- `Item/` - Item definitions and interactions
- `NPC/` - NPC role configurations
- `Projectiles/` - Projectile definitions

Use Hytale's codec system rather than manual JSON parsing:

```kotlin
// In EmpireCodecRegistry
plugin.getCodecRegistry(Interaction.CODEC)
    .register("InteractionName", InteractionClass::class.java, InteractionClass.CODEC)
```

## Hytale SDK Usage Notes

- **ECS Architecture**: Entities are holders of components. Systems process components.
- **Store types**: `EntityStore` for entities, `ChunkStore` for blocks
- **Queries**: Define which components a system needs with `Query.and()` or `Query.or()`
- **ComponentTypes**: Always use lateinit in registries, accessed via registry fields
- **CommandBuffer**: Use for making changes to the world/state
- **Events**: Subscribe in `registerEvents()` via lambda wrappers
