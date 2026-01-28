package gravitygames.registries

import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import gravitygames.Interactions.SpawnGuardArcherInteraction
import gravitygames.Interactions.TrebuchetProjectileInteraction

object EmpireCodecRegistry
{
    fun registerCodecs(plugin: JavaPlugin)
    {
        plugin.getCodecRegistry(Interaction.CODEC)
            .register("TrebuchetProjectile", TrebuchetProjectileInteraction::class.java, TrebuchetProjectileInteraction.CODEC)
            .register("SpawnGuardArcher", SpawnGuardArcherInteraction::class.java, SpawnGuardArcherInteraction.CODEC)
    }
}