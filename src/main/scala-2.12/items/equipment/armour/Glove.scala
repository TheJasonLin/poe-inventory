package items.equipment.armour

class Glove(
             rarity: String,
             base: String,
             name: Option[String]
           ) extends Armour(rarity, base, name) {
  override def height(): Int = 2
}

object Glove {
  val identifiers = Array(
    "Gauntlets",
    "Gloves",
    "Mitts"
  )
}
