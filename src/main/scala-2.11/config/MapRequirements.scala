package config

import com.poe.constants.Rarity

object MapRequirements {
  val rollRarity: Rarity = IniReader.getString("mapRequirements", "rollRarity") match {
    case "normal" => Rarity.NORMAL
    case "magic" => Rarity.MAGIC
    case "rare" => Rarity.RARE
    case _ => Rarity.NORMAL
  }

  // prefix IIQ range: [6, 20]
  // suffix IIQ range: [6, 20] + 25 (no leech)
  val minItemQuantity: Int = IniReader.getInt("mapRequirements", "minItemQuantity")

  // prefix IIR range: [3, 10]
  // suffix IIR range: [5, 15]
  val minItemRarity: Int = IniReader.getInt("mapRequirements", "minItemRarity")

  // suffix Pack Size range: [0, 16]
  val minPackSize: Int = IniReader.getInt("mapRequirements", "minPackSize")
  val minQuality: Int = IniReader.getInt("mapRequirements", "minQuality")

  val blacklistMods: Seq[String] = IniReader.getStrings("mapRequirements", "blacklist")
}
