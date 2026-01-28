package gravitygames.Interactions

import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.math.vector.Vector3i
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.inventory.ItemStack
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.hypixel.hytale.server.npc.NPCPlugin
import com.hypixel.hytale.server.npc.role.RoleUtils
import gravitygames.Empire
import gravitygames.Faction
import gravitygames.component.FactionEntityComponent
import gravitygames.registries.EmpireComponentRegistry

class SpawnGuardArcherInteraction : SimpleBlockInteraction()
{
    companion object
    {
        val CODEC: BuilderCodec<SpawnGuardArcherInteraction> = BuilderCodec.builder(
            SpawnGuardArcherInteraction::class.java, ::SpawnGuardArcherInteraction, SimpleBlockInteraction.CODEC
        ).build()
    }

    override fun interactWithBlock(
        world: World,
        commandBuffer: CommandBuffer<EntityStore?>,
        interactionType: InteractionType,
        interactionContext: InteractionContext,
        itemStack: ItemStack?,
        blockPosition: Vector3i,
        cooldownHandler: CooldownHandler
    )
    {
        world.execute {
            Empire.Logger.atInfo().log("Spawning guard archer")
            val roleIndex = NPCPlugin.get().getIndex("Archer_Guard")
            val entityPosition = blockPosition.toVector3d()
            entityPosition.y += 1.5
            NPCPlugin.get()
                .spawnEntity(commandBuffer.store, roleIndex, entityPosition, Vector3f(), null, { npc, holder, store ->
                    holder.addComponent(
                        EmpireComponentRegistry.factionEntityComponentType, FactionEntityComponent(Faction.FACTION2)
                    )
                }, { npc, holder, store ->
                    RoleUtils.setItemInHand(npc, "Weapon_Shortbow_Crude")
                })
        }

    }

    override fun simulateInteractWithBlock(
        var1: InteractionType, var2: InteractionContext, var3: ItemStack?, var4: World, var5: Vector3i
    )
    {
    }


}