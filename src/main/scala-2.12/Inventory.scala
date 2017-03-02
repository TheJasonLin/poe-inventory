import java.util.Optional

import items.currency.{BasicCurrency, Essence}
import items.equipment.Equipment
import items.equipment.accessory.{Accessory, Quiver}
import items.equipment.armour.{Armour, Shield}
import items.equipment.weapon.{Dagger, Wand}
import items.map.MapItem
import items.{DivinationCard, Item}
import structures.{PixelPosition, Position}

object Inventory extends Container {
  val xCeil: Int = Config.INVENTORY_BOTTOM_RIGHT_COORD._1
  val yCeil: Int = Config.INVENTORY_BOTTOM_RIGHT_COORD._2
  val pixelWidth: Int = xCeil - xBase().get
  val pixelHeight: Int = yCeil - yBase().get

  override def xBase(): Option[Int] = Option(Config.INVENTORY_TOP_LEFT_COORD._1)

  override def yBase() = Option(Config.INVENTORY_TOP_LEFT_COORD._2)

  override def cellRadius() = Option(Config.INVENTORY_CELL_RADIUS)

  override def xCellOffset(): Option[Double] = Option(pixelWidth.asInstanceOf[Double] / (width().get - 1).asInstanceOf[Double])

  override def width() = Option(Config.INVENTORY_WIDTH)

  override def yCellOffset(): Option[Double] = Option(pixelHeight.asInstanceOf[Double] / (height().get - 1).asInstanceOf[Double])

  override def height() = Option(Config.INVENTORY_HEIGHT)

  /**
    * Moves an item to an allocation if possible.
    * requirements: should already be on the tab
    * @param item
    * @param allocation
    * @return
    */
  def sendItemToAllocation(item: Item, allocation: Allocation): Boolean = {
    if (item.positions.isEmpty) throw new IllegalArgumentException("Item has no position")
    val currentTabOption: Option[Tab] = Stash.currentTab()
    if(currentTabOption.isEmpty) throw new IllegalArgumentException("Current Tab layout isn't defined")
    val currentTab: Tab = currentTabOption.get
    // get location in the current tab that's open, given the provided allocation
    val positionInfoOption: Option[(PixelPosition, Position)] = currentTab.findOpenPositionInAllocation(item, allocation)
    if(positionInfoOption.isEmpty) return false
    val dropPosition: PixelPosition = positionInfoOption.get._1
    val topLeftPosition: Position = positionInfoOption.get._2

    // move item from A to B
    val moveSuccess: Boolean = Clicker.move(getPixels(item.position.get), dropPosition)
    // inform tab of new item
    if(moveSuccess) {
      currentTab.addItem(item, topLeftPosition)
    }
    moveSuccess
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

  def maps: Seq[MapItem] = {
    items.filter((item: Item) => {
      item.isInstanceOf[MapItem]
    }).map((item: Item) => {
      item.asInstanceOf[MapItem]
    })
  }

  def chaosEquipment: Seq[Equipment] = {
    items
      // make usre it's rare
      .filter((item) => item.rarity == "Rare")
      // make sure it's equipment
      .filter((item: Item) => item.isInstanceOf[Equipment])
      // map it to equipment
      .map((item) => item.asInstanceOf[Equipment])
      // make sure its item level 60 or Higher
      .filter((item) => item.itemLevel >= 60)
      // make sure its unidentified
      .filter((item) => !item.identified)
      // make sure it's desired for chaos recipe
      .filter((item: Item) => {
        val isArmour: Boolean = item.isInstanceOf[Armour] && !item.isInstanceOf[Shield]
        val isAccessory: Boolean = item.isInstanceOf[Accessory] && !item.isInstanceOf[Quiver]
        val isSmallWeapon: Boolean = item.isInstanceOf[Dagger] || item.isInstanceOf[Wand]
        isArmour || isAccessory || isSmallWeapon
      })
      .map((item: Item) => {
        item.asInstanceOf[Equipment]
      })
  }
}
