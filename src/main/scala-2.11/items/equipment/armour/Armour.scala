package items.equipment.armour

import items.equipment.Equipment

class Armour(
              rarity: String,
              base: String,
              name: Option[String],
              itemLevel: Int,
              identified: Boolean,
              quality: Int
            ) extends Equipment(rarity, base, name, itemLevel, identified, quality) {
  override def width(): Int = 2
}