package items.equipment.accessory

object AccessoryFactory {
  def create(rarity: String, base: String, name: Option[String]): Option[Accessory] = {
    if (base.contains("Amulet")) {
      return Option(new Amulet(rarity, base, name))
    } else if (base.contains("Ring")) {
      return Option(new Ring(rarity, base, name))
    } else if (base.contains("Belt") || base.contains("Sash")) {
      return Option(new Belt(rarity, base, name))
    } else if (base.contains("Quiver")) {
      return Option(new Quiver(rarity, base, name))
    }
    None
  }
}
