package config

import com.poe.constants.Rarity

object MapRequirements {
  val rollRarity: Rarity = Rarity.RARE
  // prefix IIQ range: [6, 20]
  // suffix IIQ range: [6, 20] + 25 (no leech)
  val minItemQuantity: Int = 30

  // prefix IIR range: [3, 10]
  // suffix IIR range: [5, 15]
  val minItemRarity: Int = 20

  // suffix Pack Size range: [0, 16]
  val minPackSize: Int = 0
  val minQuality: Int = 0

  val blacklistMods: Seq[String] = Seq(
    "Monsters reflect #% of Elemental Damage",
    "Monsters have #git st% chance to Avoid Elemental Ailments",
    "Cannot Leech Life from Monsters",
    "Cannot Leech Mana from Monsters",
    "Players are Cursed with Temporal Chains"
  )

  private val references: Seq[String] = Seq(
    "-#% maximum Player Resistances"
  )
}
