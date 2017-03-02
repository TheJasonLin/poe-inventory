package items.equipment.weapon

import items.equipment.Equipment

class Weapon(
              rarity: String,
              base: String,
              name: Option[String],
              itemLevel: Int,
              identified: Boolean
            ) extends Equipment(rarity, base, name, itemLevel, identified) {

  override def height(): Int = 3
}
