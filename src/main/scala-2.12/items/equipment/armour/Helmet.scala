package items.equipment.armour

class Helmet(
              rarity: String,
              base: String,
              name: Option[String],
              itemLevel: Int,
              identified: Boolean,
              quality: Int
            ) extends Armour(rarity, base, name, itemLevel, identified, quality) {
  override def height(): Int = 2
}

object Helmet {
  val identifiers = Array(
    "Hat",
    "Helmet",
    "Burgonet",
    "Cap",
    "Tricorne",
    "Hood",
    "Pelt",
    "Circlet",
    "Cage",
    "Helm",
    "Sallet",
    "Bascinet",
    "Coif",
    "Crown",
    "Mask"
  )
}
