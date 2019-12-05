package parser.item.equipment.accessory

import parser.knowninfo.KnownInfo

object AccessoryFactory {
  def create(knownInfo: KnownInfo): Option[Accessory] = {
    val typeLineWords: Seq[String] = knownInfo.typeLine.split(' ')
    if (typeLineWords.contains("Amulet")) {
      return Option(new Amulet(knownInfo))
    } else if(typeLineWords.contains("Talisman")) {
      return Option(new Talisman(knownInfo))
    } else if (typeLineWords.contains("Ring")) {
      return Option(new Ring(knownInfo))
    } else if (typeLineWords.contains("Belt") || typeLineWords.contains("Sash") || knownInfo.typeLine.contains("Stygian Vise")) {
      return Option(new Belt(knownInfo))
    } else if (typeLineWords.contains("Quiver")) {
      return Option(new Quiver(knownInfo))
    }
    None
  }
}
