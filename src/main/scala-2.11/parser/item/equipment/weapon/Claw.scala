package parser.item.equipment.weapon

import parser.knowninfo.KnownInfo

class Claw(
            knownInfo: KnownInfo
          ) extends Weapon(knownInfo) {
  override def height(): Int = 2

  override def width(): Int = 2
}

object Claw {
  val identifiers = Array(
    "Fist",
    "Claw",
    "Awl",
    "Paw",
    "Blinder",
    "Gouger",
    "Ripper",
    "Stabber"
  )
}
