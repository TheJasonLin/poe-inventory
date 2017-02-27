import java.awt.{Color, Robot}

import items.{Item, ItemFactory}
import screen.Screen
import structures.{PixelPosition, Position}

import scala.collection.mutable.ListBuffer

abstract class Container {
  val robot: Robot = new Robot
  val items: ListBuffer[Item] = new ListBuffer[Item]
  var upToDate: Boolean = false

  def xBase: Int

  def yBase: Int

  def width: Int

  def height: Int

  def xCellOffset: Int

  def yCellOffset: Int

  def emptyCheckRadius: Int

  def occupiedPositions(): Seq[Position] = {
    Screen.update()
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

  def freePositions(): Seq[Position] = {
    val freePositions: ListBuffer[Position] = new ListBuffer[Position]

    val rows: List[Int] = List.range(0, height)
    val columns: List[Int] = List.range(0, width)

    rows.foreach((row: Int) => {
      columns.foreach((column: Int) => {
        val position: Position = new Position(row, column)
        if (!isItemPresent(position)) {
          freePositions += position
        }
      })
    })
    freePositions
  }

  def isItemPresent(position: Position): Boolean = {
    val pixels = getPixels(position)
    val pixelColors: Seq[Color] = Screen.getPixels(pixels.x, pixels.y, emptyCheckRadius)
    hasColor(pixelColors)
  }

  def hasColor(pixelColors: Seq[Color]): Boolean = {
    val nonBlackPixelColorOption: Option[Color] = pixelColors.find((color: Color) => {
      color.getBlue >= 16 || color.getRed >= 16 || color.getGreen >= 16
    })

    nonBlackPixelColorOption.isDefined
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
        addItem(item, position, positions)
      }
    })
    // mark container as having been updated
    upToDate = true
  }

  /**
    * Attempts to find a pixel location which the item can be dropped off at that satisfies the allocation rules
    * @param item item to be dropped off
    * @param allocation rules to follow
    * @return possible pixel location
    */
  def positionInAllocation(item: Item, allocation: Allocation): Option[(PixelPosition, Position, Seq[Position])] = {
    // find free spaces in allocation
    val openPositions: Seq[Position] = freePositions()
    val rowMin = allocation.topLeft.row
    val colMin = allocation.topLeft.column
    val rowMax = allocation.bottomRight.row
    val colMax = allocation.bottomRight.column
    val possiblePositions: Seq[Position] = openPositions.filter((position: Position) => {
      position.row >= rowMin && position.row <= rowMax && position.column >= colMin && position.column <= colMax
    })
    // find adjacent spaces that fit item
    var adjacentPositions: Seq[Position] = Seq.empty[Position]
    val topLeftPositionOption: Option[Position] = possiblePositions.find((position: Position) => {
      // check if this is a valid topLeftPosition
      val adjacentPositionsOption = getAdjacentPositions(item, position, possiblePositions)
      val success = adjacentPositionsOption.isDefined
      if(success) {
        adjacentPositions = adjacentPositionsOption.get
      }
      success
    })
    if(topLeftPositionOption.isEmpty) return None
    // get pixel positions of the spaces
    val pixelPositions: Seq[PixelPosition] = adjacentPositions
        .map((position: Position) => {
          getPixels(position)
        })
    // return the average of the pixel positions
    val sum: PixelPosition = pixelPositions.reduce[PixelPosition]((a: PixelPosition, b: PixelPosition) => {
      new PixelPosition(a.x + b.x, a.y + b.y)
    })
    val positionCount: Int = adjacentPositions.length
    val averagePosition: PixelPosition = new PixelPosition(sum.x / positionCount, sum.y / positionCount)
    val topLeftPosition: Position = topLeftPositionOption.get
    // return trio of information
    Option(
      averagePosition,
      topLeftPosition,
      adjacentPositions
    )
  }

  /**
    * if there's a valid set of positions of the 'item' size with the top left corner as 'position' among 'positions'
    * it returns those
    * @param item item with desired size
    * @param position position to use as the top left corner
    * @param positions all possible positions
    * @return
    */
  def getAdjacentPositions(item: Item, position: Position, positions: Seq[Position]): Option[Seq[Position]] = {
    var isValid = true
    val lastRow = position.row + (item.height - 1)
    val lastColumn = position.column + (item.width - 1)
    val adjacentPositions: ListBuffer[Position] = new ListBuffer[Position]
    // check that space is available
    for (row <- position.row to lastRow) {
      for (column <- position.column to lastColumn) {
        val positionOption: Option[Position] = positions.find((position: Position) => {
          position.row == row && position.column == column
        })
        if(positionOption.isDefined) adjacentPositions += positionOption.get
        else isValid = false
      }
    }
    if(isValid) Option(adjacentPositions.toList)
    else None
  }

  /**
    * always try to include all parameters. not doing so will require more screen updates
    * @param item
    * @param position top left corner position of them item
    * @param positions list of positions, where some may need to be updated to reflect the addition of the item
    */
  def addItem(item: Item, position: Position, positions: Seq[Position]): Unit = {
    items += item
    // mark positions with item
//    position.item = Option(item)
    // get covered positions
    val coveredPositions: Seq[Position] = getAdjacentPositions(item, position, positions).get
    // mark positions
    item.positions = coveredPositions
    item.positions.foreach((position) => {
      position.item = Option(item)
    })
  }

  def removeItem(item: Item): Unit = {
    // remove from list of items
    items -= item
    // update positions about change
    item.positions.foreach((position: Position) => {
      position.item = None
    })
    item.positions = List.empty[Position]
  }

  def getItem(position: Position): Item = {
    val itemInfo: String = Clicker.getItemInfo(getPixels(position))
    ItemFactory.create(itemInfo)
  }

  def getPixels(position: Position): PixelPosition = {
    val x = xBase + position.column * xCellOffset
    val y = yBase + position.row * yCellOffset
    new PixelPosition(x, y)
  }
}
