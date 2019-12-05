package parser.item.equipment.accessory

import parser.knowninfo.KnownInfo

class Belt(
            knownInfo: KnownInfo
          ) extends Accessory(knownInfo: KnownInfo) {
  override def width(): Int = 2
}