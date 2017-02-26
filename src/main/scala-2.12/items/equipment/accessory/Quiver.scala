package items.equipment.accessory

class Quiver(
              rarity: String,
              base: String,
              name: Option[String]
            ) extends Accessory(rarity, base, name) {
  override def width(): Int = 2
  override def height(): Int = 3
}
