package items.equipment.armour

class Glove(
             rarity: String,
             base: String,
             name: Option[String],
             itemLevel: Int,
             identified: Boolean
           ) extends Armour(rarity, base, name, itemLevel, identified) {
  override def height(): Int = 2
}

object Glove {
  val identifiers = Array(
    "Gauntlets",
    "Gloves",
    "Mitts"
  )
}
