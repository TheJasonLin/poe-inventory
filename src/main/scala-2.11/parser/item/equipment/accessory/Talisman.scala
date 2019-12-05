package parser.item.equipment.accessory

import parser.item.DBItem
import parser.knowninfo.KnownInfo

class Talisman(
              knownInfo: KnownInfo
              ) extends Amulet(knownInfo) {
  val talismanTier: Int = knownInfo.talismanTier.get
  override def toString: String = super.toString + s"[talismanTier: $talismanTier]"

  override def asDBItem: DBItem = {
    super.asDBItem.copy(
      talismanTier = Option(talismanTier)
    )
  }
}
