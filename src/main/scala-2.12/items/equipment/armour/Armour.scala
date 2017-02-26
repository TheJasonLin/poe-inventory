package items.equipment.armour

import items.equipment.Equipment

class Armour(
              rarity: String,
              base: String,
              name: Option[String]
            ) extends Equipment(rarity, base, name) {
  override def width(): Int = 2
}