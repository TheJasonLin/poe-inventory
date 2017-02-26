package items.equipment.armour

class Shield(
              rarity: String,
              base: String,
              name: Option[String]
            ) extends Armour(rarity, base, name) {
  override def height(): Int = 3
}

object Shield {
  val identifiers = Array(
    "Shield",
    "Buckler",
    "Bundle"
  )
}
