package config

import java.io.File

import org.ini4j.Ini

object IniConfig {
  final val iniFilename = "config.ini"

  var ini = new Ini(new File(iniFilename))

  def getBool(section: String, key: String): Boolean = ini.get(section, key) == "true"
}
