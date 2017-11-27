package items

class MapItem(
           rarity: String,
           base: String,
           name: Option[String],
           itemLevel: Int,
           identified: Boolean,
           quality: Int,
           val tier: Int
         ) extends CraftableItem(rarity, base, name, itemLevel, identified, quality) {

  override def toString: String = super.toString() + s"[Tier: $tier]"
}