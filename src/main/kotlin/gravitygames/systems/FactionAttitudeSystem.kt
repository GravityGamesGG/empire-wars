package gravitygames.systems

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.system.StoreSystem
import com.hypixel.hytale.server.core.asset.type.attitude.Attitude
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.hypixel.hytale.server.npc.blackboard.Blackboard
import com.hypixel.hytale.server.npc.blackboard.view.attitude.AttitudeView
import com.hypixel.hytale.server.npc.blackboard.view.attitude.IAttitudeProvider
import com.hypixel.hytale.server.npc.role.Role
import gravitygames.Faction
import gravitygames.registries.EmpireComponentRegistry

class FactionAttitudeSystem : StoreSystem<EntityStore>()
{
    companion object
    {
        val factionAttitudes = mutableMapOf<String, MutableMap<String, Attitude>>()
    }

    init
    {
        setupFactionAttitudes()
    }

    override fun onSystemAddedToStore(store: Store<EntityStore?>)
    {
        val blackboard = store.getResource(Blackboard.getResourceType())
        val view = blackboard.getView(AttitudeView::class.java, 0L)
        view.registerProvider(50, FactionAttitudeProvider())
    }

    override fun onSystemRemovedFromStore(var1: Store<EntityStore?>)
    {
    }

    private fun setupFactionAttitudes()
    {
        factionAttitudes[Faction.FACTION1.name] = mutableMapOf(
            Pair(Faction.FACTION2.name, Attitude.HOSTILE),
            Pair(Faction.FACTION3.name, Attitude.HOSTILE),
        )
    }
}

class FactionAttitudeProvider : IAttitudeProvider
{
    override fun getAttitude(
        sourceRef: Ref<EntityStore?>,
        role: Role,
        targetRef: Ref<EntityStore?>,
        accessor: ComponentAccessor<EntityStore?>
    ): Attitude?
    {
        val sourceEntityFactionComponent = accessor.getComponent(
            sourceRef, EmpireComponentRegistry.factionEntityComponentType
        ) ?: return null

        val targetEntityFactionComponent = accessor.getComponent(
            targetRef, EmpireComponentRegistry.factionEntityComponentType
        ) ?: return null

        val attitude = when (targetEntityFactionComponent.faction)
        {
            sourceEntityFactionComponent.faction -> Attitude.FRIENDLY
            Faction.NONE -> Attitude.NEUTRAL
            else -> Attitude.HOSTILE
        }

        return attitude
    }
}