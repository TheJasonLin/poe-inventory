package items.equipment

import items.equipment.accessory.AccessoryFactory
import items.equipment.armour.ArmourFactory
import structures.Position

object EquipmentFactory {
  def create(position: Position, rarity: String, base: String, name: Option[String]): Option[Equipment] = {
    var equipmentOption: Option[Equipment] = None
    // Flask
    if(base.contains("Flask")) {
      return Option(new Flask(position, rarity, base, name))
    }
    // Jewel
    if(base.contains("Jewel")) {
      return Option(new Jewel(position, rarity, base, name))
    }
    // Accessory
    equipmentOption = AccessoryFactory.create(position, rarity, base, name)
    if (equipmentOption.isDefined) return equipmentOption
    // Armour
    equipmentOption = ArmourFactory.create(position, rarity, base, name)
    if (equipmentOption.isDefined) return equipmentOption
    // Weapon

    None
  }
}
