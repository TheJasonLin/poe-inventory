package config

import com.poe.constants.Rarity

object MapRequirements {
  IniReader.getString("mapRequirements", "rollRarity")
  val rollRarity: Rarity = Rarity.RARE
  // prefix IIQ range: [6, 20]
  // suffix IIQ range: [6, 20] + 25 (no leech)
  val minItemQuantity: Int = 0

  // prefix IIR range: [3, 10]
  // suffix IIR range: [5, 15]
  val minItemRarity: Int = 0

  // suffix Pack Size range: [0, 16]
  val minPackSize: Int = 15
  val minQuality: Int = 0

  val blacklistMods = IniReader.getStrings("mapRequirements", "blacklist")
}
