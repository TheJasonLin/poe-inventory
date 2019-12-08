package parser

import parser.item._
import parser.item.currency.CurrencyFactory
import parser.item.equipment.EquipmentFactory
import parser.item.equipment.accessory.Talisman
import parser.knowninfo.KnownInfo

object ItemFactory {
  def create(knownInfo: KnownInfo): Item = {
    implicit
    val typeLineWords = knownInfo.typeLine.split(' ')
    if (knownInfo.isGem) return new Gem(knownInfo)
    else if (knownInfo.isDivinationCard) return new DivinationCard(knownInfo)
    else if (knownInfo.isMap) return new MapItem(knownInfo)
    else if (knownInfo.isTalisman) return new Talisman(knownInfo)
    else if (knownInfo.isLeaguestone) return new Leaguestone(knownInfo)
    else if (knownInfo.isFragment) return new Fragment(knownInfo)

    var itemOption: Option[Item] = None

    itemOption = CurrencyFactory.create(knownInfo)
    if (itemOption.isDefined) return itemOption.get

    // All parser.item.equipment.Equipment has itemLevel
    if (knownInfo.itemLevel.isDefined) {
      itemOption = EquipmentFactory.create(knownInfo)
      if (itemOption.isDefined) return itemOption.get
    }

    new UnknownItem(knownInfo)
  }

  def create(clipboard: String): Item = {
    val knownInfo: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    if (knownInfo.isEmpty) {
      throw new IllegalArgumentException("couldn't parse KnownInfo from clipboard: " + clipboard)
    }
    create(knownInfo.get)
  }

  def createFromChangeJSON(changeJSON: String): Seq[Item] = {
    val knownInfosOption: Option[Seq[KnownInfo]] = ChangeParser.parseChangeJSON(changeJSON)
    if (knownInfosOption.isEmpty) {
      throw new IllegalArgumentException("couldn't parse Change JSON: " + changeJSON)
    }
    val items: Seq[Item] = knownInfosOption.get.map((knownInfo) => create(knownInfo))
    items
  }
}
