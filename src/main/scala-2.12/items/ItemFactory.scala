package items

import items.currency.{Currency, CurrencyFactory, Essence}
import items.equipment.{Equipment, EquipmentFactory}

object ItemFactory {
  private def isValid(clipboard: String): Boolean = {
    // should be at least 3 lines
    clipboard.split('\n').length > 2
  }

  def create(clipboard: String): Option[Item] = {
    if(!isValid(clipboard)) {
      return None
    }

    val rarity = parseRarity(clipboard)
    val base = parseBase(clipboard)
    val name = parseName(clipboard)

    Option(create(rarity, base, name))
  }

  def parseRarity(clipboard: String): String = {
    val spaceIndex = clipboard.indexOf(' ')
    val newLineIndex = clipboard.indexOf('\n')
    clipboard.substring(spaceIndex + 1, newLineIndex)
  }

  def parseBase(clipboard: String): String = {
    val lines: Array[String] = clipboard.split('\n')
    var baseLineIndex = 1
    if (hasName(clipboard)) {
      baseLineIndex = 2
    }
    lines(baseLineIndex)
  }

  def parseName(clipboard: String): Option[String] = {
    val lines: Array[String] = clipboard.split('\n')
    if (!hasName(clipboard)) {
      return None
    }
    Option(lines(1))
  }

  def hasName(clipboard: String): Boolean = {
    val lines: Array[String] = clipboard.split('\n')
    lines(2) != "--------"
  }

  private def create(rarity: String, base: String, name: Option[String]): Item = {
    if (rarity == "Gem") {
      return new Gem(rarity, base, name)
    } else if (rarity == "Divination Card") {
      return new DivinationCard(rarity, base, name)
    }
    var itemOption: Option[Item] = None

    itemOption = CurrencyFactory.create(rarity, base, name)
    if(itemOption.isDefined) return itemOption.get

    itemOption = EquipmentFactory.create(rarity, base, name)
    if(itemOption.isDefined) return itemOption.get

    new UnknownItem(rarity, base, name)
  }
}
