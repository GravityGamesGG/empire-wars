package gravitygames.listeners

import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent
import gravitygames.chat.ChatMessageFormatter

object PlayerChatListener
{
    private val FORMATTER = ChatMessageFormatter()

    fun onPlayerChat(event: PlayerChatEvent)
    {
        event.formatter = FORMATTER
    }
}
