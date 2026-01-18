package gravitygames.systems.events

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.EntityEventSystem
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import gravitygames.registries.EmpireComponentRegistry

class CheckFamilyPermission() : EntityEventSystem<EntityStore, UseBlockEvent.Pre>(UseBlockEvent.Pre::class.java)
{
    override fun handle(
        var1: Int,
        var2: ArchetypeChunk<EntityStore?>,
        var3: Store<EntityStore?>,
        var4: CommandBuffer<EntityStore?>,
        var5: UseBlockEvent.Pre
    )
    {

    }

    override fun getQuery(): Query<EntityStore?>
    {
        return PlayerRef.getComponentType()
    }
}