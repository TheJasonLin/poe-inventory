package items

import structures.Position

class QuestItem (
                  position: Position,
                  rarity: String,
                  base: String,
                  name: Option[String]
                ) extends Item (position, rarity, base, name) {

}