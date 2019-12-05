package parser.item.equipment

import parser.item.equipment.accessory.AccessoryFactory
import parser.item.equipment.armour.ArmourFactory
import parser.item.equipment.weapon.WeaponFactory
import parser.knowninfo.KnownInfo

object EquipmentFactory {
  def create(knownInfo: KnownInfo): Option[Equipment] = {
    var equipmentOption: Option[Equipment] = None

    if (knownInfo.AssumeEquipment.isFlask) {
      return Option(new Flask(knownInfo))
    }

    if (knownInfo.AssumeEquipment.isJewel) {
      return Option(new Jewel(knownInfo))
    }

    // Accessory
    equipmentOption = AccessoryFactory.create(knownInfo)
    if (equipmentOption.isDefined) return equipmentOption

    // Armour
    equipmentOption = ArmourFactory.create(knownInfo)
    if (equipmentOption.isDefined) return equipmentOption

    // Weapon
    equipmentOption = WeaponFactory.create(knownInfo)
    if(equipmentOption.isDefined) return equipmentOption

    None
  }
}
