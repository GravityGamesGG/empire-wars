package gravitygames.registries

import com.hypixel.hytale.component.ComponentRegistryProxy
import com.hypixel.hytale.component.ComponentType
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import gravitygames.component.FamilyOwnershipBlockComponent
import gravitygames.component.FamilyOwnershipEntityComponent
import gravitygames.component.triggers.SetupFamilyOwnershipComponent

object EmpireComponentRegistry
{
    //Component types
    lateinit var setupFamilyOwnershipEntityComponentType: ComponentType<EntityStore?, SetupFamilyOwnershipComponent?>
    lateinit var familyOwnershipEntityComponentType: ComponentType<EntityStore?, FamilyOwnershipEntityComponent?>
    lateinit var familyOwnershipBlockComponentType: ComponentType<ChunkStore, FamilyOwnershipBlockComponent?>

    fun registerEntityComponents(entityStoreRegistry: ComponentRegistryProxy<EntityStore>)
    {
        setupFamilyOwnershipEntityComponentType = entityStoreRegistry.registerComponent(
            SetupFamilyOwnershipComponent::class.java, ::SetupFamilyOwnershipComponent
        )

        familyOwnershipEntityComponentType = entityStoreRegistry.registerComponent(
            FamilyOwnershipEntityComponent::class.java, ::FamilyOwnershipEntityComponent
        )
    }

    fun registerBlockComponents(chunkStoreRegistry: ComponentRegistryProxy<ChunkStore>)
    {
        familyOwnershipBlockComponentType = chunkStoreRegistry.registerComponent(
            FamilyOwnershipBlockComponent::class.java, ::FamilyOwnershipBlockComponent
        )
    }
}