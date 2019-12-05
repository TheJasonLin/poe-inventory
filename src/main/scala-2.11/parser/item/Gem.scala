package parser.item

import parser.knowninfo.KnownInfo

/**
  * All Gems by default have itemLevel 1
  * All Gems by default are identified
  */
class Gem(
         knownInfo: KnownInfo
         ) extends CraftableItem(knownInfo) {

}