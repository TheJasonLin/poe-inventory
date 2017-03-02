package items.equipment.weapon

class Staff(
             rarity: String,
             base: String,
             name: Option[String],
             itemLevel: Int,
             identified: Boolean
           ) extends Weapon(rarity, base, name, itemLevel, identified) {
  override def height(): Int = 4

  override def width(): Int = 2
}

object Staff {
  val identifiers = Array(
    "Branch",
    "Staff",
    "Quarterstaff",
    "Lathi"
  )
}
