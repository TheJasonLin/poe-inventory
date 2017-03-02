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

    dumpFullSetEquipment(false)
    dumpFullSetEquipment(true)

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
      Stash.activateTab(TabContents.CURRENCY, Mode.NO_READ, false)
      Inventory.ctrlClickItem(currency)
    })
  }

  private def dumpEssences(): Unit = {
    Inventory.essences.foreach((essence) => {
      Stash.activateTab(TabContents.ESSENCE, Mode.NO_READ, false)
      Inventory.ctrlClickItem(essence)
    })
  }

  private def dumpDivinationCards(): Unit = {
    Inventory.divinationCards.foreach((divinationCard: DivinationCard) => {
      Stash.activateTab(TabContents.DIVINATION, Mode.NO_READ, false)
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

  private def dumpFullSetEquipment(level75: Boolean): Unit = {
    val chaosEquipment: Seq[Equipment] = Inventory.fullSetEquipment(level75)
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
      Stash.activateTab(TabContents.HELMET, Mode.READ_POSITIONS, level75)
      helmets.foreach((helmet: Helmet) => Inventory.sendItemToAllocation(helmet, Stash.helmetAllocation))
    }

    if (boots.nonEmpty) {
      println("Dumping Boots")
      Stash.activateTab(TabContents.BOOT, Mode.READ_POSITIONS, level75)
      boots.foreach((boot: Boot) => Inventory.sendItemToAllocation(boot, Stash.bootAllocation))
    }

    if (gloves.nonEmpty) {
      println("Dumping Gloves")
      Stash.activateTab(TabContents.GLOVE, Mode.READ_POSITIONS, level75)
      gloves.foreach((glove: Glove) => Inventory.sendItemToAllocation(glove, Stash.gloveAllocation))
    }

    if (bodys.nonEmpty) {
      println("Dumping Body Armours")
      Stash.activateTab(TabContents.BODY, Mode.READ_POSITIONS, level75)
      bodys.foreach((bodyArmour: BodyArmour) => Inventory.sendItemToAllocation(bodyArmour, Stash.bodyAllocation))
    }

    if (weapons.nonEmpty) {
      println("Dumping Weapons")
      Stash.activateTab(TabContents.WEAPON, Mode.READ_POSITIONS, level75)
      weapons.foreach((weapon: Weapon) => Inventory.sendItemToAllocation(weapon, Stash.weaponAllocation))
    }

    if (rings.nonEmpty) {
      println("Dumping Rings")
      Stash.activateTab(TabContents.RING, Mode.READ_POSITIONS, level75)
      rings.foreach((ring: Ring) => Inventory.sendItemToAllocation(ring, Stash.ringAllocation))
    }

    if (amulets.nonEmpty) {
      println("Dumping Amulets")
      Stash.activateTab(TabContents.AMULET, Mode.READ_POSITIONS, level75)
      amulets.foreach((amulet: Amulet) => Inventory.sendItemToAllocation(amulet, Stash.amuletAllocation))
    }

    if (belts.nonEmpty) {
      println("Dumping Belts")
      Stash.activateTab(TabContents.BELT, Mode.READ_POSITIONS, level75)
      belts.foreach((belt: Belt) => Inventory.sendItemToAllocation(belt, Stash.beltAllocation))
    }
  }

  private def markContainersOutOfDate(): Unit = {
    Inventory.upToDate = false
    Stash.markTabsOutOfDate()
  }

  def extractFullSet(level75: Boolean): Unit = {
    Stash.resetTab()
    extractItemFromsTab(TabContents.HELMET, 1, level75)
    extractItemFromsTab(TabContents.BOOT, 1, level75)
    extractItemFromsTab(TabContents.GLOVE, 1, level75)
    extractItemFromsTab(TabContents.BODY, 1, level75)
    extractItemFromsTab(TabContents.WEAPON, 2, level75)
    extractItemFromsTab(TabContents.RING, 2, level75)
    extractItemFromsTab(TabContents.AMULET, 1, level75)
    extractItemFromsTab(TabContents.BELT, 1, level75)

    markContainersOutOfDate()
  }

  def extractItemFromsTab(tabType: TabContents, count: Int, level75: Boolean): Unit = {
    val allocation: Allocation = Stash.getAllocation(tabType, level75)
    Stash.activateTab(tabType, Mode.READ_POSITIONS, use75Allocations = level75)
    val tab = Stash.currentTab().get
    var extractedCount = 0
    tab.positionsInAllocation(allocation)
      .filter(_.occupied == true)
      .foreach((position: Position) => {
        // make sure it's still occupied
        if(extractedCount < count && position.occupied) {
          val itemOption = tab.readAndRecordItem(position)
          if (itemOption.isDefined) {
            tab.ctrlClickItem(itemOption.get)
            extractedCount += 1
          }
        }
      })
  }
}
