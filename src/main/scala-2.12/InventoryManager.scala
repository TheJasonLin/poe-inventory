import items.DivinationCard
import items.currency.Currency
import items.equipment.Equipment
import items.equipment.accessory.{Amulet, Belt, Ring}
import items.equipment.armour.{BodyArmour, Boot, Glove, Helmet}
import items.equipment.weapon.Weapon

object InventoryManager {
  val rowCount: Int = Inventory.height
  val columnCount: Int = Inventory.width

  val rows: List[Int] = List.range(0, rowCount)
  val columns: List[Int] = List.range(0, columnCount)


  def emptyInventory(): Unit = {
    Stash.resetTab()
    Inventory.update()
    Stash.activateCurrencyTab()
    dumpCurrencies()
    Stash.activateEssenceTab()
    dumpEssences()
    Stash.activateDivinationTab()
    dumpDivinationCards()
  }

  def dumpChaosEquipment(): Unit = {
    Stash.resetTab()
    Inventory.update()

    val chaosEquipment: Seq[Equipment] = Inventory.chaosEquipment
    val helmets: Seq[Helmet] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Helmet]).map((equipment) => equipment.asInstanceOf[Helmet])
    val boots: Seq[Boot] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Boot]).map((equipment) => equipment.asInstanceOf[Boot])
    val gloves: Seq[Glove] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Glove]).map((equipment) => equipment.asInstanceOf[Glove])
    val bodys: Seq[BodyArmour] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[BodyArmour]).map((equipment) => equipment.asInstanceOf[BodyArmour])
    val weapons: Seq[Weapon] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Weapon]).map((equipment) => equipment.asInstanceOf[Weapon])
    val rings: Seq[Ring] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Ring]).map((equipment) => equipment.asInstanceOf[Ring])
    val amulets: Seq[Amulet] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Amulet]).map((equipment) => equipment.asInstanceOf[Amulet])
    val belts: Seq[Belt] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Belt]).map((equipment) => equipment.asInstanceOf[Belt])

    if(helmets.nonEmpty) {
      Stash.activateHelmetTab()
      helmets.foreach((helmet: Helmet) => Inventory.sendItemToAllocation(helmet, Stash.helmetAllocation))
    }

    if(boots.nonEmpty) {
      Stash.activateBootTab()
      boots.foreach((boot: Boot) => Inventory.sendItemToAllocation(boot, Stash.bootAllocation))
    }

    if(gloves.nonEmpty) {
      Stash.activateGloveTab()
      gloves.foreach((glove: Glove) => Inventory.sendItemToAllocation(glove, Stash.gloveAllocation))
    }

    if(bodys.nonEmpty) {
      Stash.activateBodyTab()
      bodys.foreach((bodyArmour: BodyArmour) => Inventory.sendItemToAllocation(bodyArmour, Stash.bodyAllocation))
    }

    if(weapons.nonEmpty) {
      Stash.activateWeaponTab()
      weapons.foreach((weapon: Weapon) => Inventory.sendItemToAllocation(weapon, Stash.weaponAllocation))
    }

    if(rings.nonEmpty) {
      Stash.activateRingTab()
      rings.foreach((ring: Ring) => Inventory.sendItemToAllocation(ring, Stash.ringAllocation))
    }

    if(amulets.nonEmpty) {
      Stash.activateAmuletTab()
      amulets.foreach((amulet: Amulet) => Inventory.sendItemToAllocation(amulet, Stash.amuletAllocation))
    }

    if(belts.nonEmpty) {
      Stash.activateBeltTab()
      belts.foreach((belt: Belt) => Inventory.sendItemToAllocation(belt, Stash.beltAllocation))
    }
  }

  private def dumpCurrencies(): Unit = {
    Inventory.basicCurrencies.foreach((currency: Currency) => {
      Inventory.sendItemToStash(currency)
    })
  }

  private def dumpEssences(): Unit = {
    Inventory.essences.foreach((essence) => {
      Inventory.sendItemToStash(essence)
    })
  }

  private def dumpDivinationCards(): Unit = {
    Inventory.divinationCards.foreach((divinationCard: DivinationCard) => {
      Inventory.sendItemToStash(divinationCard)
    })
  }
}
