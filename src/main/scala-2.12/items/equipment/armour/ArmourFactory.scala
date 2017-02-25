package items.equipment.armour

import items.equipment.Equipment
import structures.Position

object ArmourFactory {
  def create(position: Position, rarity: String, base: String, name: Option[String]): Option[Equipment] = {
    if(matchesIdentifier(base, Helmet.identifiers)) {
      return Option(new Helmet(position, rarity, base, name))
    }

    if(matchesIdentifier(base, Boot.identifiers)) {
      return Option(new Boot(position, rarity, base, name))
    }

    if(matchesIdentifier(base, Glove.identifiers)) {
      return Option(new Glove(position, rarity, base, name))
    }

    if(matchesIdentifier(base, Shield.identifiers)) {
      return Option(new Helmet(position, rarity, base, name))
    }

    if(matchesIdentifier(base, BodyArmour.identifiers)) {
      return Option(new BodyArmour(position, rarity, base, name))
    }

    None
  }

  def matchesIdentifier(base: String, identifiers: Array[String]): Boolean = {
    val baseWords = base.split(" ")
    baseWords.exists((baseWord) => {
      identifiers.contains(baseWord)
    })
  }
}
