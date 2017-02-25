package items.currency

import structures.Position

class Essence(
               position: Position,
               rarity: String,
               base: String,
               name: Option[String]
             ) extends Currency(position, rarity, base, name) {

}
