package items.equipment.weapon

class Axe(
           rarity: String,
           base: String,
           name: Option[String],
           oneHanded: Boolean
         ) extends VariableWeapon(rarity, base, name, oneHanded) {
}

object Axe {
  val identifiers = Array(
    "Hatchet",
    "Axe",
    "Cleaver",
    "Tomahawk",
    "Chopper",
    "Splitter",
    "Woodsplitter",
    "Poleaxe",
    "Labrys",
    "Fleshripper"
  )

  val oneHandedBases = Array(
    "Rusted Hatchet",
    "Jade Hatchet",
    "Boarding Axe",
    "Cleaver",
    "Broad Axe",
    "Arming Axe",
    "Decorative Axe",
    "Spectral Axe",
    "Etched Hatchet",
    "Jasper Axe",
    "Tomahawk",
    "Wrist Chopper",
    "War Axe",
    "Chest Splitter",
    "Ceremonial Axe",
    "Wraith Axe",
    "Engraved Hatchet",
    "Karui Axe",
    "Siege Axe",
    "Reaver Axe",
    "Butcher Axe",
    "Vaal Hatchet",
    "Royal Axe",
    "Infernal Axe",
    "Runic Hatchet"
  )

  val twoHandedBases = Array(
    "Stone Axe",
    "Jade Chopper",
    "Woodsplitter",
    "Poleaxe",
    "Double Axe",
    "Gilded Axe",
    "Shadow Axe",
    "Dagger Axe",
    "Jasper Chopper",
    "Timber Axe",
    "Headsman Axe",
    "Labrys",
    "Noble Axe",
    "Abyssal Axe",
    "Karui Chopper",
    "Talon Axe",
    "Sundering Axe",
    "Ezomyte Axe",
    "Vaal Axe",
    "Despot Axe",
    "Void Axe",
    "Fleshripper"
  )
}
