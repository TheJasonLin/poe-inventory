package items.equipment.accessory

class Belt(
            rarity: String,
            base: String,
            name: Option[String]
          ) extends Accessory(rarity, base, name) {
  override def width(): Int = 2
}