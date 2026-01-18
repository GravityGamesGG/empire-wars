package gravitygames

import java.util.*

data class PlayerData(var Faction: Factions? = null)
data class FamilyData(val name: String)
{
    val members = mutableListOf<UUID>()
}