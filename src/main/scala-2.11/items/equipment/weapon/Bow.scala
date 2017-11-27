package items.equipment.weapon

class Bow(
           rarity: String,
           base: String,
           name: Option[String],
           itemLevel: Int,
           identified: Boolean,
           quality: Int
         ) extends Weapon(rarity, base, name, itemLevel, identified, quality) {
  override def height(): Int = 4

  override def width(): Int = 2
}