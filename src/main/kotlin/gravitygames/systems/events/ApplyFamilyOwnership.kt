package gravitygames.systems.events

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.EntityEventSystem
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent
import com.hypixel.hytale.server.core.modules.block.BlockModule
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.Universe
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import gravitygames.Empire
import gravitygames.component.FamilyOwnershipBlockComponent
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
        val playerRefComponent = entityCommandBuffer.getComponent(playerEntityRef, PlayerRef.getComponentType())
        Empire.Logger.atInfo().log("${event.targetBlock.x}")
        val targetBlockEntity = BlockModule.getBlockEntity(
            Universe.get().defaultWorld!!, event.targetBlock.x, event.targetBlock.y, event.targetBlock.z
        )
        if (targetBlockEntity == null)
        {
            playerRefComponent?.sendMessage(Message.raw("No target entity found"))
            return
        }
        val entityFamOwnershipComponent = entityStore.externalData.world.chunkStore.store.getComponent(
            targetBlockEntity, EmpireComponentRegistry.familyOwnershipBlockComponentType
        )
        if (entityFamOwnershipComponent != null)
        {
            playerRefComponent?.sendMessage(Message.raw("This object is already owned by the family \"${entityFamOwnershipComponent.owner}\""))
        } else
        {
            val playerFamOwnershipComponent = entityCommandBuffer.getComponent(
                playerEntityRef, EmpireComponentRegistry.familyOwnershipEntityComponentType
            )
            val newFamOwnershipComponent = FamilyOwnershipBlockComponent(playerFamOwnershipComponent!!.owner)
            Universe.get().defaultWorld!!.chunkStore.store.addComponent(
                targetBlockEntity, EmpireComponentRegistry.familyOwnershipBlockComponentType, newFamOwnershipComponent
            )
            entityCommandBuffer.store.removeComponent(
                playerEntityRef, EmpireComponentRegistry.familyOwnershipEntityComponentType
            )
            playerRefComponent?.sendMessage(Message.raw("Your family have claimed ownership of this object"))
        }
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