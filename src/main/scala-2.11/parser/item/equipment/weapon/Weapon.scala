package parser.item.equipment.weapon

import parser.item.equipment.Equipment
import parser.knowninfo.KnownInfo

class Weapon(
              knownInfo: KnownInfo
            ) extends Equipment(knownInfo) {

  override def height(): Int = 3
}
