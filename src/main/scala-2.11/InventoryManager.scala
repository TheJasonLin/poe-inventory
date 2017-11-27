import com.poe.parser.item.currency.Currency
import com.poe.parser.item.equipment.accessory.{Amulet, Belt, Ring}
import com.poe.parser.item.equipment.armour.{BodyArmour, Boot, Glove, Helmet}
import com.poe.parser.item.equipment.weapon.Weapon
import screen.Screen
import structures.{Position, ScreenItem}

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
    dumpLeaguestones()
    dumpTalismans()

    dumpQualityFlasks()
    dumpQualityGems()


    if(Config.SEPARATE_REGAL) {
      dumpFullSetEquipment(chaos = true, regal = false)
      dumpFullSetEquipment(chaos = false, regal = true)
    } else {
      dumpFullSetEquipment(chaos = true, regal = true)
    }

    dumpMisc()

    markContainersOutOfDate()
  }

  /**
    * Ctrl Click Everything
    */
  def emptyInventory(): Unit = {
    // delay to let the user let go of the hotkey
    Thread sleep 200
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
    val currencies = Inventory.basicCurrencies
    if(currencies.isEmpty) return
    currencies.foreach((item: ScreenItem) => {
      Stash.activateTab(TabContents.CURRENCY, Mode.NO_READ, false)
      Inventory.ctrlClickItem(item)
    })
  }

  private def dumpEssences(): Unit = {
    val essences = Inventory.essences
    if(essences.isEmpty) return
    essences.foreach((item: ScreenItem) => {
      Stash.activateTab(TabContents.ESSENCE, Mode.NO_READ, false)
      Inventory.ctrlClickItem(item)
    })
  }

  private def dumpDivinationCards(): Unit = {
    val divinationCards = Inventory.divinationCards
    if(divinationCards.isEmpty) return
    divinationCards.foreach((item: ScreenItem) => {
      Stash.activateTab(TabContents.DIVINATION, Mode.NO_READ, false)
      Inventory.ctrlClickItem(item)
    })
  }

  private def dumpMaps(): Unit = {
    val maps = Inventory.maps
    if(maps.isEmpty) return
    maps.foreach((item: ScreenItem) => {
      val allocationOption: Option[Allocation] = Stash.findMapAllocation(item)
      if(allocationOption.isEmpty) return
      val allocation = allocationOption.get
      Stash.activateTab(allocation, Mode.READ_POSITIONS)
      Inventory.sendItemToAllocation(item, allocation)
    })
  }

  private def dumpLeaguestones(): Unit = {
    val leaguestones = Inventory.leaguestones
    if(leaguestones.isEmpty) return
    leaguestones.foreach((item: ScreenItem) => {
      val allocationOption: Option[Allocation] = Stash.findLeaguestoneAllocation(item)
      if(allocationOption.isEmpty) return
      val allocation = allocationOption.get
      Stash.activateTab(allocation, Mode.READ_POSITIONS)
      Inventory.sendItemToAllocation(item, allocation)
    })
  }

  private def dumpTalismans(): Unit = {
    val talismans = Inventory.talismans
    if(talismans.isEmpty) return
    talismans.foreach((item: ScreenItem) => {
      val allocationOption: Option[Allocation] = Stash.findTalismanAllocation(item)
      if(allocationOption.isEmpty) return
      val allocation = allocationOption.get
      Stash.activateTab(allocation, Mode.READ_POSITIONS)
      Inventory.sendItemToAllocation(item, allocation)
    })
  }

  private def dumpQualityFlasks(): Unit = {
    val qualityFlasks = Inventory.qualityFlasks
    if(qualityFlasks.isEmpty) return
    val allocation: Allocation = Stash.qualityFlaskAllocations
    Stash.activateTab(allocation, Mode.READ_POSITIONS)
    qualityFlasks.foreach((item: ScreenItem) => {
      Inventory.sendItemToAllocation(item, Stash.qualityFlaskAllocations)
    })
  }

  private def dumpQualityGems(): Unit = {
    val qualityGems = Inventory.qualityGems
    if(qualityGems.isEmpty) return
    val allocation: Allocation = Stash.qualityGemAllocations
    Stash.activateTab(allocation, Mode.READ_POSITIONS)
    qualityGems.foreach((item: ScreenItem) => {
      Inventory.sendItemToAllocation(item, Stash.qualityGemAllocations)
    })
  }

  private def dumpMisc(): Unit = {
    val miscItems = Inventory.miscItems
    if(miscItems.isEmpty) return
    miscItems.foreach((item: ScreenItem) => {
      val allocation: Allocation = Stash.findMiscAllocation(item)
      Stash.activateTab(allocation, Mode.READ_POSITIONS)
      Inventory.sendItemToAllocation(item, allocation)
    })
  }

  private def dumpFullSetEquipment(chaos: Boolean, regal: Boolean): Unit = {
    val useRegalTab: Boolean = regal && !chaos

    val chaosEquipment: Seq[ScreenItem] = Inventory.fullSetEquipment(chaos, regal)
    val helmets: Seq[ScreenItem] = chaosEquipment.filter((item) => item.data.isInstanceOf[Helmet])
    val boots: Seq[ScreenItem] = chaosEquipment.filter((item) => item.data.isInstanceOf[Boot])
    val gloves: Seq[ScreenItem] = chaosEquipment.filter((item) => item.data.isInstanceOf[Glove])
    val bodyArmours: Seq[ScreenItem] = chaosEquipment.filter((item) => item.data.isInstanceOf[BodyArmour])
    val weapons: Seq[ScreenItem] = chaosEquipment.filter((item) => item.data.isInstanceOf[Weapon])
    val rings: Seq[ScreenItem] = chaosEquipment.filter((item) => item.data.isInstanceOf[Ring])
    val amulets: Seq[ScreenItem] = chaosEquipment.filter((item) => item.data.isInstanceOf[Amulet])
    val belts: Seq[ScreenItem] = chaosEquipment.filter((item) => item.data.isInstanceOf[Belt])

    if (helmets.nonEmpty) {
      println("Dumping Helmets")
      Stash.activateTab(TabContents.HELMET, Mode.READ_POSITIONS, useRegalTab)
      helmets.foreach((helmet: ScreenItem) => Inventory.sendItemToAllocation(helmet, Stash.helmetAllocation))
    }

    if (boots.nonEmpty) {
      println("Dumping Boots")
      Stash.activateTab(TabContents.BOOT, Mode.READ_POSITIONS, useRegalTab)
      boots.foreach((boot: ScreenItem) => Inventory.sendItemToAllocation(boot, Stash.bootAllocation))
    }

    if (gloves.nonEmpty) {
      println("Dumping Gloves")
      Stash.activateTab(TabContents.GLOVE, Mode.READ_POSITIONS, useRegalTab)
      gloves.foreach((glove: ScreenItem) => Inventory.sendItemToAllocation(glove, Stash.gloveAllocation))
    }

    if (bodyArmours.nonEmpty) {
      println("Dumping Body Armours")
      Stash.activateTab(TabContents.BODY, Mode.READ_POSITIONS, useRegalTab)
      bodyArmours.foreach((bodyArmour: ScreenItem) => Inventory.sendItemToAllocation(bodyArmour, Stash.bodyAllocation))
    }

    if (weapons.nonEmpty) {
      println("Dumping Weapons")
      Stash.activateTab(TabContents.WEAPON, Mode.READ_POSITIONS, useRegalTab)
      weapons.foreach((weapon: ScreenItem) => Inventory.sendItemToAllocation(weapon, Stash.weaponAllocation))
    }

    if (rings.nonEmpty) {
      println("Dumping Rings")
      Stash.activateTab(TabContents.RING, Mode.READ_POSITIONS, useRegalTab)
      rings.foreach((ring: ScreenItem) => Inventory.sendItemToAllocation(ring, Stash.ringAllocation))
    }

    if (amulets.nonEmpty) {
      println("Dumping Amulets")
      Stash.activateTab(TabContents.AMULET, Mode.READ_POSITIONS, useRegalTab)
      amulets.foreach((amulet: ScreenItem) => Inventory.sendItemToAllocation(amulet, Stash.amuletAllocation))
    }

    if (belts.nonEmpty) {
      println("Dumping Belts")
      Stash.activateTab(TabContents.BELT, Mode.READ_POSITIONS, useRegalTab)
      belts.foreach((belt: ScreenItem) => Inventory.sendItemToAllocation(belt, Stash.beltAllocation))
    }
  }

  private def markContainersOutOfDate(): Unit = {
    Inventory.upToDate = false
    Stash.markTabsOutOfDate()
  }

  def extractFullSet(level75: Boolean): Unit = {
    Stash.resetTab()
    extractItemsFromTab(TabContents.HELMET, 1, level75)
    extractItemsFromTab(TabContents.BOOT, 1, level75)
    extractItemsFromTab(TabContents.GLOVE, 1, level75)
    extractItemsFromTab(TabContents.BODY, 1, level75)
    extractItemsFromTab(TabContents.WEAPON, 2, level75)
    extractItemsFromTab(TabContents.RING, 2, level75)
    extractItemsFromTab(TabContents.AMULET, 1, level75)
    extractItemsFromTab(TabContents.BELT, 1, level75)

    markContainersOutOfDate()
  }

  def extractItemsFromTab(tabType: TabContents, count: Int, level75: Boolean): Unit = {
    val allocation: Allocation = Stash.getAllocation(tabType, level75)
    Stash.activateTab(tabType, Mode.READ_POSITIONS, use75Allocations = level75)
    val tab = Stash.currentTab().get
    var extractedCount = 0
    tab.positionsInAllocation(allocation)
      .filter(_.occupied == true)
      .foreach((position: Position) => {
        // make sure it's still occupied
        if(extractedCount < count && position.occupied) {
          val item = tab.readAndRecordItem(position)
          tab.ctrlClickItem(item)
          extractedCount += 1
        }
      })
  }
}
