package items

class Talisman(
                rarity: String,
                base: String,
                name: Option[String],
                val talismanTier: Int
              ) extends Item(rarity, base, name) {
  override def toString: String = super.toString + s"[talismanTier: $talismanTier]"
}
