package items

import structures.Position

class Divination (
                   position: Position,
                   rarity: String,
                   base: String,
                   name: Option[String]
                 ) extends Item (position, rarity, base, name) {

}