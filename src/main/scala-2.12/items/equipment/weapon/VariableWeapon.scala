package items.equipment.weapon

class VariableWeapon(
                      rarity: String,
                      base: String,
                      name: Option[String],
                      val oneHanded: Boolean
                    ) extends Weapon(rarity, base, name) {
  override def height(): Int = if (oneHanded) 3 else 4

  override def width(): Int = 2
}
