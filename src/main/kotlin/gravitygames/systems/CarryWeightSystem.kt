package gravitygames.systems

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.RemovalBehavior
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import javax.annotation.Nonnull

class CarryWeightSystem : EntityTickingSystem<EntityStore>()
{

    private val carryWeightStatIndex: Int by lazy { EntityStatType.getAssetMap().getIndex("CarryWeight") }
    private val overencumberedEffectIndex: Int by lazy { EntityEffect.getAssetMap().getIndex("Overencumbered") }

    override fun tick(
        dt: Float,
        index: Int,
        archetypeChunk: ArchetypeChunk<EntityStore>,
        store: Store<EntityStore>,
        commandBuffer: CommandBuffer<EntityStore>
    )
    {
        // Validate indices are loaded
        if (carryWeightStatIndex == Int.MIN_VALUE || overencumberedEffectIndex == Int.MIN_VALUE)
        {
            return
        }

        val statMap = archetypeChunk.getComponent(index, EntityStatMap.getComponentType()) ?: return
        val effectController = archetypeChunk.getComponent(index, EffectControllerComponent.getComponentType())
            ?: return
        val ref = archetypeChunk.getReferenceTo(index)

        // Safely get the stat entry
        val carryWeightStat = statMap.get(carryWeightStatIndex) ?: return

        val currentWeight = carryWeightStat.get()
        val maxWeight = carryWeightStat.max
        val hasOverencumbered = effectController.activeEffects.containsKey(overencumberedEffectIndex)

        // Apply effect when at or above max weight
        if (currentWeight >= maxWeight && !hasOverencumbered)
        {
            val effect = EntityEffect.getAssetMap().getAsset(overencumberedEffectIndex)
            if (effect != null)
            {
                effectController.addEffect(ref, effect, commandBuffer)
            }
        }
        // Remove effect when below max weight
        else if (currentWeight < maxWeight && hasOverencumbered)
        {
            effectController.removeEffect(ref, overencumberedEffectIndex, RemovalBehavior.COMPLETE, commandBuffer)
        }
    }

    @Nonnull
    override fun getQuery(): Query<EntityStore>
    {
        return Query.and(
            Player.getComponentType(), EntityStatMap.getComponentType(), EffectControllerComponent.getComponentType()
        )
    }
}
