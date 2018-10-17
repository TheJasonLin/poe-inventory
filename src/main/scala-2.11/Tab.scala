import structures.TabType._
import structures.{Position, TabType}

class Tab(
           val index: Int,
           val tabType: TabType
         )
  extends Container {

  val xCeil: Option[Int] = tabType match {
    case NORMAL => Option(Config.NORMAL_TAB_REGION_COORDS.bottomRight.x)
    case QUAD => Option(Config.QUAD_TAB_REGION_COORDS.bottomRight.x)
    case SPECIAL => None
  }

  val yCeil: Option[Int] = tabType match {
    case NORMAL => Option(Config.NORMAL_TAB_REGION_COORDS.bottomRight.y)
    case QUAD => Option(Config.QUAD_TAB_REGION_COORDS.bottomRight.y)
    case SPECIAL => None
  }

  val pixelWidth: Option[Int] = if(xCeil.isDefined) Option(xCeil.get - xBase().get) else None
  val pixelHeight: Option[Int] = if(xCeil.isDefined) Option(yCeil.get - yBase().get) else None

  val specialTabAccessError = "Attempting to access Special Tab dimensions"
  override def xBase(): Option[Int] = tabType match {
    case NORMAL => Option(Config.NORMAL_TAB_REGION_COORDS.topLeft.x)
    case QUAD => Option(Config.QUAD_TAB_REGION_COORDS.topLeft.x)
    case SPECIAL => throw new IllegalStateException(specialTabAccessError)
  }

  override def yBase(): Option[Int] = tabType match {
    case NORMAL => Option(Config.NORMAL_TAB_REGION_COORDS.topLeft.y)
    case QUAD => Option(Config.QUAD_TAB_REGION_COORDS.topLeft.y)
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
