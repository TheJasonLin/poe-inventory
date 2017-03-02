import TabType._
import structures.Position

class Tab(
           val index: Int,
           val tabType: TabType
         )
  extends Container {

  val xCeil: Option[Int] = tabType match {
    case NORMAL => Option(Config.NORMAL_TAB_BOTTOM_RIGHT_COORD._1)
    case QUAD => Option(Config.QUAD_TAB_BOTTOM_RIGHT_COORD._1)
    case SPECIAL => None
  }

  val yCeil: Option[Int] = tabType match {
    case NORMAL => Option(Config.NORMAL_TAB_BOTTOM_RIGHT_COORD._2)
    case QUAD => Option(Config.QUAD_TAB_BOTTOM_RIGHT_COORD._2)
    case SPECIAL => None
  }

  val pixelWidth: Option[Int] = if(xCeil.isDefined) Option(xCeil.get - xBase().get) else None
  val pixelHeight: Option[Int] = if(xCeil.isDefined) Option(yCeil.get - yBase().get) else None

  val specialTabAccessError = "Attempting to access Special Tab dimensions"
  override def xBase(): Option[Int] = tabType match {
    case NORMAL => Option(Config.NORMAL_TAB_TOP_LEFT_COORD._1)
    case QUAD => Option(Config.QUAD_TAB_TOP_LEFT_COORD._1)
    case SPECIAL => throw new IllegalStateException(specialTabAccessError)
  }

  override def yBase(): Option[Int] = tabType match {
    case NORMAL => Option(Config.NORMAL_TAB_TOP_LEFT_COORD._2)
    case QUAD => Option(Config.QUAD_TAB_TOP_LEFT_COORD._2)
    case SPECIAL => throw new IllegalStateException(specialTabAccessError)
  }

  override def xCellOffset(): Option[Double] =
    if(width().isDefined)
      Option(pixelWidth.get.asInstanceOf[Double] / (width().get - 1).asInstanceOf[Double])
    else None

  override def width(): Option[Int] = tabType match {
    case NORMAL => Option(Config.NORMAL_TAB_WIDTH)
    case QUAD => Option(Config.QUAD_TAB_WIDTH)
    case SPECIAL => throw new IllegalStateException(specialTabAccessError)
  }

  override def yCellOffset(): Option[Double] =
    if(height().isDefined)
      Option(pixelHeight.get.asInstanceOf[Double] / (height().get - 1).asInstanceOf[Double])
    else None

  override def height(): Option[Int] = tabType match {
    case NORMAL => Option(Config.NORMAL_TAB_HEIGHT)
    case QUAD => Option(Config.QUAD_TAB_HEIGHT)
    case SPECIAL => throw new IllegalStateException(specialTabAccessError)
  }

  override def cellRadius(): Option[Int] = tabType match {
    case NORMAL => Option(Config.NORMAL_TAB_CELL_RADIUS)
    case QUAD => Option(Config.QUAD_TAB_CELL_RADIUS)
    case SPECIAL => throw new IllegalStateException(specialTabAccessError)
  }

  override def createPositions(): Option[Seq[Seq[Position]]] = {
    if(tabType == SPECIAL) {
      None
    } else {
      super.createPositions()
    }
  }
}
