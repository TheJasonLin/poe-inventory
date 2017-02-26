package items.equipment

import items.Item

class Equipment(
                 rarity: String,
                 base: String,
                 name: Option[String]
               ) extends Item(rarity, base, name) {

}

object Equipment {
  def getType(rarity: String, name: String): Option[String] = {

    None
  }
}
