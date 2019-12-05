package parser.item.equipment.weapon

import parser.knowninfo.KnownInfo

class Wand(
            knownInfo: KnownInfo
          ) extends Weapon(knownInfo) {

}

object Wand {
  val identifiers = Array(
    "Wand",
    "Horn"
  )
}
