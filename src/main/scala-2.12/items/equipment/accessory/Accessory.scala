package items.equipment.accessory

import items.equipment.Equipment
import structures.Position

class Accessory(
                 position: Position,
                 rarity: String,
                 base: String,
                 name: Option[String]
               ) extends Equipment(position, rarity, base, name) {

}
