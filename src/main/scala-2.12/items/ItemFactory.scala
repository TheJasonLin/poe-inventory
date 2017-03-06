package items

import java.awt.datatransfer.Clipboard

import items.currency.{Currency, CurrencyFactory, Essence}
import items.equipment.{Equipment, EquipmentFactory}

object ItemFactory {
  private def isValid(clipboard: String): Boolean = {
    // should be at least 3 lines
    clipboard.split('\n').length > 2
  }

  /**
    * Handles most of the parsing
    * @param clipboard
    * @return
    */
  def create(clipboard: String): Option[Item] = {
    if(!isValid(clipboard)) {
      return None
    }

    val rarity = parseRarity(clipboard)
    val base = parseBase(clipboard)
    val name = parseName(clipboard)
    val itemLevel = parseItemLevel(clipboard)
    val identified = parseIdentified(clipboard)
    val quality = parseQuality(clipboard)

    Option(create(clipboard, rarity, base, name, itemLevel, identified, quality))
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

  def parseItemLevel(clipboard: String): Option[Int] = {
    parseNumericAttribute(clipboard, "Item Level: ")
  }

  /**
    * Parses the quality. Defaults to 0 if not found.
    * @param clipboard
    * @return
    */
  def parseQuality(clipboard: String): Int = {
    val label = "Quality: +"
    val labelIndex: Int = clipboard.indexOf(label)
    if(labelIndex < 0) {
      0
    } else {
      val valueIndex: Int = labelIndex + label.length
      val endIndex: Int = clipboard.indexOf("%", valueIndex)
      if(endIndex < 0) {
        0
      } else {
        val qualityString = clipboard.substring(valueIndex, endIndex)
        Integer.parseInt(qualityString)
      }
    }
  }

  private def hasName(clipboard: String): Boolean = {
    val lines: Array[String] = clipboard.split('\n')
    lines(2) != "--------"
  }

  /**
    * Handles less parsing and more item creation
    * @param clipboard
    * @param rarity
    * @param base
    * @param nameOption
    * @param itemLevelOption
    * @return
    */
  private def create(clipboard: String, rarity: String, base: String, nameOption: Option[String], itemLevelOption: Option[Int], identified: Boolean, quality: Int): Item = {
    if (rarity == "Gem") {
      return new Gem(rarity, base, nameOption, quality)
    } else if (rarity == "Divination Card") {
      return new DivinationCard(rarity, base, nameOption)
    } else if (base.contains("Map")) {
      val mapTierOption = parseMapTier(clipboard)
      if(mapTierOption.isDefined && itemLevelOption.isDefined) {
        return new MapItem(rarity, base, nameOption, itemLevelOption.get, identified, quality, mapTierOption.get)
      }
    } else if (base.contains("Leaguestone")) {
      return new Leaguestone(rarity, base, nameOption, itemLevelOption.get, identified)
    }
    var itemOption: Option[Item] = None

    itemOption = CurrencyFactory.create(rarity, base, nameOption)
    if(itemOption.isDefined) return itemOption.get

    // All Equipment has itemLevel
    if(itemLevelOption.isDefined) {
      itemOption = EquipmentFactory.create(rarity, base, nameOption, itemLevelOption.get, identified, quality)
      if(itemOption.isDefined) return itemOption.get
    }

    new UnknownItem(rarity, base, nameOption)
  }

  private def parseMapTier(clipboard: String): Option[Int] = {
    parseNumericAttribute(clipboard, "Map Tier: ")
  }

  private def parseNumericAttribute(clipboard: String, label: String): Option[Int] = {
    val labelStartIndex = clipboard.indexOf(label)
    if(labelStartIndex < 0) {
      None
    } else {
      val labelLength = label.length()
      val valueStartIndex = labelStartIndex + labelLength
      val valueEndIndex = clipboard.indexOf('\n', valueStartIndex)
      val valueText: String = clipboard.substring(valueStartIndex, valueEndIndex)
      val value = Integer.parseInt(valueText)
      Option(value)
    }
  }

  private def parseIdentified(clipboard: String): Boolean = {
    !clipboard.contains("Unidentified")
  }
}
