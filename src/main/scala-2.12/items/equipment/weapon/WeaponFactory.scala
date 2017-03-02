package items.equipment.weapon

import items.Item

object WeaponFactory {
  def create(rarity: String, base: String, name: Option[String], itemLevel: Int, identified: Boolean): Option[Weapon] = {
    if (base.contains("Bow")) {
      return Option(new Bow(rarity, base, name, itemLevel, identified))
    }

    if (Item.matchesIdentifier(base, Axe.identifiers)) {
      if (Axe.oneHandedBases.contains(base)) {
        return Option(new Axe(rarity, base, name, itemLevel, identified, true))
      } else {
        return Option(new Axe(rarity, base, name, itemLevel, identified, false))
      }
    }

    if (Item.matchesIdentifier(base, Claw.identifiers)) {
      return Option(new Claw(rarity, base, name, itemLevel, identified))
    }

    if (
      Item.matchesIdentifier(base, Dagger.identifiers) ||
        Dagger.oneHandedBases.contains(base)
    ) {
      return Option(new Dagger(rarity, base, name, itemLevel, identified))
    }

    if (Item.matchesIdentifier(base, Mace.identifiers)) {
      val matchesOneHandedIdentifier = Item.matchesIdentifier(base, Mace.oneHandedIdentifiers)
      val matchesOneHandedBase = Mace.oneHandedBases.contains(base)
      if (matchesOneHandedIdentifier || matchesOneHandedBase) {
        return Option(new Mace(rarity, base, name, itemLevel, identified, true))
      } else {
        return Option(new Mace(rarity, base, name, itemLevel, identified, false))
      }
    }

    if (Item.matchesIdentifier(base, Staff.identifiers)) {
      return Option(new Staff(rarity, base, name, itemLevel, identified))
    }

    if (
      Item.matchesIdentifier(base, Sword.identifiers)
        && !Sword.identifierExceptions.contains(base)
    ) {
      if (Item.matchesIdentifier(base, Sword.oneHandedIdentifiers) || Sword.oneHandedBases.contains(base)) {
        //it's one handed, but need to figure out if it's a thrusting sword or regular
        val isThrusting: Boolean = Item.matchesIdentifier(base, Sword.thrustingIdentifiers)
        return Option(new Sword(rarity, base, name, itemLevel, identified, true, isThrusting))
      } else {
        return Option(new Sword(rarity, base, name, itemLevel, identified, false, false))
      }
    }

    if (Item.matchesIdentifier(base, Wand.identifiers)) {
      return Option(new Wand(rarity, base, name, itemLevel, identified))
    }

    None
  }
}
