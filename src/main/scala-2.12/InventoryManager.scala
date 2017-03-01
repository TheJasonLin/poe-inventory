import items.currency.Currency
import items.equipment.Equipment
import items.equipment.accessory.{Amulet, Belt, Ring}
import items.equipment.armour.{BodyArmour, Boot, Glove, Helmet}
import items.equipment.weapon.Weapon
import items.{DivinationCard, Item}
import structures.Position

object InventoryManager {
  def emptyInventory(): Unit = {
    prepareInventoryAction()

    Stash.activateTab(TabType.CURRENCY, Mode.NO_READ)
    dumpCurrencies()
    Stash.activateTab(TabType.ESSENCE, Mode.NO_READ)
    dumpEssences()
    Stash.activateTab(TabType.DIVINATION, Mode.NO_READ)
    dumpDivinationCards()

    dumpChaosEquipment()

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
      Stash.activateTab(TabType.HELMET, Mode.READ_POSITIONS)
      helmets.foreach((helmet: Helmet) => Inventory.sendItemToAllocation(helmet, Stash.helmetAllocation))
    }

    if (boots.nonEmpty) {
      println("Dumping Boots")
      Stash.activateTab(TabType.BOOT, Mode.READ_POSITIONS)
      boots.foreach((boot: Boot) => Inventory.sendItemToAllocation(boot, Stash.bootAllocation))
    }

    if (gloves.nonEmpty) {
      println("Dumping Gloves")
      Stash.activateTab(TabType.GLOVE, Mode.READ_POSITIONS)
      gloves.foreach((glove: Glove) => Inventory.sendItemToAllocation(glove, Stash.gloveAllocation))
    }

    if (bodys.nonEmpty) {
      println("Dumping Body Armours")
      Stash.activateTab(TabType.BODY, Mode.READ_POSITIONS)
      bodys.foreach((bodyArmour: BodyArmour) => Inventory.sendItemToAllocation(bodyArmour, Stash.bodyAllocation))
    }

    if (weapons.nonEmpty) {
      println("Dumping Weapons")
      Stash.activateTab(TabType.WEAPON, Mode.READ_POSITIONS)
      weapons.foreach((weapon: Weapon) => Inventory.sendItemToAllocation(weapon, Stash.weaponAllocation))
    }

    if (rings.nonEmpty) {
      println("Dumping Rings")
      Stash.activateTab(TabType.RING, Mode.READ_POSITIONS)
      rings.foreach((ring: Ring) => Inventory.sendItemToAllocation(ring, Stash.ringAllocation))
    }

    if (amulets.nonEmpty) {
      println("Dumping Amulets")
      Stash.activateTab(TabType.AMULET, Mode.READ_POSITIONS)
      amulets.foreach((amulet: Amulet) => Inventory.sendItemToAllocation(amulet, Stash.amuletAllocation))
    }

    if (belts.nonEmpty) {
      println("Dumping Belts")
      Stash.activateTab(TabType.BELT, Mode.READ_POSITIONS)
      belts.foreach((belt: Belt) => Inventory.sendItemToAllocation(belt, Stash.beltAllocation))
    }
  }

  private def markContainersOutOfDate(): Unit = {
    Inventory.upToDate = false
    Stash.markTabsOutOfDate()
  }

  def extractChaosSet(): Unit = {
    Stash.resetTab()
    extractItemFromsTab(TabType.HELMET, 1)
    extractItemFromsTab(TabType.BOOT, 1)
    extractItemFromsTab(TabType.GLOVE, 1)
    extractItemFromsTab(TabType.BODY, 1)
    extractItemFromsTab(TabType.WEAPON, 2)
    extractItemFromsTab(TabType.RING, 2)
    extractItemFromsTab(TabType.AMULET, 1)
    extractItemFromsTab(TabType.BELT, 1)

    markContainersOutOfDate()
  }

  def extractItemFromsTab(tabType: TabType, count: Int): Unit = {
    val allocation: Allocation = Stash.allocations(tabType)
    Stash.activateTab(tabType, Mode.READ_POSITIONS)
    val tab = Stash.currentTab().get
    tab.positionsInAllocation(allocation)
      .filter(_.occupied == true)
      .slice(0, count)
      .map((position: Position) => {
        tab.readAndRecordItem(position)
      })
      .foreach((itemOption: Option[Item]) => {
        if (itemOption.isDefined) {
          tab.ctrlClickItem(itemOption.get)
        }
      })
  }
}
