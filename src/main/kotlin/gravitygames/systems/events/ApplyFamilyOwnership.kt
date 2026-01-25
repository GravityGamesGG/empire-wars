package gravitygames.systems.events

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.EntityEventSystem
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.Universe
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import gravitygames.component.FamilyBlockComponent
import gravitygames.registries.EmpireComponentRegistry

class ApplyFamilyOwnership : EntityEventSystem<EntityStore, UseBlockEvent.Pre>(UseBlockEvent.Pre::class.java)
{
    override fun handle(
        entityIndex: Int,
        archetypeStore: ArchetypeChunk<EntityStore?>,
        entityStore: Store<EntityStore?>,
        entityCommandBuffer: CommandBuffer<EntityStore?>,
        event: UseBlockEvent.Pre
    )
    {
        val playerEntityRef = archetypeStore.getReferenceTo(entityIndex)
        val playerRef = entityStore.getComponent(playerEntityRef, PlayerRef.getComponentType()) ?: return
        val familyOwnership = entityStore.getComponent(
            playerEntityRef, EmpireComponentRegistry.familyEntityComponentType
        )
        val owner = familyOwnership?.owner ?: return

        val world = Universe.get().defaultWorld ?: return
        val pos = event.targetBlock
        val chunk = world.getChunk(ChunkUtil.indexChunkFromBlock(pos.x, pos.z)) ?: return

        var holder = chunk.getBlockComponentHolder(pos.x, pos.y, pos.z)
        if (holder == null)
        {
            holder = ChunkStore.REGISTRY.newHolder()
        }

        if (holder.getComponent(EmpireComponentRegistry.familyBlockComponentType) != null)
        {
            playerRef.sendMessage(Message.raw("This object is already owned by another family"))
            return
        }

        holder.addComponent(
            EmpireComponentRegistry.familyBlockComponentType, FamilyBlockComponent(owner = owner)
        )

        chunk.setState(pos.x, pos.y, pos.z, holder)
        entityCommandBuffer.removeComponent(
            playerEntityRef, EmpireComponentRegistry.setupFamilyOwnershipEntityComponentType
        )
        playerRef.sendMessage(Message.raw("Applied family ownership"))
    }

    override fun getQuery(): Query<EntityStore?>
    {
        return Query.and(
            PlayerRef.getComponentType(),
            EmpireComponentRegistry.setupFamilyOwnershipEntityComponentType,
            EmpireComponentRegistry.familyEntityComponentType
        )
    }
}