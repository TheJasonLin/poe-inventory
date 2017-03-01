import structures.Position

object Config {
  val CURRENCY_TAB: Int = 0
  val ESSENCE_TAB: Int = 1
  val DIVINATION_TAB: Int = 2

  val BOOT_ALLOCATION: Allocation = new Allocation(
    3,
    new Position(0, 0),
    new Position(11, 1)
  )

  val GLOVE_ALLOCATION: Allocation = new Allocation(
    3,
    new Position(0, 2),
    new Position(11, 3)
  )

  val HELMET_ALLOCATION: Allocation = new Allocation(
    3,
    new Position(0, 4),
    new Position(11, 5)
  )

  val BODY_ALLOCATION: Allocation = new Allocation(
    3,
    new Position(0, 6),
    new Position(11, 7)
  )

  val WEAPON_ALLOCATION: Allocation = new Allocation(
    3,
    new Position(0, 8),
    new Position(11, 11)
  )

  val RING_ALLOCATION: Allocation = new Allocation(
    4,
    new Position(0, 0),
    new Position(11, 4)
  )

  val AMULET_ALLOCATION: Allocation = new Allocation(
    4,
    new Position(0, 5),
    new Position(11, 7)
  )

  val BELT_ALLOCATION: Allocation = new Allocation(
    4,
    new Position(0, 8),
    new Position(11, 11)
  )


  val TAB_TOP_LEFT_COORD: (Int, Int) = (42, 188)
  val TAB_BOTTOM_RIGHT_COORD: (Int, Int) = (622, 767)
  val TAB_WIDTH: Int = 12
  val TAB_HEIGHT: Int = 12
  val TAB_CELL_RADIUS: Int = 20

  val INVENTORY_TOP_LEFT_COORD: (Int, Int) = (1298, 615)
  val INVENTORY_BOTTOM_RIGHT_COORD: (Int, Int) = (1878, 825)
  val INVENTORY_HEIGHT: Int = 5
  val INVENTORY_WIDTH: Int = 12
  val INVENTORY_CELL_RADIUS: Int = TAB_CELL_RADIUS


}
