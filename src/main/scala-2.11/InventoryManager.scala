import com.poe.constants.Rarity
import com.poe.parser.ItemFactory
import com.poe.parser.item.currency.{BasicCurrency, Currency}
import com.poe.parser.item.equipment.Equipment
import com.poe.parser.item.equipment.accessory.{Amulet, Belt, Ring}
import com.poe.parser.item.equipment.armour.{BodyArmour, Boot, Glove, Helmet}
import com.poe.parser.item.equipment.weapon.Weapon
import com.poe.parser.item.{CraftableItem, Item, MapItem, Mod}
import com.typesafe.scalalogging.Logger
import config._
import screen.Screen
import structures._

object InventoryManager {
  val log = Logger("InventoryManager")
  /**
    * Store everything into the Stash
    */
  def storeInventory(): Unit = {
    userReleaseSleep()
    prepareInventoryAction()

    dumpCurrencies()
    dumpEssences()
    dumpDivinationCards()
    dumpMaps()
    dumpFragments()

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
    userReleaseSleep()
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

  /**
    * Switch to first tab and read inventory
    */
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
    dumpMapsSpecial()
  }

  private def dumpMapsSpecial(): Unit = {
    val maps = Inventory.maps
    if (maps.isEmpty) return
    maps.foreach((item: ScreenItem) => {
      Stash.activateTab(TabContents.MAP, Mode.NO_READ, false)
      Inventory.ctrlClickItem(item)
    })
  }

