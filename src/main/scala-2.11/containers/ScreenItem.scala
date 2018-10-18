package containers

import com.poe.parser.item.Item

/**
  * containers.ScreenItem contains the poe item as well as additional data for inventory management
  * @param data
  * @param positions
  */
class ScreenItem(
                val data: Item,
                var positions: Seq[Position] = List.empty[Position]
                ) {
  def position: Option[Position] = if(positions.isEmpty) None else Option(positions(0))

  override def toString: String = s"($data, $positions)"
}
