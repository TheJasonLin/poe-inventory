package parser.item.equipment.armour

import parser.knowninfo.KnownInfo

class Boot(
            knownInfo: KnownInfo
          ) extends Armour(knownInfo) {
  override def height(): Int = 2
}

object Boot {
  val identifiers = Array(
    "Greaves",
    "Boots",
    "Shoes",
    "Slippers"
  )
}
