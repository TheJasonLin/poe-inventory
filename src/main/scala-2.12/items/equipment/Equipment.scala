package items.equipment

import items.CraftableItem

class Equipment(
                 rarity: String,
                 base: String,
                 name: Option[String],
                 itemLevel: Int,
                 identified: Boolean,
                 quality: Int
               ) extends CraftableItem(rarity, base, name, 1, true, quality) {

}

object Equipment {
  def getType(rarity: String, name: String): Option[String] = {

    None
  }
}
