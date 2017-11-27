package items

class CraftableItem(rarity: String, base: String, name: Option[String], val itemLevel: Int, val identified: Boolean, val quality: Int) extends Item(rarity, base, name) {

  override def toString: String = super.toString() + s"[ilvl: $itemLevel, id: $identified, quality: $quality]"
}
