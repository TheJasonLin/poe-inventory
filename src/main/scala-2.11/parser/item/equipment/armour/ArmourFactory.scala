package parser.item.equipment.armour

import parser.item.Item
import parser.item.equipment.Equipment
import parser.knowninfo.KnownInfo

object ArmourFactory {
  def create(knownInfo: KnownInfo): Option[Equipment] = {
    val typeLine = knownInfo.typeLine
    if(Item.matchesIdentifier(typeLine, Helmet.identifiers)) {
      return Option(new Helmet(knownInfo))
    }

    if(Item.matchesIdentifier(typeLine, Boot.identifiers)) {
      return Option(new Boot(knownInfo))
    }

    if(Item.matchesIdentifier(typeLine, Glove.identifiers)) {
      return Option(new Glove(knownInfo))
    }

    if(Item.matchesIdentifier(typeLine, Shield.identifiers)) {
      val tallShield = Item.containsIdentifier(typeLine, Shield.tallShieldIdentifiers)
      val shortShield = Item.containsIdentifier(typeLine, Shield.shortShieldIdentifiers)
      return Option(new Shield(knownInfo, tallShield, shortShield))
    }

    if(Item.matchesIdentifier(typeLine, BodyArmour.identifiers) || BodyArmour.baseNames.contains(typeLine)) {
      return Option(new BodyArmour(knownInfo))
    }

    None
  }
}
