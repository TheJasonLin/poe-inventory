package structures

import com.poe.constants.Rarity

case class MapRequirements(blacklistMods: Seq[String], rollRarity: Rarity, minItemQuantity: Int, minItemRarity: Int, minPackSize: Int, minQuality: Int)
