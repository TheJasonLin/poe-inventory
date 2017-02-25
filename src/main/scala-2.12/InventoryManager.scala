import java.awt.Robot

object InventoryManager {
  val robot: Robot = new Robot()

  val rowCount: Int = Inventory.getHeight()
  val columnCount: Int = Inventory.getWidth()

  val rows: List[Int] = List.range(0, rowCount)
  val columns: List[Int] = List.range(0, columnCount)


  def emptyInventory(): Unit = {
//    Stash.resetTab()
    Stash.activateCurrencyTab()
    dumpCurrency()
//    Stash.activateEssenceTab()
//    dumpAll()
//    Stash.activateDivinationTab()
//    dumpAll()
  }

  def dumpAll(): Unit = {
    Inventory.getOccupiedPositions().foreach((position: Position) => {
      val sent = Inventory.sendItemToStash(position)
      if (sent) Thread sleep 200
      else Thread sleep 50
    })
  }

  def dumpCurrency(): Unit = {
    Inventory.getOccupiedPositions()
      .map((position: Position) => {
        Inventory.getItem(position)
      })
      .filter((item: Item) => {
        item.isCurrency
      })
      .foreach((item: Item) => {
        Inventory.sendItemToStash(item)
      })
  }
}
