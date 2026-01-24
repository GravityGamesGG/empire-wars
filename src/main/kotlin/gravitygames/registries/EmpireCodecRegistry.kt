package gravitygames.registries

import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction
import com.hypixel.hytale.server.core.plugin.registry.CodecMapRegistry
import gravitygames.Interactions.TrebuchetProjectileInteraction

object EmpireCodecRegistry
{
    fun registerCodecs(codecRegistry: CodecMapRegistry.Assets<Interaction, *>)
    {
        codecRegistry.register("TrebuchetProjectile", TrebuchetProjectileInteraction::class.java, TrebuchetProjectileInteraction.CODEC)
    }
}