package config

import java.io.File
import collection.JavaConverters._

import structures._
import org.ini4j.{Ini, Profile}

object IniReader {
  final val iniFilename = "config.ini"

  var ini = new Ini(new File(iniFilename))

  def getBool(section: String, key: String): Boolean = ini.get(section, key) == "true"

  def getInt(section: String, key: String): Int = {
    val value = ini.get(section, key)
    value.toInt
  }

  def getString(section: String, key: String): String = ini.get(section, key)

  def getAllocation(section: String, key: String): Option[Allocation] = {
    val allocationString = ini.get(section, key)
    val allocationParts = allocationString.split(' ')

    if (allocationParts.length < 2) {
      return None
    }
    val tabIndex = allocationParts(0).toInt

    val tabType = allocationParts(1) match {
      case "special" => TabType.SPECIAL
      case "normal" => TabType.NORMAL
      case "quad" => TabType.QUAD
      case _ => TabType.SPECIAL
    }

    var region: Option[Region] = None

    if (allocationParts.length == 6) {
      val regionValues = allocationParts
        .slice(2, 6)
        .map(regionValueString => regionValueString.toInt)

      val topLeft = new Position(regionValues(0), regionValues(1))
      val bottomRight = new Position(regionValues(2), regionValues(3))
      region = Option(new Region(topLeft, bottomRight))
    }

    Option(new Allocation(tabIndex, tabType, region))
  }

  def getRegion(section: String, key: String): Option[Region] = {
    val regionParts = ini.get(section, key)
      .split(' ')
      .map(regionPartString => regionPartString.toInt)

    if (regionParts.length != 4) {
      return None
    }

    val topLeft = new Position(regionParts(0), regionParts(1))
    val bottomRight = new Position(regionParts(2), regionParts(3))

    Option(new Region(topLeft, bottomRight))
  }

  def getPixelRegion(section: String, key: String): Option[PixelRegion] = {
    val regionParts = ini.get(section, key)
      .split(' ')
      .map(regionPartString => regionPartString.toInt)

    if (regionParts.length != 4) {
      return None
    }

    val topLeft = new PixelPosition(regionParts(0), regionParts(1))
    val bottomRight = new PixelPosition(regionParts(2), regionParts(3))

    Option(new PixelRegion(topLeft, bottomRight))
  }

  def getPixelPosition(section: String, key: String): Option[PixelPosition] = {
    val positionParts = ini.get(section, key)
      .split(' ')
      .map(positionPartString => positionPartString.toInt)

    if (positionParts.length != 2) {
      return None
    }

    Option(new PixelPosition(positionParts(0), positionParts(1)))
  }

  def getStrings(section: String, key: String): Seq[String] = {
    val sections: Profile.Section = ini.get(section)
    sections.getAll(key).asScala
  }
}
