import java.awt.{Color, Robot}
import java.awt.event.{InputEvent, KeyEvent}

import items.{Item, ItemFactory}
import structures.{PixelPosition, Position}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

object Inventory {
  val xBase = Config.INVENTORY_TOP_LEFT_COORD._1
  val yBase = Config.INVENTORY_TOP_LEFT_COORD._2
  val xCeil = Config.INVENTORY_BOTTOM_RIGHT_COORD._1
  val yCeil = Config.INVENTORY_BOTTOM_RIGHT_COORD._2
  val pixelWidth = xCeil - xBase
  val pixelHeight = yCeil - yBase
  val width = Config.INVENTORY_WIDTH
  val height = Config.INVENTORY_HEIGHT
  val xCellOffset = pixelWidth / (width - 1)
  val yCellOffset = pixelHeight / (height - 1)

  val robot: Robot = new Robot()

  val items: ListBuffer[Item] = new ListBuffer[Item]

  def sendItemToStash(position: Position): Boolean = {
    Clicker.click(getPixels(position), ctrlMod = true)
  }

  def sendItemToStash(item: Item): Boolean = {
    Clicker.click(getPixels(item.position), ctrlMod = true)
  }

  def isEmptyColor(color: Color): Boolean = {
    color.getBlue < 16 && color.getRed < 16 && color.getGreen < 16
  }

  def isItemPresent(position: Position): Boolean = {
    val pixels = getPixels(position)
    val color = robot.getPixelColor(pixels.x, pixels.y)
    !isEmptyColor(color)
  }

  def getOccupiedPositions(): Seq[Position] = {
    val occupiedPositions: ListBuffer[Position] = new ListBuffer[Position]

    val rows: List[Int] = List.range(0, getHeight())
    val columns: List[Int] = List.range(0, getWidth())

    rows.foreach((row: Int) => {
      columns.foreach((column: Int) => {
        val position: Position = new Position(row, column)
        if (Inventory.isItemPresent(position)) {
          occupiedPositions += position
        }
      })
    })
    occupiedPositions
  }

  def getItem(position: Position): Item = {
    val itemInfo: String = Clicker.getItemInfo(getPixels(position))
    ItemFactory.create(position, itemInfo)
  }

  def readInventory(): Unit = {
    // get all occupied positions
    val occupiedPositions = getOccupiedPositions()
    // iterate over positions, scraping the items. If a multi-cell item is found, mark its other positions as visited
    occupiedPositions.foreach((position) => {
      Clicker.getItemInfo(getPixels(position))
    })

  }

  def getPixels(position: Position): PixelPosition = {
    new PixelPosition(xBase + position.column * xCellOffset, yBase + position.row * yCellOffset)
  }

  def getWidth(): Int = {
    width
  }

  def getHeight(): Int = {
    height
  }
}
