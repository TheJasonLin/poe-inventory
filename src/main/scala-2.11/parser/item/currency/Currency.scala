package parser.item.currency

import parser.item.{Item, StackSize}
import parser.knowninfo.KnownInfo

class Currency(
                knownInfo: KnownInfo
              ) extends Item(knownInfo) {
  var stackSize: Option[StackSize] = knownInfo.stackSize
}