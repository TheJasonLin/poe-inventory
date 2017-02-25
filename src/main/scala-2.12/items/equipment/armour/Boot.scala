package items.equipment.armour

import structures.Position

class Boot(
             position: Position,
             rarity: String,
             base: String,
             name: Option[String]
           ) extends Armour(position, rarity, base, name) {

}

object Boot {
  val identifiers = Array(
    "Greaves",
    "Boots",
    "Shoes",
    "Slippers"
  )
}
