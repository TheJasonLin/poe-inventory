package items

import items.currency.{Currency, CurrencyFactory, Essence}
import items.equipment.{Equipment, EquipmentFactory}
import structures.Position


object ItemFactory {
  def create(position: Position, clipboard: String): Item = {
    val rarity = parseRarity(clipboard)
    val base = parseBase(clipboard)
    val name = parseName(clipboard)
    create(position, rarity, base, name)
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

  private def create(position: Position, rarity: String, base: String, name: Option[String]): Item = {
    if (base == "Gem") {
      return new Gem(position, rarity, base, name)
    } else if (base == "Divination Card") {
      return new Divination(position, rarity, base, name)
    }
    var itemOption: Option[Item] = None

    itemOption = CurrencyFactory.create(position, rarity, base, name)
    if(itemOption.isDefined) return itemOption.get

    itemOption = EquipmentFactory.create(position, rarity, base, name)
    if(itemOption.isDefined) return itemOption.get

    new UnknownItem(position, rarity, base, name)
  }
}
