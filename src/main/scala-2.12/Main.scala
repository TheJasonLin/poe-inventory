import com.melloware.jintellitype
import com.melloware.jintellitype.JIntellitype
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends jintellitype.HotkeyListener {
  val EVENT_QUIT: Int = 1
  val EVENT_EMPTY_INVENTORY: Int = 2

  override def onHotKey(identifier: Int): Unit = {
    identifier match {
      case EVENT_QUIT => quit()
      case EVENT_EMPTY_INVENTORY => emptyInventory()
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
  }

  def emptyInventory(): Unit = {
    InventoryManager.emptyInventory()
  }
}
