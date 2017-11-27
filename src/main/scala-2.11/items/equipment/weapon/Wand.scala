package items.equipment.weapon

class Wand(
            rarity: String,
            base: String,
            name: Option[String],
            itemLevel: Int,
            identified: Boolean,
            quality: Int
          ) extends Weapon(rarity, base, name, itemLevel, identified, quality) {

}

object Wand {
  val identifiers = Array(
    "Wand",
    "Horn"
  )
}
