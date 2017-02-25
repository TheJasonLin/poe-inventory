package items

import structures.Position

abstract class Item(
          val position: Position,
          val rarity: String,
          val base: String,
          val name: Option[String]
          ) {
}
