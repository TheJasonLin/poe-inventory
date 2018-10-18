package containers

import java.awt.{Color, Robot}

import com.poe.parser.ItemFactory
import com.poe.parser.item.Item
import screen.{PixelPosition, Screen}

import scala.collection.mutable.ListBuffer

abstract class Container {
  val robot: Robot = new Robot
  val items: ListBuffer[ScreenItem] = new ListBuffer[ScreenItem]
  var upToDate: Boolean = false
  private val _positions: Option[Seq[Seq[Position]]] = createPositions()

  def xBase(): Option[Int]

  def yBase(): Option[Int]

  def width(): Option[Int]

  def height(): Option[Int]

  def xCellOffset(): Option[Double]

  def yCellOffset(): Option[Double]

  def cellRadius(): Option[Int]

  def createPositions(): Option[Seq[Seq[Position]]] = {
    val matrix = Array.ofDim[Position](height().get, width().get)

    for (x <- 0 until height().get; y <- 0 until width().get) {
      matrix(x)(y) = new Position(x, y)
    }
    Option(matrix.map(_.toSeq).toSeq)
  }

  def positions(): Seq[Position] = _positions.get.flatMap(_.toStream)

  /**
    * testing Purposes
    */
  def drawBoxes(): Unit = {
    positions()
      .foreach((position: Position) => {
        val pixelPosition: PixelPosition = getPixels(position)
        Screen.setPixels(pixelPosition, cellRadius().get)
      })
  }

  private def getItem(position: Position): ScreenItem = {
    val itemInfo: String = Clicker.getItemInfo(getPixels(position))
    val poeItem: Item = ItemFactory.create(itemInfo)
    new ScreenItem(poeItem)
  }

  def getPixels(position: Position): PixelPosition = {
    val x = xBase().get + position.column * xCellOffset().get
    val y = yBase().get + position.row * yCellOffset().get
    new PixelPosition(x.asInstanceOf[Int], y.asInstanceOf[Int])
  }

  /**
    * @param item
    * @param position top left corner position of them item
    */
  def addItem(item: ScreenItem, position: Position): Unit = {
    items += item
    // mark positions
    // get covered positions
    val coveredPositions: Seq[Position] = getAdjacentPositions(item, position, positions()).get
    // mark positions
    item.positions = coveredPositions
    item.positions.foreach((position) => {
      position.item = Option(item)
    })
  }

  def removeItem(item: ScreenItem): Unit = {
    // remove from list of items
    items -= item
    // update positions about change
    item.positions.foreach((position: Position) => {
      position._item = None
    })
    item.positions = List.empty[Position]
  }

  private def isItemPresent(position: Position): Boolean = {
    val pixels = getPixels(position)
    val pixelColors: Seq[Color] = Screen.getPixels(pixels, cellRadius().get)
    val present: Boolean = hasColor(pixelColors)
    present
  }

  /**
    * Looking for the Red or Blue that all items have that indicate whether they can be equipped
    *
    * @param pixelColors
    * @return
    */
  private def hasColor(pixelColors: Seq[Color]): Boolean = {
    val coloredPixelsColorOption: Option[Color] = pixelColors.find((color: Color) => {
      val red = color.getRed
      val green = color.getGreen
      val blue = color.getBlue
      val isRed = red > 40 && green < 8 && blue < 8
      val isBlue = blue >= 30 && green < 8 && red < 8
      isRed || isBlue
    })

    coloredPixelsColorOption.isDefined
  }

  /**
    * Updates Occupancy info ONLY
    */
  def updateOccupancy(): Unit = {
    Clicker.center()
    positions
      .foreach((position: Position) => {
        val itemPresent: Boolean = isItemPresent(position)
        position.occupied = itemPresent
        position._item = None
      })
  }

