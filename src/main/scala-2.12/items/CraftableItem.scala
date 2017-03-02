package items

class CraftableItem(
                     rarity: String,
                     base: String,
                     name: Option[String],
                     val itemLevel: Int,
                     val identified: Boolean
                   ) extends Item(rarity, base, name) {

  override def toString: String = super.toString() + s"[ilvl: $itemLevel, id: $identified]"
}
