import com.melloware.jintellitype
import com.melloware.jintellitype.JIntellitype
import com.typesafe.scalalogging.Logger
import config.Config
import containers.Mode
import screen.Screen

object Main extends jintellitype.HotkeyListener {
  val log = Logger("Main")
  val EVENT_QUIT: Int = 1
  val EVENT_STORE_INVENTORY: Int = 2
  val EVENT_GET_CHAOS_SET: Int = 3
  val EVENT_CALIBRATE: Int = 4
  val EVENT_EMPTY_INVENTORY: Int = 5
  val EVENT_GET_REGAL_SET: Int = 6
  val EVENT_ROLL_MAPS: Int = 7
  val EVENT_ID_AND_DUMP: Int = 8
  val EVENT_ACCEPT_TRADE: Int = 9

  override def onHotKey(identifier: Int): Unit = {
    identifier match {
      case EVENT_QUIT => quit()
      case EVENT_STORE_INVENTORY => InventoryManager.storeInventory()
      case EVENT_EMPTY_INVENTORY => InventoryManager.emptyInventory()
      case EVENT_ROLL_MAPS => InventoryManager.rollMaps()
      case EVENT_ID_AND_DUMP => InventoryManager.idForQuickSell()
      case EVENT_CALIBRATE => calibrate()
      case EVENT_ACCEPT_TRADE => InventoryManager.acceptTrade()
    }
  }

  def quit(): Unit = {
    JIntellitype.getInstance().cleanUp()
    System.exit(0)
  }

  def main(args: Array[String]): Unit = {
    registerHotkeys()
    println("Script Loaded Successfully")
  }

  def registerHotkeys(): Unit = {
    val ctrlShift = jintellitype.JIntellitypeConstants.MOD_CONTROL + jintellitype.JIntellitypeConstants.MOD_SHIFT
    JIntellitype.getInstance().addHotKeyListener(this)
    JIntellitype.getInstance().registerHotKey(EVENT_QUIT, ctrlShift, 'Q')

    JIntellitype.getInstance().registerHotKey(EVENT_CALIBRATE, ctrlShift, 'T')
    JIntellitype.getInstance().registerHotKey(EVENT_ROLL_MAPS, ctrlShift, 'M')

    JIntellitype.getInstance().registerHotKey(EVENT_STORE_INVENTORY, ctrlShift, 'B')
    JIntellitype.getInstance().registerHotKey(EVENT_EMPTY_INVENTORY, ctrlShift, 'V')
    JIntellitype.getInstance().registerHotKey(EVENT_ID_AND_DUMP, ctrlShift, 'L')
    JIntellitype.getInstance().registerHotKey(EVENT_ACCEPT_TRADE, ctrlShift, 'U')
  }

  def calibrate(): Unit = {
    Stash.resetTab()
    // Normal Tab
    if(Config.CALIBRATION_NORMAL_TAB_INDEX.isDefined) {
      Stash.activateTab(Config.CALIBRATION_NORMAL_TAB_INDEX.get, Mode.NO_READ)
      Stash.currentTab().get.drawBoxes()
      Screen.save("normal-tab-calibration.png")
    }
    // Quad Tab
    if(Config.CALIBRATION_QUAD_TAB_INDEX.isDefined) {
      Stash.activateTab(Config.CALIBRATION_QUAD_TAB_INDEX.get, Mode.NO_READ)
      Stash.currentTab().get.drawBoxes()
      Screen.save("quad-tab-calibration.png")
    }
    // Always calibrate Inventory
    Screen.update()
    Inventory.drawBoxes()
    Screen.save("inventory-calibration.png")
  }
}
