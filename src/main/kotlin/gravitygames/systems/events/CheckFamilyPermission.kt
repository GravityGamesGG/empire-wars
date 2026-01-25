package gravitygames.systems.events

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.EntityEventSystem
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.math.vector.Vector3i
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import gravitygames.component.FamilyBlockComponent
import gravitygames.registries.EmpireComponentRegistry

class CheckFamilyPermission : EntityEventSystem<EntityStore, UseBlockEvent.Pre>(UseBlockEvent.Pre::class.java)
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
        val playerComponent = entityStore.getComponent(playerEntityRef, Player.getComponentType()) ?: return
        val world = playerComponent.world ?: return
        val blockFamily = getFamilyOwnershipBlockComponent(world, event.targetBlock)?.owner ?: return
        val playerFamily = entityStore.getComponent(
            playerEntityRef, EmpireComponentRegistry.familyEntityComponentType
        )?.owner

        if (playerFamily == null || playerFamily != blockFamily)
        {
            event.isCancelled = true
            playerRef.sendMessage(Message.raw("This object is owned by the family ${blockFamily}"))
        }
    }

    fun getFamilyOwnershipBlockComponent(world: World, pos: Vector3i): FamilyBlockComponent?
    {
        return world.getChunk(ChunkUtil.indexChunkFromBlock(pos.x, pos.z))
            ?.getBlockComponentHolder(pos.x, pos.y, pos.z)
            ?.getComponent(EmpireComponentRegistry.familyBlockComponentType)
    }

    override fun getQuery(): Query<EntityStore?>
    {
        return PlayerRef.getComponentType()
    }
}