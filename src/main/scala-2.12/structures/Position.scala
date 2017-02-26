package structures

import items.Item


class Position(
                val row: Int,
                val column: Int
              ) {
  var item: Option[Item] = None

}
