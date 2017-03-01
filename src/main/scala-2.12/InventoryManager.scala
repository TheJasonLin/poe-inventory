import items.{DivinationCard, Item}
import items.currency.Currency
import items.equipment.Equipment
import items.equipment.accessory.{Amulet, Belt, Ring}
import items.equipment.armour.{BodyArmour, Boot, Glove, Helmet}
import items.equipment.weapon.Weapon
import screen.Screen

object InventoryManager {
  def emptyInventory(): Unit = {
    prepareInventoryAction()

    Stash.activateCurrencyTab(Mode.NO_READ)
    dumpCurrencies()
    Stash.activateEssenceTab(Mode.NO_READ)
    dumpEssences()
    Stash.activateDivinationTab(Mode.NO_READ)
    dumpDivinationCards()

    dumpChaosEquipment()

    markContainersOutOfDate()
  }

  def extractChaosSet(): Unit = {
    Stash.resetTab()
    // helmet
    Stash.activateHelmetTab(Mode.READ_POSITIONS_AND_ITEMS)
    var tab: Tab = Stash.currentTab().get
    var itemOption: Option[Item] = tab.items.find(_.isInstanceOf[Helmet])
    if(itemOption.isDefined) {
      tab.ctrlClickItem(itemOption.get)
    }
    // boot
    Stash.activateBootTab(Mode.READ_POSITIONS_AND_ITEMS)
    tab = Stash.currentTab().get
    itemOption = tab.items.find(_.isInstanceOf[Boot])
    if(itemOption.isDefined) tab.ctrlClickItem(itemOption.get)
    // Glove
    Stash.activateGloveTab(Mode.READ_POSITIONS_AND_ITEMS)
    tab = Stash.currentTab().get
    itemOption = tab.items.find(_.isInstanceOf[Glove])
    if(itemOption.isDefined) tab.ctrlClickItem(itemOption.get)
    // Body
    Stash.activateBodyTab(Mode.READ_POSITIONS_AND_ITEMS)
    tab = Stash.currentTab().get
    itemOption = tab.items.find(_.isInstanceOf[BodyArmour])
    if(itemOption.isDefined) tab.ctrlClickItem(itemOption.get)
    // Weapon
    Stash.activateWeaponTab(Mode.READ_POSITIONS_AND_ITEMS)
    tab = Stash.currentTab().get
    tab.items
      .filter(_.isInstanceOf[Weapon])
      .slice(0, 2)
      .foreach((item: Item) => {
        tab.ctrlClickItem(item)
      })
    // Ring
    Stash.activateRingTab(Mode.READ_POSITIONS_AND_ITEMS)
    tab = Stash.currentTab().get
    tab.items
      .filter(_.isInstanceOf[Ring])
      .slice(0, 2)
      .foreach((item: Item) => {
        tab.ctrlClickItem(item)
      })
    // Amulet
    Stash.activateAmuletTab(Mode.READ_POSITIONS_AND_ITEMS)
    tab = Stash.currentTab().get
    itemOption = tab.items.find(_.isInstanceOf[Amulet])
    if(itemOption.isDefined) tab.ctrlClickItem(itemOption.get)
    // Belt
    Stash.activateBeltTab(Mode.READ_POSITIONS_AND_ITEMS)
    tab = Stash.currentTab().get
    itemOption = tab.items.find(_.isInstanceOf[Belt])
    if(itemOption.isDefined) tab.ctrlClickItem(itemOption.get)

    markContainersOutOfDate()
  }

  private def prepareInventoryAction(): Unit = {
    Stash.resetTab()
    Inventory.updateOccupancyAndItems()
  }

  private def dumpCurrencies(): Unit = {
    Inventory.basicCurrencies.foreach((currency: Currency) => {
      Inventory.ctrlClickItem(currency)
    })
  }

  private def dumpEssences(): Unit = {
    Inventory.essences.foreach((essence) => {
      Inventory.ctrlClickItem(essence)
    })
  }

  private def dumpDivinationCards(): Unit = {
    Inventory.divinationCards.foreach((divinationCard: DivinationCard) => {
      Inventory.ctrlClickItem(divinationCard)
    })
  }

  private def dumpChaosEquipment(): Unit = {
    val chaosEquipment: Seq[Equipment] = Inventory.chaosEquipment
    val helmets: Seq[Helmet] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Helmet]).map((equipment) => equipment.asInstanceOf[Helmet])
    val boots: Seq[Boot] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Boot]).map((equipment) => equipment.asInstanceOf[Boot])
    val gloves: Seq[Glove] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Glove]).map((equipment) => equipment.asInstanceOf[Glove])
    val bodys: Seq[BodyArmour] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[BodyArmour]).map((equipment) => equipment.asInstanceOf[BodyArmour])
    val weapons: Seq[Weapon] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Weapon]).map((equipment) => equipment.asInstanceOf[Weapon])
    val rings: Seq[Ring] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Ring]).map((equipment) => equipment.asInstanceOf[Ring])
    val amulets: Seq[Amulet] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Amulet]).map((equipment) => equipment.asInstanceOf[Amulet])
    val belts: Seq[Belt] = chaosEquipment.filter((equipment) => equipment.isInstanceOf[Belt]).map((equipment) => equipment.asInstanceOf[Belt])

    if (helmets.nonEmpty) {
      println("Dumping Helmets")
      Stash.activateHelmetTab(Mode.READ_POSITIONS)
      helmets.foreach((helmet: Helmet) => Inventory.sendItemToAllocation(helmet, Stash.helmetAllocation))
    }

    if (boots.nonEmpty) {
      println("Dumping Boots")
      Stash.activateBootTab(Mode.READ_POSITIONS)
      boots.foreach((boot: Boot) => Inventory.sendItemToAllocation(boot, Stash.bootAllocation))
    }

    if (gloves.nonEmpty) {
      println("Dumping Gloves")
      Stash.activateGloveTab(Mode.READ_POSITIONS)
      gloves.foreach((glove: Glove) => Inventory.sendItemToAllocation(glove, Stash.gloveAllocation))
    }

    if (bodys.nonEmpty) {
      println("Dumping Body Armours")
      Stash.activateBodyTab(Mode.READ_POSITIONS)
      bodys.foreach((bodyArmour: BodyArmour) => Inventory.sendItemToAllocation(bodyArmour, Stash.bodyAllocation))
    }

    if (weapons.nonEmpty) {
      println("Dumping Weapons")
      Stash.activateWeaponTab(Mode.READ_POSITIONS)
      weapons.foreach((weapon: Weapon) => Inventory.sendItemToAllocation(weapon, Stash.weaponAllocation))
    }

    if (rings.nonEmpty) {
      println("Dumping Rings")
      Stash.activateRingTab(Mode.READ_POSITIONS)
      rings.foreach((ring: Ring) => Inventory.sendItemToAllocation(ring, Stash.ringAllocation))
    }

    if (amulets.nonEmpty) {
      println("Dumping Amulets")
      Stash.activateAmuletTab(Mode.READ_POSITIONS)
      amulets.foreach((amulet: Amulet) => Inventory.sendItemToAllocation(amulet, Stash.amuletAllocation))
    }

    if (belts.nonEmpty) {
      println("Dumping Belts")
      Stash.activateBeltTab(Mode.READ_POSITIONS)
      belts.foreach((belt: Belt) => Inventory.sendItemToAllocation(belt, Stash.beltAllocation))
    }
  }

  private def markContainersOutOfDate(): Unit = {
    Inventory.upToDate = false
    Stash.markTabsOutOfDate()
  }
}
