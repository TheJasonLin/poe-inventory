package items.equipment.armour

import structures.Position

class BodyArmour(
                  position: Position,
                  rarity: String,
                  base: String,
                  name: Option[String]
                ) extends Armour(position, rarity, base, name) {


}

/**
  * Must try matching Body Armour last, because there are overlaps with the other identifiers
  */
object BodyArmour {
  val identifiers = Array(
    "Vest",
    "Chestplate",
    "Plate",
    "Jerkin",

    /**
      * Careful. This one has overlap with some things.
      */
    "Leather",
    "Tunic",
    "Garb",
    "Robe",
    "Vestment",
    "Regalia",
    "Wrap",
    "Silks",
    "Brigandine",
    "Doublet",
    "Armour",
    "Lamellar",
    "Wyrmscale",
    "Coat",
    "Ringmail",
    "Chainmail",
    "Hauberk",
    "Jacket",
    "Raiment"
  )
}
