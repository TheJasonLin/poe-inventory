package items.equipment.armour

import structures.Position

class Shield (
               position: Position,
               rarity: String,
               base: String,
               name: Option[String]
             ) extends Armour(position, rarity, base, name) {

}

object Shield {
  val identifiers = Array(
    "Shield",
    "Buckler",
    "Bundle"
  )
}
