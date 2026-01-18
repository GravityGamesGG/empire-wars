package gravitygames.commands

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncPlayerCommand
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import gravitygames.FamilyData
import gravitygames.FamilyStore
import gravitygames.component.FamilyOwnershipEntityComponent
import gravitygames.component.triggers.SetupFamilyOwnershipComponent
import gravitygames.registries.EmpireComponentRegistry
import java.util.concurrent.CompletableFuture

class FamilyCommands : AbstractCommandCollection("family", "Family management commands")
{
    init
    {
        addSubCommand(FamilyCreateCommand())
        addSubCommand(FamilyJoinCommand())
        addSubCommand(FamilyDeleteCommand())
        addSubCommand(FamilyAcceptCommand())
        addSubCommand(FamilyGetOwnershipCommmand())
    }
}

class FamilyCreateCommand : AbstractAsyncPlayerCommand("create", "Create a new family")
{
    private val familyName: RequiredArg<String?> = this.withRequiredArg<String?>(
        "familyName", "The name of the new family to create", ArgTypes.STRING
    )

    override fun executeAsync(
        ctx: CommandContext, store: Store<EntityStore?>, ref: Ref<EntityStore?>, playerRef: PlayerRef, world: World
    ): CompletableFuture<Void?>
    {
        val familyName = ctx.get<String?>(familyName)
        if (familyName == null)
        {
            ctx.sendMessage(Message.raw("Must provide a family name"))
            return CompletableFuture.completedFuture(null)
        }

        FamilyStore[familyName] = FamilyData(familyName).apply { members.add(playerRef.uuid) }
        EmpireComponentRegistry.familyOwnershipEntityComponentType?.let {
            val newComponent = FamilyOwnershipEntityComponent(familyName)
            store.addComponent(ref, it, newComponent)
        }
        ctx.sendMessage(Message.raw("Family $familyName created!"))
        return CompletableFuture.completedFuture(null)
    }
}

class FamilyJoinCommand : AbstractAsyncPlayerCommand("join", "Join a family")
{
    override fun executeAsync(
        ctx: CommandContext, store: Store<EntityStore?>, ref: Ref<EntityStore?>, playerRef: PlayerRef, world: World
    ): CompletableFuture<Void?>
    {
        ctx.sendMessage(Message.raw("Joined family!"))
        return CompletableFuture.completedFuture(null)
    }
}

class FamilyDeleteCommand : AbstractAsyncPlayerCommand("delete", "Delete your family")
{
    override fun executeAsync(
        ctx: CommandContext, store: Store<EntityStore?>, ref: Ref<EntityStore?>, playerRef: PlayerRef, world: World
    ): CompletableFuture<Void?>
    {
        ctx.sendMessage(Message.raw("Family deleted!"))
        return CompletableFuture.completedFuture(null)
    }
}

class FamilyAcceptCommand : AbstractAsyncPlayerCommand("accept", "Accept a family invitation")
{
    override fun executeAsync(
        ctx: CommandContext, store: Store<EntityStore?>, ref: Ref<EntityStore?>, playerRef: PlayerRef, world: World
    ): CompletableFuture<Void?>
    {
        ctx.sendMessage(Message.raw("Invitation accepted!"))
        return CompletableFuture.completedFuture(null)
    }
}

class FamilyGetOwnershipCommmand :
    AbstractAsyncPlayerCommand("getownership", "Get ownership of the next object you interact with")
{
    override fun executeAsync(
        ctx: CommandContext, store: Store<EntityStore?>, ref: Ref<EntityStore?>, playerRef: PlayerRef, world: World
    ): CompletableFuture<Void?>
    {
        EmpireComponentRegistry.familyOwnershipEntityComponentType?.let {
            val playerFamilyComponent = store.getComponent(ref, it)

            if (playerFamilyComponent == null)
            {
                ctx.sendMessage(Message.raw("You are not in a family"))
                return CompletableFuture.completedFuture(null)
            }

            val newComponent = SetupFamilyOwnershipComponent()
            EmpireComponentRegistry.setupFamilyOwnershipEntityComponentType?.let {
                store.addComponent(ref, it, newComponent)
                ctx.sendMessage(Message.raw("Your family will be the owner of the next object you interact with"))
            }
        }

        return CompletableFuture.completedFuture(null)
    }
}
