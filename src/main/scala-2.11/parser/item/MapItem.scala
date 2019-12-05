package parser.item

import parser.knowninfo.KnownInfo

class MapItem(
               knownInfo: KnownInfo
             ) extends CraftableItem(knownInfo) {
  private val mapInfo = knownInfo.mapInfo.get
  val tier: Int = mapInfo.tier
  val itemQuantity: Int = mapInfo.itemQuantity
  val itemRarity: Int = mapInfo.itemRarity
  val packSize: Int = mapInfo.packSize

  override def toString: String = super.toString() + s"[Tier: $tier]"

  override def asDBItem: DBItem = {
    super.asDBItem.copy(
      mapTier = Option(tier)
    )
  }
}