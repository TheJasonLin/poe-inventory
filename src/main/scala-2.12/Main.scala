import com.melloware.jintellitype
import com.melloware.jintellitype.JIntellitype
import screen.Screen

object Main extends jintellitype.HotkeyListener {
  val EVENT_QUIT: Int = 1
  val EVENT_STORE_INVENTORY: Int = 2
  val EVENT_GET_CHAOS_SET: Int = 3
  val EVENT_CALIBRATE: Int = 4
  val EVENT_EMPTY_INVENTORY: Int = 5
  val EVENT_GET_REGAL_SET: Int = 6

  override def onHotKey(identifier: Int): Unit = {
    identifier match {
      case EVENT_QUIT => quit()
      case EVENT_STORE_INVENTORY => InventoryManager.storeInventory()
      case EVENT_GET_CHAOS_SET => InventoryManager.extractFullSet(false)
      case EVENT_GET_REGAL_SET => InventoryManager.extractFullSet(true)
      case EVENT_EMPTY_INVENTORY => InventoryManager.emptyInventory()
      case EVENT_CALIBRATE => calibrate()
    }
  }

  def quit(): Unit = {
    JIntellitype.getInstance().cleanUp()
    System.exit(0)
  }

  def main(args: Array[String]): Unit = {
    registerHotkeys()
  }

  def registerHotkeys(): Unit = {
    val ctrlShift = jintellitype.JIntellitypeConstants.MOD_CONTROL + jintellitype.JIntellitypeConstants.MOD_SHIFT
    JIntellitype.getInstance().addHotKeyListener(this)
    JIntellitype.getInstance().registerHotKey(EVENT_QUIT, ctrlShift, 'Q')
    JIntellitype.getInstance().registerHotKey(EVENT_STORE_INVENTORY, ctrlShift, 'B')
    JIntellitype.getInstance().registerHotKey(EVENT_GET_CHAOS_SET, ctrlShift, 'G')
    JIntellitype.getInstance().registerHotKey(EVENT_GET_REGAL_SET, ctrlShift, 'F')
    JIntellitype.getInstance().registerHotKey(EVENT_CALIBRATE, ctrlShift, 'T')
    JIntellitype.getInstance().registerHotKey(EVENT_EMPTY_INVENTORY, ctrlShift, 'V')
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
