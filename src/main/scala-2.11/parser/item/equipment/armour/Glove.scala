package parser.item.equipment.armour

import parser.knowninfo.KnownInfo

class Glove(
             knownInfo: KnownInfo
           ) extends Armour(knownInfo) {
  override def height(): Int = 2
}

object Glove {
  val identifiers = Array(
    "Gauntlets",
    "Gloves",
    "Mitts"
  )
}
