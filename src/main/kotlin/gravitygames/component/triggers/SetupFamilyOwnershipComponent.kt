package gravitygames.component.triggers

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

class SetupFamilyOwnershipComponent : Component<EntityStore?>
{
    override fun clone(): Component<EntityStore?> = SetupFamilyOwnershipComponent()
}