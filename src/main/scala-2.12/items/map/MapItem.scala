package items.map

import items.{CraftableItem, Item}

class MapItem(
           rarity: String,
           base: String,
           name: Option[String],
           itemLevel: Int,
           identified: Boolean,
           val tier: Int
         ) extends CraftableItem(rarity, base, name, itemLevel, identified) {

  override def toString: String = super.toString() + s"[Tier: $tier]"
}