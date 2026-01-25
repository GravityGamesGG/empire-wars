package gravitygames.registries

import com.hypixel.hytale.component.ComponentRegistryProxy
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import gravitygames.systems.FactionAttitudeSystem
import gravitygames.systems.events.ApplyFamilyOwnership
import gravitygames.systems.events.CheckFamilyPermission

object EmpireEntityStoreRegistry
{
    fun registerSystems(entityStoreRegistry: ComponentRegistryProxy<EntityStore>)
    {
        entityStoreRegistry.registerSystem(CheckFamilyPermission())
        entityStoreRegistry.registerSystem(ApplyFamilyOwnership())
        entityStoreRegistry.registerSystem(FactionAttitudeSystem())
    }
}