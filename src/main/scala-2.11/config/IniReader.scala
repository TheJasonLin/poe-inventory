package config

import java.io.File

import structures.{Allocation, TabType}
import org.ini4j.Ini

object IniReader {
  final val iniFilename = "config.ini"

  var ini = new Ini(new File(iniFilename))

  def getBool(section: String, key: String): Boolean = ini.get(section, key) == "true"

  def getInt(section: String, key: String): Int = ini.get(section, key).toInt

  def getString(section: String, key: String): String = ini.get(section, key)

  def getAllocation(section: String, key: String): Option[Allocation] = {
    val allocationString = ini.get(section, key)
    val allocationParts = allocationString.split(' ')

    if (allocationParts.length < 2) {
      return None
    }
    val tabIndex = allocationParts(0).toInt

    var tabType = TabType.NORMAL
    if (allocationParts(1) == "special") {
      tabType = TabType.SPECIAL
    } else if (allocationParts(1) == "normal") {
      tabType = TabType.NORMAL
    } else if (allocationParts(1) == "quad") {
      tabType = TabType.QUAD
    } else {
      return None
    }

    var region = None

    new Allocation(tabIndex, tabType, region)
  }
}
