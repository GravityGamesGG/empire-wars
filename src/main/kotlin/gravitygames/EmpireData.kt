package gravitygames

import java.util.*

data class PlayerData(var Faction: Faction? = null)
data class FamilyData(val name: String)
{
    val members = mutableListOf<UUID>()
}