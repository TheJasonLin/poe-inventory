import com.typesafe.scalalogging.Logger
import config._
import constants.Rarity
import containers._
import maps.{MapIssue, MapRequirements}
import parser.item.currency.Currency
import parser.item.{CraftableItem, MapItem, Mod}
import screen.{PixelPosition, Screen}

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
    dumpDelve()
    dumpMiscItems()

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
    if (!Stash.activateTab(TabContents.CURRENCY, Mode.NO_READ)) return
    currencies.foreach((item: ScreenItem) => {
      Inventory.ctrlClickItem(item)
    })
  }

  private def dumpEssences(): Unit = {
    val essences = Inventory.essences
    if(essences.isEmpty) return
    if (!Stash.activateTab(TabContents.ESSENCE, Mode.NO_READ)) return
    essences.foreach((item: ScreenItem) => {
      Inventory.ctrlClickItem(item)
    })
  }

  private def dumpDivinationCards(): Unit = {
    val divinationCards = Inventory.divinationCards
    if(divinationCards.isEmpty) return
    if (!Stash.activateTab(TabContents.DIVINATION, Mode.NO_READ)) return
    divinationCards.foreach((item: ScreenItem) => {
      Inventory.ctrlClickItem(item)
    })
  }

  private def dumpMaps(): Unit = {
    dumpMapsSpecial()
  }

  private def dumpMapsSpecial(): Unit = {
    val maps = Inventory.maps
    if (maps.isEmpty) return

    if (!Stash.activateTab(TabContents.MAP, Mode.NO_READ)) return
    maps.foreach((item: ScreenItem) => {
      Inventory.ctrlClickItem(item)
    })
  }

  private def dumpFragments(): Unit = {
    val fragments = Inventory.fragments
    if (fragments.isEmpty) return
    if (!Stash.activateTab(TabContents.FRAGMENT, Mode.NO_READ)) return
    fragments.foreach((item: ScreenItem) => {
      Inventory.ctrlClickItem(item)
    })
  }

  private def dumpDelve(): Unit = {
    val delves = Inventory.delves
    if (delves.isEmpty) return
    if (!Stash.activateTab(TabContents.DELVE, Mode.NO_READ)) return
    delves.foreach((item: ScreenItem) => {
      Inventory.ctrlClickItem(item)
    })
  }

  private def dumpMiscItems(): Unit = {
    val miscItems = Inventory.miscDesirableItems
    if (miscItems.isEmpty) return
    if (!Stash.activateTab(TabContents.MISC, Mode.NO_READ)) return
    miscItems.foreach((item: ScreenItem) => {
      Inventory.ctrlClickItem(item)
    })
  }

  private def markContainersOutOfDate(): Unit = {
    Inventory.upToDate = false
    Stash.markTabsOutOfDate()
  }

  /**
    * Read Inventory to know what currency we have to work with
    * Begin rolling maps in the RUN_TAB
    */
  def rollMaps(): Unit = {
    userReleaseSleep()
    prepareInventoryAction()

    if (!Stash.activateTab(Config.RUN_ALLOCATION, Mode.READ_POSITIONS)) return

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

        try {
          rollMapInPosition(tab, position)
        } catch {
          case e: Exception => {
            log.warn(s"stopped rolling maps due to: ${e.getMessage}")
            keepRolling = false
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
      log.debug("ID'ing Map")
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
      log.debug("Scouring Map")
      log.debug(item.toString)
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
      log.debug("Alch'ing Map")
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
    val quality = map.quality.getOrElse(0)
    if (quality < MapRequirements.minQuality) {
      log.debug(s"Quality: $quality")
      issues += MapIssue.QUALITY_LOW
    }

    val iiq = map.itemQuantity
    val iir = map.itemRarity
    val packSize = map.packSize

    if (iiq < MapRequirements.minItemQuantity) {
      log.debug(s"BadIIQ: $iiq")
      issues += MapIssue.QUALITY_LOW
    } else if (iir < MapRequirements.minItemRarity) {
      log.debug(s"BadIIR: $iir")
      issues += MapIssue.QUALITY_LOW
    } else if (packSize < MapRequirements.minPackSize) {
      log.debug(s"PackSize: $packSize")
      issues += MapIssue.QUALITY_LOW
    }

    // check mods
    val mods = map.explicits ++ map.implicits

    val foundBlacklistMod = findBlacklistMod(mods)
    if (foundBlacklistMod.isDefined) {
      issues += MapIssue.BAD_ATTRIBUTES
      log.debug("Bad Mod: " + foundBlacklistMod.get.text)
    }

    issues
  }

  private def findBlacklistMod(mods: Seq[Mod]): Option[Mod] = {
    val matchOption = mods.find((mod: Mod) => {
      MapRequirements.blacklistMods.contains(mod.text)
    })

    matchOption
  }

  private def userReleaseSleep(): Unit = {
    Thread sleep Config.USER_KEY_RELEASE_DELAY
  }

  def idForQuickSell(): Unit = {
    userReleaseSleep()
    prepareInventoryAction()
    idItems()
    dumpInventoryToQuickSellAllocation()
  }

  private def idItems(): Unit = {
    Inventory.items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[CraftableItem] && !item.data.asInstanceOf[CraftableItem].identified
    }).foreach((item: ScreenItem) => {
      useCurrencyFromTabOnItemInInventory(item, "Scroll of Wisdom")
    })
  }

  private def useCurrencyFromTabOnItemInInventory(item: ScreenItem, currency: String): Unit = {
    Stash.activateTab(TabContents.CURRENCY, Mode.NO_READ)
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

  private def dumpInventoryToQuickSellAllocation(): Unit = {
    if (!Stash.activateTab(Config.QUICK_SELL_ALLOCATION, Mode.NO_READ)) return
    Inventory.items.foreach((item: ScreenItem) => {
      Inventory.ctrlClickItem(item)
      Stash.currentTab().get.upToDate = false
    })
  }
}
