package parser

import constants.{Clipboard, Rarity, RarityFactory}
import parser.item.StackSize
import parser.knowninfo.{KnownInfo, MapInfo}
import com.typesafe.scalalogging.Logger

object ClipboardParser {
  val log = Logger("ClipboardParser")
  def parseKnownInfo(clipboard: String): Option[KnownInfo] = {
    if (!isValid(clipboard)) {
      return None
    }

    val rarity = parseRarity(clipboard)
    val typeLine = parseTypeLine(clipboard)
    val name = parseName(clipboard)
    val itemLevel = parseItemLevel(clipboard)
    val identified = parseIdentified(clipboard)
    val quality = parsePercentageAttribute(clipboard, "Quality")

    val knownInfo = new KnownInfo(typeLine, rarity)

    knownInfo.name = name
    knownInfo.itemLevel = itemLevel
    knownInfo.identified = Option(identified)
    knownInfo.quality = quality
    if (knownInfo.isTalisman) {
      knownInfo.talismanTier = parseTalismanTier(clipboard)
    }

    // from now on, use sections
    val sections: Seq[String] = getSections(clipboard)
    knownInfo.stackSize = parseStackSize(sections)
    knownInfo.corrupted = isCorrupted(sections)
    if (knownInfo.isMap) knownInfo.mapInfo = parseMapInfo(sections)

    // set mods last so that we have maximum info to work with
    setMods(sections, knownInfo)

    Option(knownInfo)
  }

  private def isValid(clipboard: String): Boolean = {
    // should be at least 3 lines
    clipboard.split('\n').length > 2
  }

  private def parseRarity(clipboard: String): Rarity = {
    val spaceIndex = clipboard.indexOf(' ')
    val newLineIndex = clipboard.indexOf('\n')
    val rarityString = clipboard.substring(spaceIndex + 1, newLineIndex)
    val rarityOptional: Option[Rarity] = RarityFactory.getByString(rarityString)
    if (rarityOptional.isDefined) {
      rarityOptional.get
    } else {
      throw new IllegalArgumentException("rarity couldn't be parsed")
    }
  }

  private def parseTypeLine(clipboard: String): String = {
    val lines: Array[String] = clipboard.split('\n')
    var typeLineIndex = 1
    if (hasName(clipboard)) {
      typeLineIndex = 2
    }
    lines(typeLineIndex)
  }

  private def parseName(clipboard: String): Option[String] = {
    val lines: Array[String] = clipboard.split('\n')
    if (!hasName(clipboard)) {
      return None
    }
    Option(lines(1))
  }

  private def parseItemLevel(clipboard: String): Option[Int] = {
    parseNumericAttribute(clipboard, "Item Level")
  }

  private def hasName(clipboard: String): Boolean = {
    val lines: Array[String] = clipboard.split('\n')
    lines(2) != Clipboard.Divider
  }

  private def parseMapInfo(sections: Seq[String]): Option[MapInfo] = {
    val sectionIndex = findSectionIndex(sections, "Map Tier: ")
    if (sectionIndex < 0) {
      None
    }

    val section = sections(sectionIndex)
    val tier = parseNumericAttribute(section, "Map Tier")
    if (tier.isEmpty) None

    val itemQuantity = parsePercentageAttribute(section, "Item Quantity")
    val itemRarity = parsePercentageAttribute(section, "Item Rarity")
    val packSize = parsePercentageAttribute(section, "Monster Pack Size")

    Option(MapInfo(tier.get, itemQuantity.getOrElse(0), itemRarity.getOrElse(0), packSize.getOrElse(0)))
  }

  private def getLabelValue(searchText: String, label: String): Option[String] = {
    val fullLabel = label + ": "
    val labelStartIndex = searchText.indexOf(fullLabel)
    if(labelStartIndex < 0) {
      None
    } else {
      val labelLength = fullLabel.length()
      val valueStartIndex = labelStartIndex + labelLength
      var valueEndIndex = searchText.indexOf('\n', valueStartIndex)
      if (valueEndIndex < 0) {
        valueEndIndex = searchText.length
      }
      val valueString = searchText.substring(valueStartIndex, valueEndIndex)
      Option(valueString)
    }
  }

  private def parseNumericAttribute(searchText: String, label: String): Option[Int] = {
    val valueStringOption = getLabelValue(searchText, label)
    if (valueStringOption.isEmpty) {
      return None
    }
    val cleanValueString = cleanNumericAttribute(valueStringOption.get)
    val value = Integer.parseInt(cleanValueString)
    Option(value)
  }

  private def cleanNumericAttribute(dirtyValue: String): String = {
    var cleanValue = dirtyValue
    if (dirtyValue.contains("(augmented)")) {
      cleanValue = dirtyValue.replace("(augmented)", "")
    }
    cleanValue = cleanValue.trim()
    cleanValue
  }

  /**
    *
    * @param searchText
    * @param label for example "Quality"
    * @return
    */
  private def parsePercentageAttribute(searchText: String, label: String): Option[Int] = {
    val valueStringOption = getLabelValue(searchText, label)
    if (valueStringOption.isEmpty) {
      return None
    }
    val valueString = valueStringOption.get
    val signIndex: Int = 0
    val valueIndex: Int = signIndex + 1
    val endIndex: Int = valueString.indexOf("%", valueIndex)
    if(endIndex < 0) {
      None
    } else {
      val numberString = valueString.substring(valueIndex, endIndex)
      val magnitude = Integer.parseInt(numberString)
      if (valueString.indexOf(signIndex) == '-') {
        Option(-1 * magnitude)
      } else {
        Option(magnitude)
      }
    }
  }

  private def parseIdentified(clipboard: String): Boolean = {
    !clipboard.contains("Unidentified")
  }

