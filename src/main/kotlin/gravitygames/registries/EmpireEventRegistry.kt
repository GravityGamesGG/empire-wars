package gravitygames.registries

import com.hypixel.hytale.event.EventRegistry
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent
import gravitygames.listeners.PlayerReadyListener

object EmpireEventRegistry
{
    fun registerEvents(eventRegistry: EventRegistry)
    {

        eventRegistry.registerGlobal<String?, PlayerReadyEvent?>(PlayerReadyEvent::class.java) { event: PlayerReadyEvent? ->
            PlayerReadyListener.onPlayerReady(event!!)
        }
    }
}