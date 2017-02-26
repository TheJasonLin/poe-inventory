package items.equipment.weapon

import items.equipment.Equipment

class Weapon(
              rarity: String,
              base: String,
              name: Option[String]
            ) extends Equipment(rarity, base, name) {

  override def height(): Int = 3
}