  /**
    * if there's a valid set of positions of the 'item' size with the top left corner as 'position' among 'positions'
    * it returns those
    *
    * @param item      item with desired size
    * @param position  position to use as the top left corner
    * @param positions all possible positions
    * @return
    */
  def getAdjacentPositions(item: ScreenItem, position: Position, positions: Seq[Position]): Option[Seq[Position]] = {
    var isValid = true
    val lastRow = position.row + (item.data.height - 1)
    val lastColumn = position.column + (item.data.width - 1)
    val adjacentPositions: ListBuffer[Position] = new ListBuffer[Position]
    for (row <- position.row to lastRow; column <- position.column to lastColumn) {
      val positionOption: Option[Position] = positions.find((position: Position) => {
        position.row == row && position.column == column
      })
      if (positionOption.isDefined) adjacentPositions += positionOption.get
      else isValid = false
    }
    if (isValid) Option(adjacentPositions.toList)
    else None
  }

  /**
    * Updates Items and Positions from the screen
    */
  def updateOccupancyAndItems(): Unit = {
    // clear items
    items.clear()
    // update occupancy
    updateOccupancy()
    // get occupied positions
    val occupiedPositions: Seq[Position] = positions()
      .filter((position: Position) => {
        position.occupied
      })
    // in each occupied position, get the item and mark it along with any other occupied positions
    occupiedPositions.foreach((position) => {
      // only create an item if this is the first time we've seen it
      if (position._item.isEmpty) {
        // read item and record it
        readAndRecordItem(position)
      }
    })
    // mark container as having been updated
    upToDate = true
  }

  def readAndRecordItem(position: Position): ScreenItem = {
    // create item
    val item: ScreenItem = getItem(position)
    // record item
    addItem(item, position)
    item
  }

  /**
    * returns positions that are in the allocation
    *
    * @param allocation
    * @return
    */
  def positionsInAllocation(allocation: Allocation): Seq[Position] = {
    val rowMin = allocation.region.get.topLeft.row
    val colMin = allocation.region.get.topLeft.column
    val rowMax = allocation.region.get.bottomRight.row
    val colMax = allocation.region.get.bottomRight.column
    positions()
      .filter((position: Position) => {
        position.row >= rowMin && position.row <= rowMax && position.column >= colMin && position.column <= colMax
      })
  }

  /**
    * Attempts to find a free positions that'll hold the item, then returns the information needed to drop it off
    *
    * @param item       item to be dropped off
    * @param allocation rules to follow
    * @return (
    *         the pixel on screen it should be dropped off at,
    *         the target top left position of the item,
    *         all target positions
    *         )
    */
  def findOpenPositionInAllocation(item: ScreenItem, allocation: Allocation): Option[(PixelPosition, Position)] = {
    // find free spaces in allocation
    val possiblePositions: Seq[Position] = positionsInAllocation(allocation)
      .filter(_.occupied == false)
    // find adjacent spaces that fit item
    var adjacentPositions: Seq[Position] = Seq.empty[Position]
    val topLeftPositionOption: Option[Position] = possiblePositions.find((position: Position) => {
      // check if this is a valid topLeftPosition
      val adjacentPositionsOption = getAdjacentPositions(item, position, possiblePositions)
      val success = adjacentPositionsOption.isDefined
      if (success) {
        adjacentPositions = adjacentPositionsOption.get
      }
      success
    })
    if (topLeftPositionOption.isEmpty) return None
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
      topLeftPosition
    )
  }


  def ctrlClickItem(item: ScreenItem): Boolean = {
    if (item.position.isEmpty) throw new IllegalArgumentException("Item has no position")
    val sent: Boolean = Clicker.click(getPixels(item.position.get), ctrlMod = true)
    // mark item as sent
    if (sent) {
      removeItem(item)
    }
    sent
  }

  /**
    * WARNING: Only use this if you don't care where things end up. Use ctrlClickItem whenever possible
    * This is much faster but just blindly clicks
    *
    * @param position
    */
  def ctrlClickPosition(position: Position): Unit = {
    Clicker.click(getPixels(position), ctrlMod = true)
  }
}
