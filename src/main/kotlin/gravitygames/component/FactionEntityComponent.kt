package gravitygames.component

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import gravitygames.Faction

data class FactionEntityComponent(val faction: Faction = Faction.NONE) : Component<EntityStore?>
{
    override fun clone(): Component<EntityStore?> = this.copy()
}

data class FactionBlockComponent(val faction: Faction) : Component<ChunkStore>
{
    override fun clone(): Component<ChunkStore> = this.copy()
}
