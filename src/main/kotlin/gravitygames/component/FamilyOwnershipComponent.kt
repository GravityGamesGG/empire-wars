package gravitygames.component

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

data class FamilyOwnershipEntityComponent(var owner: String? = null) : Component<EntityStore?>
{
    override fun clone(): Component<EntityStore?>
    {
        return this.copy()
    }

}

data class FamilyOwnershipBlockComponent(var owner: String? = null) : Component<ChunkStore>
{
    override fun clone(): Component<ChunkStore>
    {
        return this.copy()
    }

}
