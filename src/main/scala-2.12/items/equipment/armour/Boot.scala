package items.equipment.armour

class Boot(
            rarity: String,
            base: String,
            name: Option[String]
          ) extends Armour(rarity, base, name) {
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
