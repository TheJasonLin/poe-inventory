package items.equipment.weapon

class Sword(
             rarity: String,
             base: String,
             name: Option[String],
             oneHanded: Boolean,
             val thrusting: Boolean
           ) extends VariableWeapon(rarity, base, name, oneHanded) {

  override def height(): Int = if (thrusting) 4 else super.width()

  override def width(): Int = if (thrusting) 1 else super.width()
}

object Sword {
  val identifiers = Array(
    "Sword",
    "Sabre",
    "Blade",
    "Cutlass",
    "Baselard",
    "Grappler",
    "Gladius",
    "Spike",
    "Rapier",
    "Foil",
    "Smallsword",
    "Estoc",
    "Pecoraro",
    "Longsword",
    "Greatsword"
  )

  val identifierExceptions = Array(
    "Boot Blade"
  )

  val oneHandedIdentifiers = Array(
    "Rusted",
    "Copper",
    "Sabre",
    "Broad",
    "War",
    "Ancient",
    "Elegant",
    "Dusk",
    "Hook",
    "Variscite",
    "Cutlass",
    "Baselard",
    "Battle",
    "Elder",
    "Graceful",
    "Twilight",
    "Grappler",
    "Gemstone",
    "Corsair",
    "Gladius",
    "Legion",
    "Eternal",
    "Midnight",
    "Whalebone",
    "Battered",
    "Basket",
    "Jagged",
    "Antique",
    "Thorn",
    "Smallsword",
    "Wyrmbone",
    "Burnished",
    "Estoc",
    "Serrated",
    "Primeval",
    "Fancy",
    "Apex",
    "Courtesan",
    "Dragonbone",
    "Tempered",
    "Pecoraro",
    "Spiraled",
    "Jewelled",
    "Harpy",
    "Dragoon"
  )

  val oneHandedBases = Array(
    "Vaal Blade",
    "Tiger Hook",
    "Vaal Rapier"
  )

  val thrustingIdentifiers = Array(
    "Spike",
    "Rapier",
    "Foil",
    "Smallsword",
    "Estoc",
    "Pecoraro",
    "Dragoon"
  )
}
