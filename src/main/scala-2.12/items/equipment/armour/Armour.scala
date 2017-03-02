package items.equipment.armour

import items.equipment.Equipment

class Armour(
              rarity: String,
              base: String,
              name: Option[String],
              itemLevel: Int,
              identified: Boolean
            ) extends Equipment(rarity, base, name, itemLevel, identified) {
  override def width(): Int = 2
}