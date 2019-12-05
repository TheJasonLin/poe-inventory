package parser.item.equipment.weapon

import parser.knowninfo.KnownInfo

class Mace(
            knownInfo: KnownInfo,
            oneHanded: Boolean
          ) extends VariableWeapon(knownInfo, oneHanded) {
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
    "Mace",
    "Sceptre"
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
