import items.currency.Currency
import items.equipment.Equipment
import items.equipment.accessory.{Amulet, Belt, Ring}
import items.equipment.armour.{BodyArmour, Boot, Glove, Helmet}
import items.equipment.weapon.Weapon
import items.map.MapItem
import items.{DivinationCard, Item}
import screen.Screen
import structures.Position

object InventoryManager {
  /**
    * Store everything into the Stash
    */
  def storeInventory(): Unit = {
    prepareInventoryAction()

    dumpCurrencies()
    dumpEssences()
    dumpDivinationCards()

    dumpMaps()

    dumpChaosEquipment()

    markContainersOutOfDate()
  }

  /**
    * Ctrl Click Everything
    */
  def emptyInventory(): Unit = {
    // since we aren't reseting the stash, which normally updates the screen for us, we need to manually update the screen
    Screen.update()
    Inventory.updateOccupancy()
    Inventory.positions()
      .filter((position) => {
        position.occupied
      })
      .foreach((position) => {
        Inventory.ctrlClickPosition(position)
      })
  }

  private def prepareInventoryAction(): Unit = {
    Stash.resetTab()
    Inventory.updateOccupancyAndItems()
  }

  private def dumpCurrencies(): Unit = {
    Inventory.basicCurrencies.foreach((currency: Currency) => {
      Stash.activateTab(TabContents.CURRENCY, Mode.NO_READ)
      Inventory.ctrlClickItem(currency)
    })
  }

  private def dumpEssences(): Unit = {
    Inventory.essences.foreach((essence) => {
      Stash.activateTab(TabContents.ESSENCE, Mode.NO_READ)
      Inventory.ctrlClickItem(essence)
    })
  }

  private def dumpDivinationCards(): Unit = {
    Inventory.divinationCards.foreach((divinationCard: DivinationCard) => {
      Stash.activateTab(TabContents.DIVINATION, Mode.NO_READ)
      Inventory.ctrlClickItem(divinationCard)
    })
  }

  private def dumpMaps(): Unit = {
    Inventory.maps.foreach((map: MapItem) => {
      val allocationOption: Option[Allocation] = Stash.findMapAllocation(map)
      if(allocationOption.isEmpty) return
      val allocation = allocationOption.get
      Stash.activateTab(allocation, Mode.READ_POSITIONS)
      Inventory.sendItemToAllocation(map, allocation)
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
      Stash.activateTab(TabContents.HELMET, Mode.READ_POSITIONS)
      helmets.foreach((helmet: Helmet) => Inventory.sendItemToAllocation(helmet, Stash.helmetAllocation))
    }

    if (boots.nonEmpty) {
      println("Dumping Boots")
      Stash.activateTab(TabContents.BOOT, Mode.READ_POSITIONS)
      boots.foreach((boot: Boot) => Inventory.sendItemToAllocation(boot, Stash.bootAllocation))
    }

    if (gloves.nonEmpty) {
      println("Dumping Gloves")
      Stash.activateTab(TabContents.GLOVE, Mode.READ_POSITIONS)
      gloves.foreach((glove: Glove) => Inventory.sendItemToAllocation(glove, Stash.gloveAllocation))
    }

    if (bodys.nonEmpty) {
      println("Dumping Body Armours")
      Stash.activateTab(TabContents.BODY, Mode.READ_POSITIONS)
      bodys.foreach((bodyArmour: BodyArmour) => Inventory.sendItemToAllocation(bodyArmour, Stash.bodyAllocation))
    }

    if (weapons.nonEmpty) {
      println("Dumping Weapons")
      Stash.activateTab(TabContents.WEAPON, Mode.READ_POSITIONS)
      weapons.foreach((weapon: Weapon) => Inventory.sendItemToAllocation(weapon, Stash.weaponAllocation))
    }

    if (rings.nonEmpty) {
      println("Dumping Rings")
      Stash.activateTab(TabContents.RING, Mode.READ_POSITIONS)
      rings.foreach((ring: Ring) => Inventory.sendItemToAllocation(ring, Stash.ringAllocation))
    }

    if (amulets.nonEmpty) {
      println("Dumping Amulets")
      Stash.activateTab(TabContents.AMULET, Mode.READ_POSITIONS)
      amulets.foreach((amulet: Amulet) => Inventory.sendItemToAllocation(amulet, Stash.amuletAllocation))
    }

    if (belts.nonEmpty) {
      println("Dumping Belts")
      Stash.activateTab(TabContents.BELT, Mode.READ_POSITIONS)
      belts.foreach((belt: Belt) => Inventory.sendItemToAllocation(belt, Stash.beltAllocation))
    }
  }

  private def markContainersOutOfDate(): Unit = {
    Inventory.upToDate = false
    Stash.markTabsOutOfDate()
  }

  def extractChaosSet(): Unit = {
    Stash.resetTab()
    extractItemFromsTab(TabContents.HELMET, 1)
    extractItemFromsTab(TabContents.BOOT, 1)
    extractItemFromsTab(TabContents.GLOVE, 1)
    extractItemFromsTab(TabContents.BODY, 1)
    extractItemFromsTab(TabContents.WEAPON, 2)
    extractItemFromsTab(TabContents.RING, 2)
    extractItemFromsTab(TabContents.AMULET, 1)
    extractItemFromsTab(TabContents.BELT, 1)

    markContainersOutOfDate()
  }

  def extractItemFromsTab(tabType: TabContents, count: Int): Unit = {
    val allocation: Allocation = Stash.generalAllocations(tabType)
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
