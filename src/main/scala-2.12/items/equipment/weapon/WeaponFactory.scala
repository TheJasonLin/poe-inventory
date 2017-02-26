package items.equipment.weapon

import items.Item

object WeaponFactory {
  def create(rarity: String, base: String, name: Option[String]): Option[Weapon] = {
    if (base.contains("Bow")) {
      return Option(new Bow(rarity, base, name))
    }

    if (Item.matchesIdentifier(base, Axe.identifiers)) {
      if (Axe.oneHandedBases.contains(base)) {
        return Option(new Axe(rarity, base, name, true))
      } else {
        return Option(new Axe(rarity, base, name, false))
      }
    }

    if (Item.matchesIdentifier(base, Claw.identifiers)) {
      return Option(new Claw(rarity, base, name))
    }

    if (
      Item.matchesIdentifier(base, Dagger.identifiers) ||
        Dagger.oneHandedBases.contains(base)
    ) {
      return Option(new Dagger(rarity, base, name))
    }

    if (Item.matchesIdentifier(base, Mace.identifiers)) {
      val matchesOneHandedIdentifier = Item.matchesIdentifier(base, Mace.oneHandedIdentifiers)
      val matchesOneHandedBase = Mace.oneHandedBases.contains(base)
      if (matchesOneHandedIdentifier || matchesOneHandedBase) {
        return Option(new Mace(rarity, base, name, true))
      } else {
        return Option(new Mace(rarity, base, name, false))
      }
    }

    if (Item.matchesIdentifier(base, Staff.identifiers)) {
      return Option(new Staff(rarity, base, name))
    }

    if (
      Item.matchesIdentifier(base, Sword.identifiers)
        && !Sword.identifierExceptions.contains(base)
    ) {
      if (Item.matchesIdentifier(base, Sword.oneHandedIdentifiers) || Sword.oneHandedBases.contains(base)) {
        return Option(new Sword(rarity, base, name, true))
      } else {
        return Option(new Sword(rarity, base, name, false))
      }
    }

    if (Item.matchesIdentifier(base, Wand.identifiers)) {
      return Option(new Wand(rarity, base, name))
    }

    None
  }
}
