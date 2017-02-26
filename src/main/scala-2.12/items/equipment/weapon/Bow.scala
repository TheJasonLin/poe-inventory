package items.equipment.weapon

class Bow(
           rarity: String,
           base: String,
           name: Option[String]
         ) extends Weapon(rarity, base, name) {
  override def height(): Int = 4

  override def width(): Int = 2
}