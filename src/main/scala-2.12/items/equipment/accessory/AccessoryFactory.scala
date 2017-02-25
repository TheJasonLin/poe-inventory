package items.equipment.accessory

import structures.Position

object AccessoryFactory {
  def create(position: Position, rarity: String, base: String, name: Option[String]): Option[Belt] = {
    if (base.contains("Amulet")) {
      Option(new Belt(position, rarity, base, name))
    } else if (base.contains("Ring")) {
      Option(new Ring(position, rarity, base, name))
    } else if (base.contains("Belt") || base.contains("Sash")) {
      Option(new Belt(position, rarity, base, name))
    }
    None
  }
}
