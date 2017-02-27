import items.currency.{BasicCurrency, Essence}
import items.{DivinationCard, Item}
import structures.Position

object Inventory extends Container {
  override def xBase = Config.INVENTORY_TOP_LEFT_COORD._1
  override def yBase = Config.INVENTORY_TOP_LEFT_COORD._2
  override def width = Config.INVENTORY_WIDTH
  override def height = Config.INVENTORY_HEIGHT
  override def emptyCheckRadius = Config.INVENTORY_EMPTY_CHECK_RADIUS
  val xCeil: Int = Config.INVENTORY_BOTTOM_RIGHT_COORD._1
  val yCeil: Int = Config.INVENTORY_BOTTOM_RIGHT_COORD._2
  val pixelWidth: Int = xCeil - xBase
  val pixelHeight: Int = yCeil - yBase
  override def xCellOffset: Int = pixelWidth / (width - 1)
  override def yCellOffset: Int = pixelHeight / (height - 1)

  def sendItemToStash(item: Item, stashTab: Option[Tab] = None): Boolean = {
    if (item.position.isEmpty) throw new IllegalArgumentException("Item has no position")
    val sent: Boolean = Clicker.click(getPixels(item.position.get), ctrlMod = true)
    // mark item as sent
    if (sent) {
      item.positions.foreach((position: Position) => {
        position.item = None
      })
      item.positions = List.empty[Position]
    }
    // 

    sent
  }

  def basicCurrencies: Seq[BasicCurrency] = {
    items.filter((item: Item) => {
      item.isInstanceOf[BasicCurrency]
    }).map((item: Item) => {
      item.asInstanceOf[BasicCurrency]
    })
  }

  def essences: Seq[Essence] = {
    items.filter((item: Item) => {
      item.isInstanceOf[Essence]
    }).map((item: Item) => {
      item.asInstanceOf[Essence]
    })
  }

  def divinationCards: Seq[DivinationCard] = {
    items.filter((item: Item) => {
      item.isInstanceOf[DivinationCard]
    }).map((item: Item) => {
      item.asInstanceOf[DivinationCard]
    })
  }
}
