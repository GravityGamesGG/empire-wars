package gravitygames.listeners

import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent
import com.hypixel.hytale.server.core.universe.PlayerRef
import gravitygames.Empire
import gravitygames.PlayerData
import gravitygames.PlayerStore

object PlayerReadyListener
{
    fun onPlayerReady(event: PlayerReadyEvent)
    {
        val player = event.player
        player.sendMessage(Message.raw("Welcome " + player.displayName))
        val store = event.playerRef.store
        val playerRefComponent = store.getComponent<PlayerRef>(event.playerRef, PlayerRef.getComponentType())
        playerRefComponent?.let {
            if (PlayerStore[playerRefComponent.uuid] == null)
            {
                PlayerStore[it.uuid] = PlayerData()
                Empire.LOGGER.atInfo().log("User ${it.uuid} has been added to the player store")
            }
        }
    }
}