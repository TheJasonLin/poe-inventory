package items.equipment.accessory

class Quiver(
              rarity: String,
              base: String,
              name: Option[String],
              itemLevel: Int,
              identified: Boolean
            ) extends Accessory(rarity, base, name, itemLevel, identified) {
  override def width(): Int = 2
  override def height(): Int = 3
}
