import config.Config
import constants.Rarity
import containers._
import maps.MapCurrency
import parser.item._
import parser.item.currency._
import parser.item.equipment._
import parser.item.equipment.accessory._
import parser.item.equipment.armour._
import parser.item.equipment.weapon._
import screen.PixelPosition

object Inventory extends Container {
  val xCeil: Int = Config.INVENTORY_REGION_COORDS.bottomRight.x
  val yCeil: Int = Config.INVENTORY_REGION_COORDS.bottomRight.y
  val pixelWidth: Int = xCeil - xBase().get
  val pixelHeight: Int = yCeil - yBase().get

  override def xBase(): Option[Int] = Option(Config.INVENTORY_REGION_COORDS.topLeft.x)

  override def yBase() = Option(Config.INVENTORY_REGION_COORDS.topLeft.y)

  override def cellRadius() = Option(Config.INVENTORY_CELL_RADIUS)

  override def xCellOffset(): Option[Double] = Option(pixelWidth.asInstanceOf[Double] / (width().get - 1).asInstanceOf[Double])

  override def width() = Option(Config.INVENTORY_WIDTH)

  override def yCellOffset(): Option[Double] = Option(pixelHeight.asInstanceOf[Double] / (height().get - 1).asInstanceOf[Double])

  override def height() = Option(Config.INVENTORY_HEIGHT)

  /**
    * Moves an item to an allocation if possible.
    * requirements: should already be on the tab
    *
    * @param item
    * @param allocation
    * @return
    */
  def sendItemToAllocation(item: ScreenItem, allocation: Allocation): Boolean = {
    if (item.positions.isEmpty) throw new IllegalArgumentException("Item has no position")
    val currentTabOption: Option[Tab] = Stash.currentTab()
    if (currentTabOption.isEmpty) throw new IllegalArgumentException("Current Tab layout isn't defined")
    val currentTab: Tab = currentTabOption.get
    // get location in the current tab that's open, given the provided allocation
    val positionInfoOption: Option[(PixelPosition, Position)] = currentTab.findOpenPositionInAllocation(item, allocation)
    if (positionInfoOption.isEmpty) return false
    val dropPosition: PixelPosition = positionInfoOption.get._1
    val topLeftPosition: Position = positionInfoOption.get._2

    // move item from A to B
    val moveSuccess: Boolean = Clicker.move(getPixels(item.position.get), dropPosition)
    // inform tab of new item
    if (moveSuccess) {
      currentTab.addItem(item, topLeftPosition)
    }
    moveSuccess
  }

  def basicCurrencies: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[BasicCurrency]
    })
  }

  def essences: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Essence]
    })
  }

  def divinationCards: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[DivinationCard]
    })
  }

  def maps: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[MapItem]
    })
  }

  def delves: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Delve]
    })
  }

  def fragments: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Fragment]
    })
  }

  /**************************************************************
    * Start Misc Desirable Items
   ***************************************************************/
  def miscDesirableItems: Seq[ScreenItem] = {
    var items = Seq[ScreenItem]()
    items ++= Inventory.qualityGems
    items ++= Inventory.qualityFlasks
    items ++= Inventory.talismans
    items ++= Inventory.leaguestones
    items ++= Inventory.oils

    items
  }

  def qualityFlasks: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Flask]
    }).filter((item: ScreenItem) => {
      val flask = item.data.asInstanceOf[Flask]
      flask.quality.isDefined && flask.quality.get > 0
    })
  }

  def qualityGems: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Gem]
    }).filter((item: ScreenItem) => {
      val gem = item.data.asInstanceOf[Gem]
      gem.quality.getOrElse(0) > 0
    })
  }

  def leaguestones: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Leaguestone]
    })
  }

  def talismans: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Talisman]
    })
  }

  def oils: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Oil]
    })
  }
  /***************************************************************
   * End Misc Desirable Items
   ***************************************************************/

  def findCurrency(name: String): Option[ScreenItem] = {
    basicCurrencies.find((item: ScreenItem) => {
      item.data.typeLine == name
    })
  }

  def hasScouringAndAlchemy: Boolean = {
    val hasScouring: Boolean = basicCurrencies.exists((item: ScreenItem) => item.data.typeLine == MapCurrency.ORB_OF_SCOURING.getName)
    val hasAlchemy: Boolean = basicCurrencies.exists((item: ScreenItem) => item.data.typeLine == MapCurrency.ORB_OF_ALCHEMY.getName)
    hasScouring && hasAlchemy
  }
}
