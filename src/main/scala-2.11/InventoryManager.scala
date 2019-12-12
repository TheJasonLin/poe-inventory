import com.typesafe.scalalogging.Logger
import config._
import containers._
import parser.item.CraftableItem
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

    dumpRemainingInventory()

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
  def prepareInventoryAction(): Unit = {
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

  def rollMaps(): Unit = {
    MapRoller.run()
  }

  def userReleaseSleep(): Unit = {
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

  private def dumpRemainingInventory(): Unit = {
    if (Inventory.items.isEmpty) return
    if (!Stash.activateTab(Config.DUMP_ALLOCATION, Mode.NO_READ)) return
    Inventory.items.foreach((item: ScreenItem) => {
      Inventory.ctrlClickItem(item)
    })
  }

  def acceptTrade(): Unit = {
    val widthSegments = Config.TRADE_WINDOW_WIDTH - 1
    val heightSegments = Config.TRADE_WINDOW_HEIGHT - 1
    val segmentLength = (Config.TRADE_WINDOW_COORDS.bottomRight.x - Config.TRADE_WINDOW_COORDS.topLeft.x) / widthSegments
    val start = Config.TRADE_WINDOW_COORDS.topLeft

    for (xIndex <- 0 to widthSegments; yIndex <- 0 to heightSegments) {
      val x = start.x + segmentLength*xIndex
      val y = start.y + segmentLength*yIndex
      Clicker.hover(new PixelPosition(x, y))
      Thread sleep 20
    }

    Clicker.click(Config.TRADE_WINDOW_ACCEPT)
  }
}
