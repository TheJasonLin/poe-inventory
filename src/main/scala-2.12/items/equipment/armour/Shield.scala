package items.equipment.armour

class Shield(
              rarity: String,
              base: String,
              name: Option[String],
              itemLevel: Int,
              identified: Boolean
            ) extends Armour(rarity, base, name, itemLevel, identified) {
  override def height(): Int = 3
}

object Shield {
  val identifiers = Array(
    "Shield",
    "Buckler",
    "Bundle"
  )
}
