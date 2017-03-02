package items.equipment

import items.CraftableItem

class Equipment(
                 rarity: String,
                 base: String,
                 name: Option[String],
                 itemLevel: Int,
                 identified: Boolean
               ) extends CraftableItem(rarity, base, name, itemLevel, identified) {

}

object Equipment {
  def getType(rarity: String, name: String): Option[String] = {

    None
  }
}
