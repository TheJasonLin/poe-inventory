package parser.item.equipment

import parser.item.CraftableItem
import parser.knowninfo.KnownInfo

class Equipment(
               knownInfo: KnownInfo
               ) extends CraftableItem(knownInfo) {

}

object Equipment {
  def getType(rarity: String, name: String): Option[String] = {

    None
  }
}
