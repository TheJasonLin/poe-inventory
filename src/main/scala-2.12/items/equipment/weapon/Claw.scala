package items.equipment.weapon

class Claw(
            rarity: String,
            base: String,
            name: Option[String]
          ) extends Weapon(rarity, base, name) {
  override def height(): Int = 2

  override def width(): Int = 2
}

object Claw {
  val identifiers = Array(
    "Fist",
    "Claw",
    "Awl",
    "Paw",
    "Blinder",
    "Gouger",
    "Ripper",
    "Stabber"
  )
}
