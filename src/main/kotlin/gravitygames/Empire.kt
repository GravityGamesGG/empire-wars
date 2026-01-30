package gravitygames

import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import gravitygames.registries.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.Nonnull

val PlayerStore = ConcurrentHashMap<UUID, PlayerData>()
val FamilyStore = ConcurrentHashMap<String, FamilyData>()

class Empire(@Nonnull init: JavaPluginInit) : JavaPlugin(init)
{
    companion object
    {
        val LOGGER = HytaleLogger.forEnclosingClass()
    }

    override fun setup()
    {
        EmpireCommandRegistry.registerCommands(commandRegistry)
        EmpireEventRegistry.registerEvents(eventRegistry)
        EmpireComponentRegistry.registerEntityComponents(entityStoreRegistry)
        EmpireEntityStoreRegistry.registerSystems(entityStoreRegistry)
        EmpireComponentRegistry.registerBlockComponents(chunkStoreRegistry)
        EmpireCodecRegistry.registerCodecs(this)
    }
}