  // deprecated
  private def dumpMapsNonSpecial(): Unit = {
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

  private def dumpFragments(): Unit = {
    val fragments = Inventory.fragments
    if (fragments.isEmpty) return
    fragments.foreach((item: ScreenItem) => {
      Stash.activateTab(TabContents.FRAGMENT, Mode.NO_READ, false)
      Inventory.ctrlClickItem(item)
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
    userReleaseSleep()
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

  /**
    * Read Inventory to know what currency we have to work with
    * Begin rolling maps in the RUN_TAB
    */
  def rollMaps(): Unit = {
    userReleaseSleep()
    if (Config.SAFE_MODE) {
      Stash.resetTab()
    } else {
      prepareInventoryAction()
    }
    Stash.activateTab(Config.RUN_MAP_ALLOCATION, Mode.READ_POSITIONS)
    if (Stash.currentTab.isEmpty) {
      throw new IllegalStateException("RUN_TAB not defined")
    }
    val tab = Stash.currentTab().get
    var keepRolling = true
    tab.positions()
      // get occupied positions
      .filter((position: Position) => {
        position.occupied
      })
      .foreach((position: Position) => {
        if (!keepRolling) {
          return
        }

        if (Config.SAFE_MODE) {
          val item: ScreenItem = tab.readAndRecordItem(position)

          val issues: Set[MapIssue] = getIssues(item.data.asInstanceOf[MapItem])
          if (issues.nonEmpty) {
            keepRolling = false
            log.info("stopped rolling due to the following issues:")
            if (issues.contains(MapIssue.UNIDENTIFIED)) log.info("map is unidentified")
            if (issues.contains(MapIssue.BAD_ATTRIBUTES)) log.info("map has bad attributes")
            if (issues.contains(MapIssue.QUALITY_LOW)) log.info("map has low quality")
          }
        } else {
          try {
            rollMapInPosition(tab, position)
          } catch {
            case e: Exception => {
              log.warn(s"stopped rolling maps due to: ${e.getMessage}")
              keepRolling = false
            }
          }
        }
      })
  }

  private def rollMapInPosition(tab: Tab, position: Position): Unit = {
    var item = tab.readAndRecordItem(position)
    if (!item.data.isInstanceOf[MapItem]) {
      return
    }

    var shouldRoll = true
    while (shouldRoll) {
      val map: MapItem = item.data.asInstanceOf[MapItem]
      val issues = getIssues(map)
      if (issues.isEmpty) {
        shouldRoll = false
      } else {
        rerollMap(tab, item, issues)
        item = tab.readAndRecordItem(item.position.get)
      }
    }
  }

  // TODO make this smarter
  private def rerollMap(tab: Tab, item: ScreenItem, issues: Set[MapIssue]): Unit = {
    val map: MapItem = item.data.asInstanceOf[MapItem]

    if (issues.contains(MapIssue.UNIDENTIFIED)) {
      // just id and consider it rerolled
      useCurrencyFromInventoryOnItemInTab(item, tab, "Scroll of Wisdom")
      return
    }

    if (map.corrupted) {
      throw new IllegalArgumentException("encountered a corrupted map that isn't up to par")
    }

    // use alteration shortcut if magic and quality is fine
    if (MapRequirements.rollRarity == Rarity.MAGIC && map.rarity == Rarity.MAGIC && !issues.contains(MapIssue.QUALITY_LOW)) {
      useCurrencyFromInventoryOnItemInTab(item, tab, "Orb of Alteration")
      return
    }

    // scour
    if (map.rarity == Rarity.MAGIC || map.rarity == Rarity.RARE) {
      useCurrencyFromInventoryOnItemInTab(item, tab, "Orb of Scouring")
    }

    // quality if necessary
    if (issues.contains(MapIssue.QUALITY_LOW)) {
      val qualityDeficit = MapRequirements.minQuality - map.quality.getOrElse(0)
      var chiselCount = qualityDeficit / 5
      if (qualityDeficit % 5 > 0) {
        chiselCount += 1
      }
      for (_ <- 0 until chiselCount) {
        useCurrencyFromInventoryOnItemInTab(item, tab, "Cartographer's Chisel")
      }
    }
    // return to rarity
    val rarity = MapRequirements.rollRarity
    if (rarity == Rarity.NORMAL) {
      // do nothing
    } else if (rarity == Rarity.MAGIC) {
      // use transmute
      useCurrencyFromInventoryOnItemInTab(item, tab, "Orb of Transmutation")
    } else if (rarity == Rarity.RARE) {
      // use alch
      useCurrencyFromInventoryOnItemInTab(item, tab, "Orb of Alchemy")
    } else {
      throw new IllegalArgumentException(s"unrecognized roll rarity $rarity")
    }
  }

  private def useCurrencyFromInventoryOnItemInTab(item: ScreenItem, tab: Tab, currencyName: String): Unit = {
    // find currency
    val currencyOption = Inventory.findCurrency(currencyName)
    if (currencyOption.isEmpty) {
      throw new IllegalStateException(s"could not find $currencyName in inventory")
    }
    val currency = currencyOption.get

    // use currency
    val currencyPixelPosition = Inventory.getPixels(currency.position.get)
    Clicker.rightClick(currencyPixelPosition)
    // record the currency as used
    decrementCurrency(currency, tab)

    Thread sleep 300

    // click item
    val itemPixelPosition = tab.getPixels(item.position.get)
    Clicker.click(itemPixelPosition)
  }

  private def decrementCurrency(currencyItem: ScreenItem, tab: Tab): Unit = {
    val currency: Currency = currencyItem.data.asInstanceOf[Currency]
    if (currency.stackSize.isEmpty) {
      log.warn("currency found without stackSize")
    }
    val stackSize = currency.stackSize.get
    if (stackSize.size > 1) {
      currency.stackSize = Option(stackSize.copy(size = stackSize.size - 1))
    } else {
      Inventory.removeItem(currencyItem)
    }
  }

  // returns issues it finds with the map
  private def getIssues(map: MapItem): Set[MapIssue] = {
    var issues: Set[MapIssue] = Set()
    // check if unidentified
    if (!map.identified) {
      issues += MapIssue.UNIDENTIFIED
      return issues
    }

    // check thresholds
    if (map.quality.getOrElse(0) < MapRequirements.minQuality) {
      issues += MapIssue.QUALITY_LOW
    }

    val badIIQ = map.itemQuantity < MapRequirements.minItemQuantity
    val badIIR = map.itemRarity < MapRequirements.minItemRarity
    val badPackSize = map.packSize < MapRequirements.minPackSize
    if (badIIQ || badIIR || badPackSize) {
      issues += MapIssue.BAD_ATTRIBUTES
    }

    // check mods
    if (hasBlacklistMods(map.explicits, MapRequirements.blacklistMods)) {
      issues += MapIssue.BAD_ATTRIBUTES
    } else if (hasBlacklistMods(map.implicits, MapRequirements.blacklistMods)) {
      issues += MapIssue.BAD_ATTRIBUTES
    }

    issues
  }

  private def hasBlacklistMods(mods: Seq[Mod], blacklistMods: Seq[String]): Boolean = {
    val matchOption = mods.find((mod: Mod) => {
      blacklistMods.contains(mod.text)
    })
    matchOption.isDefined
  }

  def countCurrencyValues(): Unit = {
    userReleaseSleep()

    Stash.resetTab()
    Stash.activateTab(TabContents.CURRENCY, Mode.NO_READ, use75Allocations = false)

    var total: Double = 0
    // read the stack size for every currency
    for ((typeLine: String, position: PixelPosition) <- CurrencyTabConfig.CURRENCY_TAB_POSITIONS) {
      val clipboard: String = Clicker.getItemInfo(position)
      try {
        val item: Item = ItemFactory.create(clipboard)
        if (item.typeLine.equals(typeLine)) {
          val currency: BasicCurrency = item.asInstanceOf[BasicCurrency]
          val stackSize = currency.stackSize.get.size
          val currencyValue: Double = CurrencyValues.values(typeLine)
          val value: Double = currencyValue * stackSize
          log.info(s"$typeLine: $stackSize ($value)")
          total += value
        }
      } catch {
        case _: RuntimeException => log.warn(s"$typeLine: 0 (0)")
      }
    }

    log.info(s"Net Liquid Worth: $total")
  }

  def countMapValues(): Unit = {
    userReleaseSleep()

    Stash.resetTab()

    var total: Double = 0.0

    for (tier <- MapValues.MinMapTierWithValue to MapValues.MaxMapTier) {
      val allocation: Allocation = Config.MAP_ALLOCATION(tier)
      Stash.activateTab(allocation, Mode.READ_POSITIONS)
      val currentTabOption: Option[Tab] = Stash.currentTab()
      if (currentTabOption.isEmpty) throw new IllegalStateException("current tab not found")
      val currentTab: Tab = currentTabOption.get
      var tierTotal: Double = 0.0
      val positions = Stash.currentTab().get.positionsInAllocation(allocation)
      positions.filter((position: Position) => {
        position.occupied
      }).map((position) => {
        currentTab.readAndRecordItem(position)
      }).foreach((item) => {
        val pairOption: Option[(String, Double)] = MapValues.values.find((pair: (String, Double)) => {
          item.data.typeLine.contains(pair._1)
        })
        if (pairOption.isDefined) {
          val pair = pairOption.get
          tierTotal += pair._2
        }
      })
      log.info(s"Tier $tier: $tierTotal")
      total += tierTotal
    }
    log.info(s"Total: $total")
  }

  private def userReleaseSleep(): Unit = {
    Thread sleep Config.USER_KEY_RELEASE_DELAY
  }

  def idAndDump(): Unit = {
    userReleaseSleep()
    prepareInventoryAction()
    idItems()
    dumpInventoryToDumpAllocation()
  }

  private def idItems(): Unit = {
    Inventory.items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[CraftableItem] && !item.data.asInstanceOf[CraftableItem].identified
    }).foreach((item: ScreenItem) => {
      useCurrencyFromTabOnItemInInventory(item, "Scroll of Wisdom")
    })
  }

  private def useCurrencyFromTabOnItemInInventory(item: ScreenItem, currency: String): Unit = {
    Stash.activateTab(TabContents.CURRENCY, Mode.NO_READ, use75Allocations = false)
    val currencyPosition: PixelPosition = CurrencyTabConfig.CURRENCY_TAB_POSITIONS(currency)
    Clicker.rightClick(currencyPosition)
    Thread sleep 500
    val itemPositionOption: Option[Position] = item.position
    if (itemPositionOption.isEmpty) {
      log.warn("item position was empty when trying to use currency")
      return
    }
    val pixelPosition: PixelPosition = Inventory.getPixels(itemPositionOption.get)
    Clicker.click(pixelPosition)
  }

  private def dumpInventoryToDumpAllocation(): Unit = {
    Stash.activateTab(Config.DUMP_ALLOCATION, Mode.NO_READ)
    Inventory.items.foreach((item: ScreenItem) => {
      Inventory.ctrlClickItem(item)
      Stash.currentTab().get.upToDate = false
    })
  }
}
