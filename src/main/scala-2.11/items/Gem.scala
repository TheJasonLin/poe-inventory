package items

/**
  * All Gems by default have itemLevel 1
  * All Gems by default are identified
  */
class Gem(
           rarity: String,
           base: String,
           name: Option[String],
           quality: Int
         ) extends CraftableItem(rarity, base, name, 1, true, quality) {

}