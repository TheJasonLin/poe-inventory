package items.equipment.armour

import structures.Position

class Helmet(
              position: Position,
              rarity: String,
              base: String,
              name: Option[String]
            ) extends Armour(position, rarity, base, name) {

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
