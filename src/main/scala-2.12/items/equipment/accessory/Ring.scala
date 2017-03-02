package items.equipment.accessory

class Ring(
            rarity: String,
            base: String,
            name: Option[String],
            itemLevel: Int,
            identified: Boolean
          ) extends Accessory(rarity, base, name, itemLevel, identified) {
}