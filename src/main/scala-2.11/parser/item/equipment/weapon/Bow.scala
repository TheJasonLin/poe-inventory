package parser.item.equipment.weapon

import parser.knowninfo.KnownInfo

class Bow(
           knownInfo: KnownInfo
         ) extends Weapon(knownInfo) {
  override def height(): Int = 4

  override def width(): Int = 2
}