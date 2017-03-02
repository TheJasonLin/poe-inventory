import structures.{Position, Region}
import TabType._

object Config {
  val CALIBRATION_NORMAL_TAB_INDEX: Option[Int] = Option(4)
  val CALIBRATION_QUAD_TAB_INDEX: Option[Int] = Option(3)

  val CURRENCY_ALLOCATION: Allocation = a(0, SPECIAL)
  val ESSENCE_ALLOCATION: Allocation = a(1, SPECIAL)
  val DIVINATION_ALLOCATION: Allocation = a(2, SPECIAL)

  /**
    * Specify where you want each tier of maps allocated
    */
  val MAP_ALLOCATION = collection.immutable.HashMap(
    1 -> a(3, QUAD, r(0, 0, 23, 0)),
    2 -> a(3, QUAD, r(0, 1, 23, 1)),
    3 -> a(3, QUAD, r(0, 2, 23, 2)),
    4 -> a(3, QUAD, r(0, 3, 23, 3)),
    5 -> a(3, QUAD, r(0, 4, 23, 4)),
    6 -> a(3, QUAD, r(0, 5, 23, 5)),
    7 -> a(3, QUAD, r(0, 6, 23, 6)),
    8 -> a(3, QUAD, r(0, 7, 23, 7)),
    9 -> a(3, QUAD, r(0, 8, 23, 8)),
    10 -> a(3, QUAD, r(0, 9, 23, 9)),
    11 -> a(3, QUAD, r(0, 10, 23, 11)),
    12 -> a(3, QUAD, r(0, 12, 23, 12)),
    13 -> a(3, QUAD, r(0, 13, 23, 14)),
    14 -> a(3, QUAD, r(0, 15, 23, 15)),
    15 -> a(3, QUAD, r(0, 16, 23, 16)),
    16 -> a(3, QUAD, r(0, 17, 23, 17))
  )

  val BOOT_ALLOCATION: Allocation = a(4, NORMAL, r(0, 0, 11, 1))
  val GLOVE_ALLOCATION: Allocation = a(4, NORMAL, r(0, 2, 11, 3))
  val HELMET_ALLOCATION: Allocation = a(4, NORMAL, r(0, 4, 11, 5))
  val BODY_ALLOCATION: Allocation = a(4, NORMAL, r(0, 6, 11, 7))
  val WEAPON_ALLOCATION: Allocation = a(4, NORMAL, r(0, 8, 11, 11))
  val RING_ALLOCATION: Allocation = a(5, NORMAL, r(0, 0, 11, 4))
  val AMULET_ALLOCATION: Allocation = a(5, NORMAL, r(0, 5, 11, 7))
  val BELT_ALLOCATION: Allocation = a(5, NORMAL, r(0, 8, 11, 11))

  val NORMAL_TAB_TOP_LEFT_COORD: (Int, Int) = (42, 188)
  val NORMAL_TAB_BOTTOM_RIGHT_COORD: (Int, Int) = (622, 767)
  val NORMAL_TAB_WIDTH: Int = 12
  val NORMAL_TAB_HEIGHT: Int = 12
  val NORMAL_TAB_CELL_RADIUS: Int = 20

  val QUAD_TAB_TOP_LEFT_COORD: (Int, Int) = (30, 175)
  val QUAD_TAB_BOTTOM_RIGHT_COORD: (Int, Int) = (635, 780)
  val QUAD_TAB_WIDTH: Int = 24
  val QUAD_TAB_HEIGHT: Int = 24
  val QUAD_TAB_CELL_RADIUS: Int = 10

  val INVENTORY_TOP_LEFT_COORD: (Int, Int) = (1350, 615)
  val INVENTORY_BOTTOM_RIGHT_COORD: (Int, Int) = (1878, 825)
  val INVENTORY_HEIGHT: Int = 5
  val INVENTORY_WIDTH: Int = 11
  val INVENTORY_CELL_RADIUS: Int = NORMAL_TAB_CELL_RADIUS

  private def a(tabIndex: Int, tabType: TabType, region: Option[Region] = None): Allocation = {
    new Allocation(tabIndex, tabType, region)
  }

  private def r(startRow: Int, startColumn: Int, endRow: Int, endColumn: Int): Option[Region] = Option(
    new Region(
      new Position(startRow, startColumn),
      new Position(endRow, endColumn)
    )
  )

}
