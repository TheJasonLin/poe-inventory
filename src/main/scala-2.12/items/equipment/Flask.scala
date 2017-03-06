package items.equipment

class Flask(
             rarity: String,
             base: String,
             name: Option[String],
             itemLevel: Int,
             identified: Boolean,
             quality: Int
           ) extends Equipment(rarity, base, name, itemLevel, identified, quality) {

}
