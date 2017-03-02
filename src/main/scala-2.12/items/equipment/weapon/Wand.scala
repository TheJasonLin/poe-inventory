package items.equipment.weapon

class Wand(
            rarity: String,
            base: String,
            name: Option[String],
            itemLevel: Int,
            identified: Boolean
          ) extends Weapon(rarity, base, name, itemLevel, identified) {

}

object Wand {
  val identifiers = Array(
    "Wand",
    "Horn"
  )
}
