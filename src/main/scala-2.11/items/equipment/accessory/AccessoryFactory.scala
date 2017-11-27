package items.equipment.accessory

object AccessoryFactory {
  def create(rarity: String, base: String, name: Option[String], itemLevel: Int, identified: Boolean): Option[Accessory] = {
    val baseWords: Seq[String] = base.split(' ')
    if (baseWords.contains("Amulet")) {
      return Option(new Amulet(rarity, base, name, itemLevel, identified))
    } else if (baseWords.contains("Ring")) {
      return Option(new Ring(rarity, base, name, itemLevel, identified))
    } else if (baseWords.contains("Belt") || base.contains("Sash")) {
      return Option(new Belt(rarity, base, name, itemLevel, identified))
    } else if (baseWords.contains("Quiver")) {
      return Option(new Quiver(rarity, base, name, itemLevel, identified))
    }
    None
  }
}
