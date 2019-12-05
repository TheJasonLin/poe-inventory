package parser.item.equipment.weapon

import parser.item.Item
import parser.knowninfo.KnownInfo

object WeaponFactory {
  def create(knownInfo: KnownInfo): Option[Weapon] = {
    val typeLine = knownInfo.typeLine
    if (typeLine.contains("Bow")) {
      return Option(new Bow(knownInfo))
    }

    if (Item.matchesIdentifier(typeLine, Axe.identifiers)) {
      if (Axe.oneHandedBases.contains(typeLine)) {
        return Option(new Axe(knownInfo, true))
      } else {
        return Option(new Axe(knownInfo, false))
      }
    }

    if (Item.matchesIdentifier(typeLine, Claw.identifiers)) {
      return Option(new Claw(knownInfo))
    }

    if (
      Item.matchesIdentifier(typeLine, Dagger.identifiers) ||
        Dagger.oneHandedBases.contains(typeLine)
    ) {
      return Option(new Dagger(knownInfo))
    }

    if (Item.matchesIdentifier(typeLine, Mace.identifiers)) {
      val matchesOneHandedIdentifier = Item.matchesIdentifier(typeLine, Mace.oneHandedIdentifiers)
      val matchesOneHandedBase = Mace.oneHandedBases.contains(typeLine)
      if (matchesOneHandedIdentifier || matchesOneHandedBase) {
        return Option(new Mace(knownInfo, true))
      } else {
        return Option(new Mace(knownInfo, false))
      }
    }

    if (Item.matchesIdentifier(typeLine, Staff.identifiers)) {
      return Option(new Staff(knownInfo))
    }

    if (
      Item.matchesIdentifier(typeLine, Sword.identifiers)
        && !Sword.identifierExceptions.contains(typeLine)
    ) {
      if (Item.matchesIdentifier(typeLine, Sword.oneHandedIdentifiers) || Sword.oneHandedBases.contains(typeLine)) {
        //it's one handed, but need to figure out if it's a thrusting sword or regular
        val isThrusting: Boolean = Item.matchesIdentifier(typeLine, Sword.thrustingIdentifiers)
        return Option(new Sword(knownInfo, true, isThrusting))
      } else {
        return Option(new Sword(knownInfo, false, false))
      }
    }

    if (Item.matchesIdentifier(typeLine, Wand.identifiers)) {
      return Option(new Wand(knownInfo))
    }

    None
  }
}
