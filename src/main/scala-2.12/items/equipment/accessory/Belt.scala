package items.equipment.accessory

import structures.Position

class Belt (
             position: Position,
             rarity: String,
             base: String,
             name: Option[String]
           ) extends Accessory(position, rarity, base, name) {
}