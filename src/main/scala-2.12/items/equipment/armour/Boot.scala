package items.equipment.armour

class Boot(
            rarity: String,
            base: String,
            name: Option[String],
            itemLevel: Int,
            identified: Boolean
          ) extends Armour(rarity, base, name, itemLevel, identified) {
  override def height(): Int = 2
}

object Boot {
  val identifiers = Array(
    "Greaves",
    "Boots",
    "Shoes",
    "Slippers"
  )
}
