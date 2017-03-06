package items.equipment.armour

import items.Item
import items.equipment.Equipment
import structures.Position

object ArmourFactory {
  def create(rarity: String, base: String, name: Option[String], itemLevel: Int, identified: Boolean, quality: Int): Option[Equipment] = {
    if(Item.matchesIdentifier(base, Helmet.identifiers)) {
      return Option(new Helmet(rarity, base, name, itemLevel, identified, quality))
    }

    if(Item.matchesIdentifier(base, Boot.identifiers)) {
      return Option(new Boot(rarity, base, name, itemLevel, identified, quality))
    }

    if(Item.matchesIdentifier(base, Glove.identifiers)) {
      return Option(new Glove(rarity, base, name, itemLevel, identified, quality))
    }

    if(Item.matchesIdentifier(base, Shield.identifiers)) {
      return Option(new Helmet(rarity, base, name, itemLevel, identified, quality))
    }

    if(Item.matchesIdentifier(base, BodyArmour.identifiers)) {
      return Option(new BodyArmour(rarity, base, name, itemLevel, identified, quality))
    }

    None
  }
}
