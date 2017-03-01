import com.melloware.jintellitype
import com.melloware.jintellitype.JIntellitype
import screen.Screen

object Main extends jintellitype.HotkeyListener {
  val EVENT_QUIT: Int = 1
  val EVENT_EMPTY_INVENTORY: Int = 2
  val EVENT_GET_CHAOS_SET: Int = 3
  val EVENT_CALIBRATE: Int = 4

  override def onHotKey(identifier: Int): Unit = {
    identifier match {
      case EVENT_QUIT => quit()
      case EVENT_EMPTY_INVENTORY => InventoryManager.emptyInventory()
      case EVENT_GET_CHAOS_SET => InventoryManager.extractChaosSet()
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
    JIntellitype.getInstance().addHotKeyListener(this)
    JIntellitype.getInstance().registerHotKey(EVENT_QUIT, jintellitype.JIntellitypeConstants.MOD_CONTROL, 'Q')
    JIntellitype.getInstance().registerHotKey(EVENT_EMPTY_INVENTORY, jintellitype.JIntellitypeConstants.MOD_CONTROL, 'B')
    JIntellitype.getInstance().registerHotKey(EVENT_GET_CHAOS_SET, jintellitype.JIntellitypeConstants.MOD_CONTROL, 'G')
    JIntellitype.getInstance().registerHotKey(EVENT_CALIBRATE, jintellitype.JIntellitypeConstants.MOD_CONTROL, 'T')
  }

  def calibrate(): Unit = {
    Screen.update()

    Stash.resetTab()
    Stash.activateBodyTab(Mode.NO_READ)
    val tab: Tab = Stash.currentTab().get
    tab.drawBoxes()

    Inventory.drawBoxes()

    Screen.save("calibration.png")
  }
}
