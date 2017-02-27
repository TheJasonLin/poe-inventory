import com.melloware.jintellitype
import com.melloware.jintellitype.JIntellitype
import screen.Screen

object Main extends jintellitype.HotkeyListener {
  val EVENT_QUIT: Int = 1
  val EVENT_EMPTY_INVENTORY: Int = 2
  val EVENT_UPDATE_CHAOS_TAB: Int = 3

  override def onHotKey(identifier: Int): Unit = {
    identifier match {
      case EVENT_QUIT => quit()
      case EVENT_EMPTY_INVENTORY => InventoryManager.emptyInventory()
      case EVENT_UPDATE_CHAOS_TAB => InventoryManager.updateChaosTab()
      //      case EVENT_UPDATE_CHAOS_TAB => InventoryManager.updateChaosTab()
      case otherEvent => println("Unexpected event: " + identifier)
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
    JIntellitype.getInstance().registerHotKey(EVENT_UPDATE_CHAOS_TAB, jintellitype.JIntellitypeConstants.MOD_CONTROL, 'U')
  }
}
