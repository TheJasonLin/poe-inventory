package items

class UnknownItem(
                   rarity: String,
                   base: String,
                   name: Option[String]
                 ) extends Item(rarity, base, name) {
}
