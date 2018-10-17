import config.{IniReader, ScreenResolution}
import structures._

object Config {
  val SAFE_MODE: Boolean = IniReader.getBool("general", "safeMode")
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

  val SEPARATE_REGAL: Boolean = IniReader.getBool("general", "separateRegalRecipe")
  val CALIBRATION_NORMAL_TAB_INDEX: Option[Int] = Option(IniReader.getInt("developer", "calibrationNormalTabIndex"))
  val CALIBRATION_QUAD_TAB_INDEX: Option[Int] = Option(IniReader.getInt("developer", "calibrationQuadTabIndex"))

  val CURRENCY_ALLOCATION: Allocation = IniReader.getAllocation("allocation", "currencyAllocation").get
  val ESSENCE_ALLOCATION: Allocation = IniReader.getAllocation("allocation", "essenceAllocation").get
  val DIVINATION_ALLOCATION: Allocation = IniReader.getAllocation("allocation", "divinationAllocation").get
  val SPECIAL_MAP_ALLOCATION: Allocation = IniReader.getAllocation("allocation", "mapAllocation").get
  val FRAGMENT_ALLOCATION: Allocation = IniReader.getAllocation("allocation", "fragmentAllocation").get
  val RUN_MAP_ALLOCATION: Allocation = IniReader.getAllocation("allocation", "runAllocation").get
  val DUMP_ALLOCATION: Allocation = IniReader.getAllocation("allocation", "dumpAllocation").get
  val QUALITY_FLASK_ALLOCATION: Allocation = IniReader.getAllocation("allocation", "qualityFlaskAllocation").get
  val QUALITY_GEM_ALLOCATION: Allocation = IniReader.getAllocation("allocation", "qualityGemAllocation").get

  /**
    * Full Set Recipe
    */

  val BOOT_ALLOCATION: Allocation = createGearAllocation("chaosGearAllocation", "fullSetQuadAllocation", "bootAllocation").get
  val GLOVE_ALLOCATION: Allocation = createGearAllocation("chaosGearAllocation", "fullSetQuadAllocation", "gloveAllocation").get
  val HELMET_ALLOCATION: Allocation = createGearAllocation("chaosGearAllocation", "fullSetQuadAllocation", "helmetAllocation").get
  val BODY_ALLOCATION: Allocation = createGearAllocation("chaosGearAllocation", "fullSetQuadAllocation", "bodyAllocation").get
  val WEAPON_ALLOCATION: Allocation = createGearAllocation("chaosGearAllocation", "fullSetQuadAllocation", "weaponAllocation").get
  val RING_ALLOCATION: Allocation = createGearAllocation("chaosJewelryAllocation", "fullSetNormalAllocation", "bootAllocation").get
  val AMULET_ALLOCATION: Allocation = createGearAllocation("chaosJewelryAllocation", "fullSetNormalAllocation", "bootAllocation").get
  val BELT_ALLOCATION: Allocation = createGearAllocation("chaosJewelryAllocation", "fullSetNormalAllocation", "bootAllocation").get

  val BOOT_75_ALLOCATION: Allocation = createGearAllocation("regalGearAllocation", "fullSetNormalAllocation", "bootAllocation").get
  val GLOVE_75_ALLOCATION: Allocation = createGearAllocation("regalGearAllocation", "fullSetNormalAllocation", "gloveAllocation").get
  val HELMET_75_ALLOCATION: Allocation = createGearAllocation("regalGearAllocation", "fullSetNormalAllocation", "helmetAllocation").get
  val BODY_75_ALLOCATION: Allocation = createGearAllocation("regalGearAllocation", "fullSetNormalAllocation", "bodyAllocation").get
  val WEAPON_75_ALLOCATION: Allocation = createGearAllocation("regalGearAllocation", "fullSetNormalAllocation", "weaponAllocation").get
  val RING_75_ALLOCATION: Allocation = createGearAllocation("regalJewelryAllocation", "fullSetNormalAllocation", "bootAllocation").get
  val AMULET_75_ALLOCATION: Allocation = createGearAllocation("regalJewelryAllocation", "fullSetNormalAllocation", "bootAllocation").get
  val BELT_75_ALLOCATION: Allocation = createGearAllocation("regalJewelryAllocation", "fullSetNormalAllocation", "bootAllocation").get

  def createGearAllocation(allocationSection: String, setTypeSection: String, key: String): Option[Allocation] = {
    val setTypeAllocationOption = IniReader.getAllocation("allocation", allocationSection)
    if (setTypeAllocationOption.isEmpty) {
      return None
    }

    val region = IniReader.getRegion(setTypeSection, key)
    if (region.isEmpty) {
      return None
    }

    val setTypeAllocation = setTypeAllocationOption.get
    Option(new Allocation(setTypeAllocation.tabIndex, setTypeAllocation.tabType, region))
  }

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
