package parser.item.equipment.armour

import parser.item.equipment.Equipment
import parser.knowninfo.KnownInfo

class Armour(
              knownInfo: KnownInfo
            ) extends Equipment(knownInfo) {
  override def width(): Int = 2
}