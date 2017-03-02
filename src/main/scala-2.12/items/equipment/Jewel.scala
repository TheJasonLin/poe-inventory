package items.equipment

class Jewel(
             rarity: String,
             base: String,
             name: Option[String],
             itemLevel: Int,
             identified: Boolean
           ) extends Equipment(rarity, base, name, itemLevel, identified) {

}
