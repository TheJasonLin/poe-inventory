package items.equipment

import items.Item
import structures.Position

class Equipment(
                 position: Position,
                 rarity: String,
                 base: String,
                 name: Option[String]
               ) extends Item (position, rarity, base, name) {

}

object Equipment {
  def getType(rarity: String, name: String): Option[String] = {

    None
  }
}
