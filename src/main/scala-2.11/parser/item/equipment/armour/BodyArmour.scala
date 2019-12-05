package parser.item.equipment.armour

import parser.knowninfo.KnownInfo

class BodyArmour(
                  knownInfo: KnownInfo
                ) extends Armour(knownInfo) {
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

  val baseNames = Array(
    "Full Dragonscale"
  )
}