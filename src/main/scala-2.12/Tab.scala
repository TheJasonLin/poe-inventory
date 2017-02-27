
class Tab extends Container {
  override def xBase: Int = Config.TAB_TOP_LEFT_COORD._1
  override def yBase: Int = Config.TAB_TOP_LEFT_COORD._2
  override def width: Int = Config.TAB_WIDTH
  override def height: Int = Config.TAB_HEIGHT
  val xCeil: Int = Config.TAB_BOTTOM_RIGHT_COORD._1
  val yCeil: Int = Config.TAB_BOTTOM_RIGHT_COORD._2
  val pixelWidth: Int = xCeil - xBase
  val pixelHeight: Int = yCeil - yBase
  override def xCellOffset: Int = pixelWidth / (width - 1)
  override def yCellOffset: Int = pixelHeight / (height - 1)
  override def emptyCheckRadius = Config.TAB_EMPTY_CHECK_RADIUS
}
