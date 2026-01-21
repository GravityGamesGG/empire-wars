package gravitygames.systems.events

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.EntityEventSystem
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.math.vector.Vector3i
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.Universe
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
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
        val targetBlockEntityRef = getBlockEntity(Universe.get().defaultWorld!!, event.targetBlock)
        // ^^ Always null ^^
    }

    private fun getBlockEntity(world: World, pos: Vector3i): Ref<ChunkStore?>?
    {
        val chunkRef = world.chunkStore.getChunkReference(ChunkUtil.indexChunkFromBlock(pos.x, pos.z))
        val chunkStore = chunkRef!!.store
        val blockComponent = chunkStore.getComponent(chunkRef, BlockComponentChunk.getComponentType())
        val blockIndex = ChunkUtil.indexBlockInColumn(pos.x, pos.y, pos.z)
        val blockRef = blockComponent?.getEntityReference(blockIndex)
        return blockRef
    }

    override fun getQuery(): Query<EntityStore?>
    {
        return Query.and(
            PlayerRef.getComponentType(),
            EmpireComponentRegistry.setupFamilyOwnershipEntityComponentType,
            EmpireComponentRegistry.familyOwnershipEntityComponentType
        )
    }
}