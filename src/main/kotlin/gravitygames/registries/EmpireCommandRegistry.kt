package gravitygames.registries

import com.hypixel.hytale.server.core.command.system.CommandRegistry
import gravitygames.commands.FamilyCommands
import gravitygames.commands.JoinFactionCommand

object EmpireCommandRegistry
{
    fun registerCommands(commandRegistry: CommandRegistry)
    {

        commandRegistry.registerCommand(JoinFactionCommand())
        commandRegistry.registerCommand(FamilyCommands())
    }
}