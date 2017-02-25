package items.currency

import items.Item
import structures.Position

class Currency(
              position: Position,
              rarity: String,
              base: String,
              name: Option[String]
              ) extends Item (position, rarity, base, name) {

}

object Currency {
  val bases = Array(
    "Armourer's Scrap",
    "Blacksmith's Whetstone",
    "Orb of augmentation",
    "Orb of Transmutation",
    "Scroll of Wisdom",
    "Silver Coin",
    "Splinter of Chayula",
    "Splinter of Esh",
    "Splinter of Tul",
    "Splinter of Uul-Netol",
    "Splinter of Xoph",
    "Chromatic Orb",
    "Glassblower's Bauble",
    "Orb of Alchemy",
    "Orb of Alteration",
    "Orb of Chance",
    "Orb of Regret",
    "Portal Scroll",
    "Jeweller's Orb",
    "Orb of Fusing",
    "Chaos Orb",
    "Gemcutter's Prism",
    "Orb of Scouring",
    "Regal Orb",
    "Vaal Orb",
    "Blessed Orb",
    "Divine Orb",
    "Exalted Orb",
    "Mirror of Kalandra",
    "Cartographer's Chisel",
    "Apprentice Cartographer's Sextant",
    "Journeyman Cartographer's Sextant",
    "Master Cartographer's Sextant",
    "Alchemy Shard",
    "Alteration Shard",
    "Scroll Fragment",
    "Transmutation Shard",
    "Eternal Orb"
  )
  def is(name: String): Boolean = {
    bases.indexOf(name) >= 0
  }
}
