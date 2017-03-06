package items

class Leaguestone(
                rarity: String,
                base: String,
                name: Option[String],
                itemLevel: Int,
                identified: Boolean
              ) extends CraftableItem(rarity, base, name, itemLevel, identified) {

}
