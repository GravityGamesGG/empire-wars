package gravitygames

import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import gravitygames.registries.EmpireCommandRegistry
import gravitygames.registries.EmpireComponentRegistry
import gravitygames.registries.EmpireEntityStoreRegistry
import gravitygames.registries.EmpireEventRegistry
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.Nonnull

val PlayerStore = ConcurrentHashMap<UUID, PlayerData>()
val FamilyStore = ConcurrentHashMap<String, FamilyData>()

class Empire(@Nonnull init: JavaPluginInit) : JavaPlugin(init)
{
    companion object
    {
        val Logger = HytaleLogger.forEnclosingClass()
    }

    override fun setup()
    {
        EmpireCommandRegistry.registerCommands(commandRegistry)
        EmpireEventRegistry.registerEvents(eventRegistry)
        EmpireComponentRegistry.registerEntityComponents(entityStoreRegistry)
        EmpireEntityStoreRegistry.registerSystems(entityStoreRegistry)
        EmpireComponentRegistry.registerBlockComponents(chunkStoreRegistry)

        getCodecRegistry(Interaction.CODEC).register("Block_Primary", MyInteraction::class.java, MyInteraction.CODEC)
    }
}
