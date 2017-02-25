package items.equipment

import structures.Position

class Weapon(
              position: Position,
              rarity: String,
              base: String,
              name: Option[String]
            ) extends Equipment (position, rarity, base, name) {

}
