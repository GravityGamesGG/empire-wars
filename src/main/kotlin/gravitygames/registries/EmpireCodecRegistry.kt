package gravitygames.registries

import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction
import com.hypixel.hytale.server.core.plugin.registry.CodecMapRegistry
import gravitygames.Interactions.TrebuchetInteraction

object EmpireCodecRegistry
{
    fun registerCodecs(codecRegistry: CodecMapRegistry.Assets<Interaction, *>)
    {
        codecRegistry.register("trebuchet_use", TrebuchetInteraction::class.java, TrebuchetInteraction.CODEC)
    }
}