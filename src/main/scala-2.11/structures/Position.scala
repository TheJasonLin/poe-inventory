package structures

import items.Item


class Position(
                val row: Int,
                val column: Int
              ) {
  var _item: Option[Item] = None
  // if a position is occupied, it may or may not have an item in certain situations (not yet updated), but a position
  // that has an item should always be occupied
  var occupied: Boolean = false

  def item: Option[Item] = _item

  def item_= (value: Option[Item]): Unit = {
    _item = value
    if(value.isDefined) occupied = true
  }

  override def toString = s"Position($occupied, $row, $column)"
}
