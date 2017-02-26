import java.awt.{Color, Robot}

import items.{Item, ItemFactory}
import structures.{PixelPosition, Position}

import scala.collection.mutable.ListBuffer

abstract class Container {
  val robot: Robot = new Robot
  val items: ListBuffer[Item] = new ListBuffer[Item]

  def xBase: Int

  def yBase: Int

  def width: Int

  def height: Int

  def xCellOffset: Int

  def yCellOffset: Int

  def occupiedPositions: Seq[Position] = {
    val occupiedPositions: ListBuffer[Position] = new ListBuffer[Position]

    val rows: List[Int] = List.range(0, height)
    val columns: List[Int] = List.range(0, width)

    rows.foreach((row: Int) => {
      columns.foreach((column: Int) => {
        val position: Position = new Position(row, column)
        if (isItemPresent(position)) {
          occupiedPositions += position
        }
      })
    })
    occupiedPositions
  }

  def isItemPresent(position: Position): Boolean = {
    val pixels = getPixels(position)
    val color = robot.getPixelColor(pixels.x, pixels.y)
    !isEmptyColor(color)
  }

  def getPixels(position: Position): PixelPosition = {
    val x = xBase + position.column * xCellOffset
    val y = yBase + position.row * yCellOffset
    new PixelPosition(x, y)
  }

  def isEmptyColor(color: Color): Boolean = {
    color.getBlue < 16 && color.getRed < 16 && color.getGreen < 16
  }

  /**
    * Reads all items. Populates list of items
    */
  def update(): Unit = {
    // get all occupied positions
    val positions: Seq[Position] = occupiedPositions
    // iterate over positions, scraping the items. If a multi-cell item is found, mark its other positions as visited
    positions.foreach((position) => {
      // only create an item if this is the first time we've seen it
      if (position.item.isEmpty) {
        // create item
        val clipboard: String = Clicker.getItemInfo(getPixels(position))
        val item: Item = ItemFactory.create(clipboard)
        println(item)
        // record item
        items += item
        // mark positions with item
        position.item = Option(item)
        val lastRow = position.row + (item.height - 1)
        val lastColumn = position.column + (item.width - 1)
        // get positions covered
        val itemPositions: ListBuffer[Position] = new ListBuffer[Position]
        for (row <- position.row to lastRow) {
          for (column <- position.column to lastColumn) {
            itemPositions += positions.find((position: Position) => {
              position.row == row && position.column == column
            }).get
          }
        }
        // mark positions
        item.positions = itemPositions.toList
        item.positions.foreach((position) => {
          position.item = Option(item)
        })
      }
    })
  }

  def getItem(position: Position): Item = {
    val itemInfo: String = Clicker.getItemInfo(getPixels(position))
    ItemFactory.create(itemInfo)
  }
}
