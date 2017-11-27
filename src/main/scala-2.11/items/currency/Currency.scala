package items.currency

import items.Item

class Currency(
                rarity: String,
                base: String,
                name: Option[String]
              ) extends Item(rarity, base, name) {

}