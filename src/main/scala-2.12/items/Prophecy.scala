package items

import structures.Position

class Prophecy(
                position: Position,
                rarity: String,
                base: String,
                name: Option[String]
              ) extends Item (position, rarity, base, name) {

}