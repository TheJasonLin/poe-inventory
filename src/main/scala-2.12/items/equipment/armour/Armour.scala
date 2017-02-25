package items.equipment.armour

import items.equipment.Equipment
import structures.Position

class Armour(
              position: Position,
              rarity: String,
              base: String,
              name: Option[String]
            ) extends Equipment (position, rarity, base, name) {
}