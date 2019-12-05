package constants

object RarityFactory {
  def getByString(key: String): Option[Rarity] = {
    key match {
      case "Normal" =>
        Option(Rarity.NORMAL)
      case "Magic" =>
        Option(Rarity.MAGIC)
      case "Rare" =>
        Option(Rarity.RARE)
      case "Unique" =>
        Option(Rarity.UNIQUE)
      case "Gem" =>
        Option(Rarity.GEM)
      case "Currency" =>
        Option(Rarity.CURRENCY)
      case "Divination Card" =>
        Option(Rarity.DIVINATION_CARD)
      case "Quest Item" =>
        Option(Rarity.QUEST_ITEM)
      case "Prophecy" =>
        Option(Rarity.PROPHECY)
      case "Relic" =>
        Option(Rarity.RELIC)
      case _ =>
        None
    }
  }
}
