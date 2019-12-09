import com.typesafe.scalalogging.Logger
import config.Config
import containers.{Clicker, Mode, Position, ScreenItem}
import maps.MapCurrency._
import maps.{MapAction, MapCurrency, Solver, Validator}
import parser.item.MapItem
import parser.item.currency.Currency

object MapRoller {
  val log = Logger("MapRoller")
  private var activeTab: Tab = _

  def run(): Unit = {
    InventoryManager.userReleaseSleep()
    Stash.resetTab()
    Inventory.updateOccupancyAndItems()

    if (!Stash.activateTab(Config.RUN_ALLOCATION, Mode.READ_POSITIONS)) return

    if (Stash.currentTab().isEmpty) {
      log.warn("Run Tab not found")
      return
    }
    activeTab = Stash.currentTab().get
    var shouldRun = true
    activeTab.positions()
      .filter((position: Position) => position.occupied)
      .foreach((position: Position) => {
        if (!shouldRun) return
        try {
          rollMap(position)
        } catch {
          case e: Exception => {
            log.warn(s"Stopped due to error: ${e.getMessage}")
            shouldRun = false
          }
        }
      })
  }

  private def rollMap(position: Position): Unit = {
    while (true) {
      val map = readMap(position)
      val issues = Validator.getIssues(map)
      if (issues.isEmpty) return

      val actions = Solver.getSolution(map, issues)
      actions.foreach((action: MapAction) => performAction(position, action))
    }
  }

  private def readMap(position: Position): MapItem = {
    val item = activeTab.readAndRecordItem(position)
    item.data.asInstanceOf[MapItem]
  }

  private def performAction(position: Position, action: MapAction): Unit = {
    log.debug(s"Performing Action: $action")

    action match {
      case MapAction.IDENTIFY => useCurrency(position, SCROLL_OF_WISDOM)
      case MapAction.SCOUR => useCurrency(position, ORB_OF_SCOURING)
      case MapAction.CHISEL => useCurrency(position, CARTOGRAPHERS_CHISEL)
      case MapAction.TRANSMUTE => useCurrency(position, ORB_OF_TRANSMUTATION)
      case MapAction.ALCHEMY => useCurrency(position, ORB_OF_ALCHEMY)
      case MapAction.CHAOS => {
        if (Inventory.hasScouringAndAlchemy) {
          useCurrency(position, ORB_OF_SCOURING)
          useCurrency(position, ORB_OF_ALCHEMY)
        } else {
          useCurrency(position, CHAOS_ORB)
        }
      }
      case MapAction.ALTERATION => useCurrency(position, ORB_OF_ALTERATION)
      case MapAction.AUGMENT => useCurrency(position, ORB_OF_AUGMENTATION)
      case other => log.error(s"unhandled action: $other")
    }
  }

  private def useCurrency(position: Position, mapCurrency: MapCurrency): Unit = {
    // find currency
    val currencyName = mapCurrency.getName
    val currencyOption = Inventory.findCurrency(currencyName)
    if (currencyOption.isEmpty) {
      throw new IllegalStateException(s"could not find $currencyName in inventory")
    }
    val currency = currencyOption.get

    // use currency
    val currencyPixelPosition = Inventory.getPixels(currency.position.get)
    Clicker.rightClick(currencyPixelPosition)
    // record the currency as used
    decrementCurrency(currency)

    Thread sleep 300

    // click item
    val itemPixelPosition = activeTab.getPixels(position)
    Clicker.click(itemPixelPosition)
  }

  private def decrementCurrency(currencyItem: ScreenItem): Unit = {
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
}
