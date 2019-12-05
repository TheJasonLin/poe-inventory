package parser.item.equipment

import parser.knowninfo.KnownInfo

class Flask(
             knownInfo: KnownInfo
           ) extends Equipment(knownInfo) {
  override def height(): Int = 2
}
