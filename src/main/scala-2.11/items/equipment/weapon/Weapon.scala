package items.equipment.weapon

import items.equipment.Equipment

class Weapon(
              rarity: String,
              base: String,
              name: Option[String],
              itemLevel: Int,
              identified: Boolean,
              quality: Int
            ) extends Equipment(rarity, base, name, itemLevel, identified, quality) {

  override def height(): Int = 3
}
