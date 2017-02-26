package items.equipment.weapon

class Wand(
            rarity: String,
            base: String,
            name: Option[String]
          ) extends Weapon(rarity, base, name) {

}

object Wand {
  val identifiers = Array(
    "Wand",
    "Horn"
  )
}
