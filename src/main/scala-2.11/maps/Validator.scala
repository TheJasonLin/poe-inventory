package maps

import com.typesafe.scalalogging.Logger
import parser.item.{MapItem, Mod}

object Validator {
  val log = Logger("Map.Validator")

  var issues: Set[MapIssue] = _
  var map: MapItem = _
  def getIssues(map: MapItem): Set[MapIssue] = {
    issues = Set()
    this.map = map

    if (!map.identified) {
      issues += MapIssue.UNIDENTIFIED
      return issues
    }

    checkQuality()
    checkValues()
    checkMods()

    issues
  }

  private def checkQuality(): Unit = {
    val quality = map.quality.getOrElse(0)
    if (quality < MapRequirements.minQuality) {
      log.debug(s"Quality: $quality < ${MapRequirements.minQuality}")
      issues += MapIssue.QUALITY_LOW
    }
  }

  private def checkValues(): Unit = {
    val iiq = map.itemQuantity
    val iir = map.itemRarity
    val packSize = map.packSize

    if (iiq < MapRequirements.minItemQuantity) {
      log.debug(s"IIQ: $iiq < ${MapRequirements.minItemQuantity}")
      issues += MapIssue.BAD_PROPERTIES
    } else if (iir < MapRequirements.minItemRarity) {
      log.debug(s"BadIIR: $iir < ${MapRequirements.minItemRarity}")
      issues += MapIssue.BAD_PROPERTIES
    } else if (packSize < MapRequirements.minPackSize) {
      log.debug(s"PackSize: $packSize")
      issues += MapIssue.BAD_PROPERTIES
    }
  }

  private def checkMods(): Unit = {
    val mods = map.explicits ++ map.implicits
    val foundBlacklistMod = findBlacklistMod(mods)
    if (foundBlacklistMod.isDefined) {
      issues += MapIssue.BAD_MODS
      log.debug("Bad Mod: " + foundBlacklistMod.get.text)
    }
  }

  private def findBlacklistMod(mods: Seq[Mod]): Option[Mod] = {
    val matchOption = mods.find((mod: Mod) => {
      MapRequirements.blacklistMods.contains(mod.text)
    })

    matchOption
  }
}
