
object Config {
  val CURRENCY_TAB: Int = 0
  val CHAOS_RECIPE_TAB: Int = 1
  val ESSENCE_TAB: Int = 2
  val DIVINATION_TAB: Int = 3

  val TAB_TOP_LEFT_COORD: (Int, Int) = (42, 188)
  val TAB_BOTTOM_RIGHT_COORD: (Int, Int) = (623, 768)
  val TAB_WIDTH: Int = 12
  val TAB_HEIGHT: Int = 12
  /**
    * How far to the left and to the right of the center of a cell we'll check for non-black cells
    * to check if it's an empty cell
    */
  val TAB_EMPTY_CHECK_RADIUS: Int = 2

  val INVENTORY_TOP_LEFT_COORD: (Int, Int) = (1297, 616)
  val INVENTORY_BOTTOM_RIGHT_COORD: (Int, Int) = (1878, 828)
  val INVENTORY_HEIGHT: Int = 5
  val INVENTORY_WIDTH: Int = 12
  val INVENTORY_EMPTY_CHECK_RADIUS: Int = 2

}