  private def parseTalismanTier(clipboard: String): Option[Int] = {
    parseNumericAttribute(clipboard, "Talisman Tier")
  }

  /**
    * This should be called after everything else is populated so we have maximum amounts
    * of information to work with
    * @param sections
    * @param knownInfo
    * @return
    */
  private def setMods(sections: Seq[String], knownInfo: KnownInfo): Unit = {
    if (
      knownInfo.isGem ||
      knownInfo.isDivinationCard ||
      knownInfo.isLeaguestone ||
      knownInfo.isEssence ||
      knownInfo.isBasicCurrency
    ) {
      // do nothing
      return
    }
    // assume it's equipment and parse mods
    val trimmedSections = removeNonModSectionsBelowItemLevel(sections, knownInfo)
    // assume mods are immediately below item level
    val itemLevelSectionIndex = getItemLevelSectionIndex(trimmedSections)
    val sectionsAfterItemLevel = trimmedSections.length - itemLevelSectionIndex - 1
    if (itemLevelSectionIndex < 0 || sectionsAfterItemLevel < 1) {
      // no mods to be found
      return
    }
    if (knownInfo.rarity == Rarity.NORMAL) {
      // only implicit
      knownInfo.implicits = Option(sectionToModStrings(trimmedSections.last))
      return
    }
    // only magic / rare / unique, which we assume always have explicits
    if (sectionsAfterItemLevel == 1) {
      // must be explicits
      knownInfo.explicits = Option(sectionToModStrings(trimmedSections.last))
    } else if (sectionsAfterItemLevel == 2) {
      knownInfo.implicits = Option(sectionToModStrings(trimmedSections(itemLevelSectionIndex + 1)))
      knownInfo.explicits = Option(sectionToModStrings(trimmedSections.last))
    } else {
      throw new IllegalArgumentException("item has too many sections after item level to reliably parse mods")
    }
  }

  private def sectionToModStrings(section: String): Seq[String] = {
    section.split("\\n")
  }

  /**
    * some items have instructions / flavor text / corrupted that make it hard to parse mods
    * removing everything below the mods makes it easier to discern implicits from explicits
    */
  private def removeNonModSectionsBelowItemLevel(sections: Seq[String], knownInfo: KnownInfo): Seq[String] = {
    var newSections = sections
    if (knownInfo.corrupted) {
      newSections = newSections.dropRight(1)
    }
    if (
      knownInfo.isMap ||
      knownInfo.AssumeEquipment.isFlask ||
      knownInfo.AssumeEquipment.isJewel
    ) {
      newSections = newSections.dropRight(1)
    }
    if (knownInfo.rarity == Rarity.UNIQUE) {
      // assume that uniques always have flavor text at the end
      newSections = newSections.dropRight(1)
    }
    if (knownInfo.isTalisman) {
      // remove talisman tier, because it comes after item level
      newSections = newSections.filter((section) => !section.contains("Talisman Tier: "))
    }
    newSections
  }

  private def getItemLevelSectionIndex(sections: Seq[String]) = findSectionIndex(sections, "Item Level: ")

  private def setMapMods(sections: Seq[String], knownInfo: KnownInfo): Unit = {
    val itemLevelSectionIndex = findSectionIndex(sections, "Item Level: ")
    val mapDescriptionIndex = findSectionIndex(sections, "Travel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once.")

    if (itemLevelSectionIndex == -1 || mapDescriptionIndex == -1 || (itemLevelSectionIndex + 1) >= mapDescriptionIndex) {
      return
    }

    // assume that no maps have implicits

    // assume explicits are always before description
    val explicitModSection = sections(mapDescriptionIndex - 1)
    val modStrings = explicitModSection.split("\\n")
    knownInfo.explicits = Option(modStrings)
  }

  private def getSections(clipboard: String): Seq[String] = {
    clipboard.split(Clipboard.Divider).map((section: String) => section.trim)
  }

  private def findSectionIndex(sections: Seq[String], needle: String): Int = {
    for (i <- sections.indices) {
      if (sections(i).contains(needle)) {
        return i
      }
    }
    -1
  }

  private def parseStackSize(sections: Seq[String]): Option[StackSize] = {
    val stackSizeLabel = "Stack Size: "
    val matchingSections: Seq[String] = sections.filter((section: String) => {
      val isLongEnough = section.length > stackSizeLabel.length
      if (!isLongEnough) {
        false
      } else {
        val hasLabel = section.substring(0, stackSizeLabel.length()) == stackSizeLabel
        val hasDivider = section.contains("/")
        hasLabel && hasDivider
      }
    })
    val length = matchingSections.length
    if (length != 1) {
      if (length > 1) {
        log.warn(s"found more than 1 stack size label: $length")
      }
      return None
    }
    val section = matchingSections.head

    // TODO replace with regex
//    val pattern = (stackSizeLabel + "([0-9]+)/([0-9]+)([\na-zA-Z0-9:]*)").r
//    val pattern(sizeString, maxString, _) =
    var content = section.substring(stackSizeLabel.length)

    // trim trailing lines
    val newlineIndex = content.indexOf('\n')
    if (newlineIndex != -1) {
      content = content.substring(0, newlineIndex)
    }
    val valueStrings = content.split("/")

    if (valueStrings.length != 2) {
      log.warn(s"found more than 2 sections: $section")
      return None
    }

    def cleanIntString(value: String): String = value.replace(",", "")
    val size = Integer.parseInt(cleanIntString(valueStrings(0)))
    val max = Integer.parseInt(cleanIntString(valueStrings(1)))

    Option(StackSize(size, max))
  }

  private def isCorrupted(sections: Seq[String]): Boolean = {
    sections.last.trim.equals("Corrupted")
  }
}
