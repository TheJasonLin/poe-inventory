package items.equipment.weapon

class VariableWeapon(
                      rarity: String,
                      base: String,
                      name: Option[String],
                      itemLevel: Int,
                      identified: Boolean,
                      quality: Int,
                      val oneHanded: Boolean
                    ) extends Weapon(rarity, base, name, itemLevel, identified, quality) {
  override def height(): Int = if (oneHanded) 3 else 4

  override def width(): Int = 2
}
