package items.equipment.accessory

import items.equipment.Equipment
import structures.Position

class Accessory(
                 rarity: String,
                 base: String,
                 name: Option[String],
                 itemLevel: Int,
                 identified: Boolean
               ) extends Equipment(rarity, base, name, itemLevel, identified, 0) {

}
