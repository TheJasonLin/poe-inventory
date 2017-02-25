package items.equipment.armour

import structures.Position

class Glove(
              position: Position,
              rarity: String,
              base: String,
              name: Option[String]
            ) extends Armour(position, rarity, base, name) {

}

object Glove {
  val identifiers = Array(
    "Gauntlets",
    "Gloves",
    "Mitts"
  )
}
