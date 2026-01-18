package gravitygames

import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction

class MyInteraction : SimpleInstantInteraction()
{
    companion object
    {
        val CODEC: BuilderCodec<MyInteraction> = BuilderCodec.builder(
            MyInteraction::class.java, ::MyInteraction, SimpleInstantInteraction.CODEC
        ).build()
    }

    override fun firstRun(
        p0: InteractionType, p1: InteractionContext, p2: CooldownHandler
    )
    {
        Empire.Logger.atInfo().log("Interaction")
    }
}