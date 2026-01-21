package gravitygames.Interactions

import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction
import gravitygames.Empire

class TrebuchetInteraction : SimpleInstantInteraction()
{
    companion object
    {
        val CODEC = BuilderCodec.builder(
            TrebuchetInteraction::class.java,
            ::TrebuchetInteraction,
            SimpleInstantInteraction.CODEC
        ).build()
    }

    override fun firstRun(
        var1: InteractionType, var2: InteractionContext, var3: CooldownHandler
    )
    {
        Empire.Logger.atInfo().log("Trebuchet")
    }
}