package items.equipment.armour

class BodyArmour(
                  rarity: String,
                  base: String,
                  name: Option[String],
                  itemLevel: Int,
                  identified: Boolean,
                  quality: Int
                ) extends Armour(rarity, base, name, itemLevel, identified, quality) {
  override def height(): Int = 3
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
