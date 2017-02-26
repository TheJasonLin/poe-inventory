package items.equipment.weapon

class Mace(
            rarity: String,
            base: String,
            name: Option[String],
            oneHanded: Boolean
          ) extends VariableWeapon(rarity, base, name, oneHanded) {
}

object Mace {
  val identifiers = Array(
    "Club",
    "Hammer",
    "Mace",
    "Breaker",
    "Tenderizer",
    "Gavel",
    "Pernarch",
    "Sceptre",
    "Fetish",
    "Sekhem",
    "Maul",
    "Mallet",
    "Sledgehammer",
    "Star",
    "Steelhead",
    "Piledriver",
    "Meatgrinder"
  )

  val oneHandedIdentifiers = Array(
    "Club",
    "Mace"
  )

  /**
    * Doesn't include names with the oneHandedIdentifiers
    */
  val oneHandedBases = Array(
    "Stone Hammer",
    "War Hammer",
    "Rock Breaker",
    "Battle Hammer",
    "Tenderizer",
    "Gavel",
    "Legion Hammer",
    "Pernarch"
  )
}
