package gravitygames.registries

import com.hypixel.hytale.component.ComponentRegistryProxy
import com.hypixel.hytale.component.ComponentType
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import gravitygames.component.FactionEntityComponent
import gravitygames.component.FamilyBlockComponent
import gravitygames.component.FamilyEntityComponent
import gravitygames.component.triggers.SetupFamilyOwnershipComponent

object EmpireComponentRegistry
{
    //Entity component types
    lateinit var setupFamilyOwnershipEntityComponentType: ComponentType<EntityStore?, SetupFamilyOwnershipComponent?>
    lateinit var familyEntityComponentType: ComponentType<EntityStore?, FamilyEntityComponent?>
    lateinit var factionEntityComponentType: ComponentType<EntityStore?, FactionEntityComponent?>

    //Block component types
    lateinit var familyBlockComponentType: ComponentType<ChunkStore, FamilyBlockComponent?>

    fun registerEntityComponents(entityStoreRegistry: ComponentRegistryProxy<EntityStore>)
    {
        setupFamilyOwnershipEntityComponentType = entityStoreRegistry.registerComponent(
            SetupFamilyOwnershipComponent::class.java, ::SetupFamilyOwnershipComponent
        )

        familyEntityComponentType = entityStoreRegistry.registerComponent(
            FamilyEntityComponent::class.java, ::FamilyEntityComponent
        )

        factionEntityComponentType = entityStoreRegistry.registerComponent(
            FactionEntityComponent::class.java, ::FactionEntityComponent
        )
    }

    fun registerBlockComponents(chunkStoreRegistry: ComponentRegistryProxy<ChunkStore>)
    {
        familyBlockComponentType = chunkStoreRegistry.registerComponent(
            FamilyBlockComponent::class.java, ::FamilyBlockComponent
        )
    }
}