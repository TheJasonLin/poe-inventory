package items.map

import items.Item

class MapItem(
           rarity: String,
           base: String,
           name: Option[String],
           val tier: Int
         ) extends Item(rarity, base, name) {

  override def toString: String = super.toString() + s"[Tier: $tier]"
}