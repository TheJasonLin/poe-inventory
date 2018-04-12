package config

import com.poe.constants.Rarity

object MapRequirements {
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

  val blacklistMods: Seq[String] = Seq(
    "Monsters reflect #% of Elemental Damage",
    "Players are Cursed with Temporal Chains"
  )

  private val references: Seq[String] = Seq(
    "-#% maximum Player Resistances",
    "Monsters have #% chance to Avoid Elemental Ailments",
    "Cannot Leech Life from Monsters",
    "Cannot Leech Mana from Monsters",
    "Players are Cursed with Temporal Chains",
    "Monsters reflect #% of Physical Damage",
    // Unlikely
    "Monsters have #% increased Accuracy Rating",
    "Monsters gain a Frenzy Charge on Hit",
    "Monsters have a #% chance to avoid Poison, Blind, and Bleeding",
    "Player Dodge chance is Unlucky",
    "Area contains many Totems",
    "Players have Elemental Equilibrium",
    "Players are Cursed with Elemental Weakness",
    "Monsters gain a Power Charge on Hit"
  )
}
