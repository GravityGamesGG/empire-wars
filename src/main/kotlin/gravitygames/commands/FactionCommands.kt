package gravitygames.commands

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncPlayerCommand
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import gravitygames.Factions
import gravitygames.PlayerData
import gravitygames.PlayerStore
import java.util.concurrent.CompletableFuture

class JoinFactionCommand : AbstractAsyncPlayerCommand("fjoin", "Join a faction", false)
{
    private val factionNumber: RequiredArg<Int?> = this.withRequiredArg<Int?>(
        "factionNumber", "The faction number to join 1, 2 or 3", ArgTypes.INTEGER
    )

    override fun executeAsync(
        ctx: CommandContext,
        store: Store<EntityStore?>,
        ref: Ref<EntityStore?>,
        playerRefComponent: PlayerRef,
        world: World
    ): CompletableFuture<Void?>
    {
        val faction = ctx.get<Int?>(factionNumber)

        if (faction == null || faction !in 1..3)
        {
            ctx.sendMessage(Message.raw("Invalid faction number"))
            return CompletableFuture.completedFuture(null)
        }

        val chosenFaction = Factions.entries[faction - 1]
        PlayerStore[playerRefComponent.uuid] = PlayerData(chosenFaction)
        ctx.sendMessage(Message.raw("Joined faction $faction"))

        return CompletableFuture.completedFuture(null)
    }

}