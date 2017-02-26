package items.equipment

import items.equipment.accessory.AccessoryFactory
import items.equipment.armour.ArmourFactory
import items.equipment.weapon.WeaponFactory
import structures.Position

object EquipmentFactory {
  def create(rarity: String, base: String, name: Option[String]): Option[Equipment] = {
    var equipmentOption: Option[Equipment] = None
    // Flask
    if(base.contains("Flask")) {
      return Option(new Flask(rarity, base, name))
    }
    // Jewel
    if(base.contains("Jewel")) {
      return Option(new Jewel(rarity, base, name))
    }
    // Accessory
    equipmentOption = AccessoryFactory.create(rarity, base, name)
    if (equipmentOption.isDefined) return equipmentOption
    // Armour
    equipmentOption = ArmourFactory.create(rarity, base, name)
    if (equipmentOption.isDefined) return equipmentOption
    // Weapon
    equipmentOption = WeaponFactory.create(rarity, base, name)
    if(equipmentOption.isDefined) return equipmentOption

    None
  }
}
