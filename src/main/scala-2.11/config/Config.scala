package config

import containers.Allocation
import screen.{PixelPosition, PixelRegion}

object Config {
  val TAB_CHANGE_DELAY: Int = IniReader.getInt("general", "tabChangeDelay")
  val QUICK_SLEEP: Int = IniReader.getInt("general", "quickSleep")
  // the amount of time to let the user let go of the hotkey
  val USER_KEY_RELEASE_DELAY: Int = IniReader.getInt("general", "userKeyReleaseDelay")

  val RESOLUTION_STRING: String = IniReader.getString("general", "resolution")
  val RESOLUTION: ScreenResolution = RESOLUTION_STRING match {
    case "1080p" => ScreenResolution.P1080
    case "1200p" => ScreenResolution.P1200
    case "1440p" => ScreenResolution.P1440
    case _ => ScreenResolution.P1080
  }

  val CALIBRATION_NORMAL_TAB_INDEX: Option[Int] = Option(IniReader.getInt("developer", "calibrationNormalTabIndex"))
  val CALIBRATION_QUAD_TAB_INDEX: Option[Int] = Option(IniReader.getInt("developer", "calibrationQuadTabIndex"))

  val CURRENCY_ALLOCATION: Option[Allocation] = IniReader.getAllocation("allocation", "currencyAllocation")
  val ESSENCE_ALLOCATION: Option[Allocation] = IniReader.getAllocation("allocation", "essenceAllocation")
  val DIVINATION_ALLOCATION: Option[Allocation] = IniReader.getAllocation("allocation", "divinationAllocation")
  val MAP_ALLOCATION: Option[Allocation] = IniReader.getAllocation("allocation", "mapAllocation")
  val FRAGMENT_ALLOCATION: Option[Allocation] = IniReader.getAllocation("allocation", "fragmentAllocation")
  val DELVE_ALLOCATION: Option[Allocation] = IniReader.getAllocation("allocation", "delveAllocation")
  val MISC_ALLOCATION: Option[Allocation] = IniReader.getAllocation("allocation", "miscAllocation")
  val RUN_ALLOCATION: Option[Allocation] = IniReader.getAllocation("allocation", "runAllocation")
  val DUMP_ALLOCATION: Option[Allocation] = IniReader.getAllocation("allocation", "dumpAllocation")
  val QUICK_SELL_ALLOCATION: Option[Allocation] = IniReader.getAllocation("allocation", "quickSellAllocation")

  val RESOLUTION_SECTION: String = RESOLUTION match {
    case ScreenResolution.P1080 => "resolution1080p"
    case ScreenResolution.P1200 => "resolution1200p"
    case ScreenResolution.P1440 => "resolution1440p"
  }

  val NORMAL_TAB_REGION_COORDS: PixelRegion = IniReader.getPixelRegion(RESOLUTION_SECTION, "normalTabCoords").get
  val QUAD_TAB_REGION_COORDS: PixelRegion = IniReader.getPixelRegion(RESOLUTION_SECTION, "quadTabCoords").get
  val INVENTORY_REGION_COORDS: PixelRegion = IniReader.getPixelRegion(RESOLUTION_SECTION, "inventoryCoords").get
  val CENTER: PixelPosition = IniReader.getPixelPosition(RESOLUTION_SECTION, "center").get
  val NORMAL_TAB_CELL_RADIUS: Int = IniReader.getInt(RESOLUTION_SECTION, "normalTabCellRadius")
  val QUAD_TAB_CELL_RADIUS: Int = IniReader.getInt(RESOLUTION_SECTION, "quadTabCellRadius")

  val NORMAL_TAB_WIDTH: Int = IniReader.getInt("tabConstants", "normalTabWidth")
  val NORMAL_TAB_HEIGHT: Int = IniReader.getInt("tabConstants", "normalTabHeight")

  val QUAD_TAB_WIDTH: Int = IniReader.getInt("tabConstants", "quadTabWidth")
  val QUAD_TAB_HEIGHT: Int = IniReader.getInt("tabConstants", "quadTabHeight")

  val INVENTORY_HEIGHT: Int = IniReader.getInt("tabConstants", "inventoryWidth")
  val INVENTORY_WIDTH: Int = IniReader.getInt("tabConstants", "inventoryHeight")
  val INVENTORY_CELL_RADIUS: Int = NORMAL_TAB_CELL_RADIUS
}
