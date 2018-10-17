import config.{IniReader, ScreenResolution}
import structures.{Allocation, Position, Region, TabType}
import structures.TabType._

object Config {
  val SAFE_MODE: Boolean = IniReader.getBool("general", "safeMode")
  val TAB_CHANGE_DELAY: Int = IniReader.getInt("general", "tabChangeDelay")
  val QUICK_SLEEP: Int = IniReader.getInt("general", "quickSleep")
  // the amount of time to let the user let go of the hotkey
  val USER_KEY_RELEASE_DELAY: Int = IniReader.getInt("general", "userKeyReleaseDelay")

  val RESOLUTION_STRING = IniReader.getString("general", "resolution")
  var RESOLUTION: ScreenResolution = ScreenResolution.P1080
  if (RESOLUTION_STRING == "1080p") {
    RESOLUTION = ScreenResolution.P1080
  } else if (RESOLUTION_STRING == "1200p") {
    RESOLUTION = ScreenResolution.P1200
  } else if (RESOLUTION_STRING == "1440p") {
    RESOLUTION = ScreenResolution.P1440
  }

  val SEPARATE_REGAL: Boolean = IniReader.getBool("general", "separateRegalRecipe")
  val CALIBRATION_NORMAL_TAB_INDEX: Option[Int] = Option(IniReader.getInt("general", "calibrationNormalTabIndex"))
  val CALIBRATION_QUAD_TAB_INDEX: Option[Int] = Option(IniReader.getInt("general", "calibrationQuadTabIndex"))

  /**
    * Ignore this. This is a helper
    */
  private var tabIndex: Int = -1

  /**
    * idx() generates a sequential number, allowing easy rearrangement
    */
  val CURRENCY_TAB: Int = idx()
  val ESSENCE_TAB: Int = idx()
  val DIVINATION_TAB: Int = idx()
  val MAP_TAB: Int = idx()
  val FRAGMENT_TAB: Int = idx()
  val RUN_TAB: Int = idx()
  val CHAOS_GEAR_60_TAB: Int = idx()
  val CHAOS_JEWELRY_60_TAB: Int = idx()
  val QUALITY_FLASK_TAB: Int = idx()
  val QUALITY_GEM_TAB: Int = QUALITY_FLASK_TAB
  val DUMP_TAB: Int = idx()
  /**
    * The following tabs are defined, but not used and in a catch all tab
    */
  val CATCH_ALL: Int = idx()
  val LEAGUESTONE_TAB: Int = CATCH_ALL
  val TALISMAN_TAB: Int = CATCH_ALL
  val REGAL_GEAR_75_TAB: Int = CATCH_ALL
  val REGAL_JEWELRY_75_TAB: Int = CATCH_ALL

  val CURRENCY_ALLOCATION: Allocation = a(CURRENCY_TAB, TabType.SPECIAL)
  val ESSENCE_ALLOCATION: Allocation = a(ESSENCE_TAB, TabType.SPECIAL)
  val DIVINATION_ALLOCATION: Allocation = a(DIVINATION_TAB, TabType.SPECIAL)
  val SPECIAL_MAP_ALLOCATION: Allocation = a(MAP_TAB, TabType.SPECIAL)
  val FRAGMENT_ALLOCATION: Allocation = a(FRAGMENT_TAB, TabType.SPECIAL)
  val DUMP_ALLOCATION: Allocation = a(DUMP_TAB, TabType.QUAD, r(0, 0, 23, 23))

  val RUN_MAP_ALLOCATION: Allocation = a(RUN_TAB, TabType.NORMAL, r(0, 0, 11, 11))

  val QUALITY_FLASK_ALLOCATION: Allocation = a(QUALITY_FLASK_TAB, TabType.NORMAL, r(0, 0, 11, 5))
  val QUALITY_GEM_ALLOCATION: Allocation = a(QUALITY_FLASK_TAB, TabType.NORMAL, r(0, 6, 11, 11))

  val MISC_ALLOCATION: Map[String, Allocation] = collection.immutable.HashMap(
//    "Offering to the Goddess" -> a(FRAGMENT_TAB, NORMAL, r(0, 0, 11, 11)),
//    "Sacrifice at Dusk" -> a(FRAGMENT_TAB, NORMAL, r(0, 0, 11, 11)),
//    "Sacrifice at Midnight" -> a(FRAGMENT_TAB, NORMAL, r(0, 0, 11, 11)),
//    "Sacrifice at Dawn" -> a(FRAGMENT_TAB, NORMAL, r(0, 0, 11, 11)),
//    "Sacrifice at Noon" -> a(FRAGMENT_TAB, NORMAL, r(0, 0, 11, 11)),
//    "Divine Vessel" -> a(FRAGMENT_TAB, NORMAL, r(0, 0, 11, 11))
  )

  val BAD_LEAGUESTONE_COLUMN = 11
  val GG_LEAGUESTONE_COLUMN = 10
  val EXP_LEAGUESTONE_COLUMN = 4
  val LOOT_LEAGUESTONE_COLUMN = 3
  val LEAGUESTONE_ALLOCATION: Map[String, Allocation] = collection.immutable.HashMap(
    "Ambush" -> a(LEAGUESTONE_TAB, NORMAL, r(0, 0, 11, 0)),
    "Anarchy" -> a(LEAGUESTONE_TAB, NORMAL, r(0, 1, 11, 1)),
    "Beyond" -> a(LEAGUESTONE_TAB, NORMAL, r(0, 2, 11, 2)),
    "Bloodlines" -> a(LEAGUESTONE_TAB, NORMAL, r(0, EXP_LEAGUESTONE_COLUMN, 11, EXP_LEAGUESTONE_COLUMN)),
    "Breach" -> a(LEAGUESTONE_TAB, NORMAL, r(0, EXP_LEAGUESTONE_COLUMN, 11, EXP_LEAGUESTONE_COLUMN)),
    "Domination" -> a(LEAGUESTONE_TAB, NORMAL, r(0, 5, 11, 5)),
    "Essence" -> a(LEAGUESTONE_TAB, NORMAL, r(0, 6, 11, 6)),
    "Invasion" -> a(LEAGUESTONE_TAB, NORMAL, r(0, 7, 11, 7)),
    "Nemesis" -> a(LEAGUESTONE_TAB, NORMAL, r(0, GG_LEAGUESTONE_COLUMN, 11, GG_LEAGUESTONE_COLUMN)),
    "Onslaught" -> a(LEAGUESTONE_TAB, NORMAL, r(0, LOOT_LEAGUESTONE_COLUMN, 11, LOOT_LEAGUESTONE_COLUMN)),
    "Perandus" -> a(LEAGUESTONE_TAB, NORMAL, r(0, 8, 11, 8)),
    "Prophecy" -> a(LEAGUESTONE_TAB, NORMAL, r(0, BAD_LEAGUESTONE_COLUMN, 11, BAD_LEAGUESTONE_COLUMN)),
    "Rampage" -> a(LEAGUESTONE_TAB, NORMAL, r(0, BAD_LEAGUESTONE_COLUMN, 11, BAD_LEAGUESTONE_COLUMN)),
    "Talisman" -> a(LEAGUESTONE_TAB, NORMAL, r(0, 9, 11, 9)),
    "Tempest" -> a(LEAGUESTONE_TAB, NORMAL, r(0, BAD_LEAGUESTONE_COLUMN, 11, BAD_LEAGUESTONE_COLUMN)),
    "Torment" -> a(LEAGUESTONE_TAB, NORMAL, r(0, BAD_LEAGUESTONE_COLUMN, 11, BAD_LEAGUESTONE_COLUMN)),
    "Warbands" -> a(LEAGUESTONE_TAB, NORMAL, r(0, BAD_LEAGUESTONE_COLUMN, 11, BAD_LEAGUESTONE_COLUMN))
  )

  /**
    * Specify where you want each tier of maps allocated
    */
  val MAP_ALLOCATION = collection.immutable.HashMap(
    1 -> a(MAP_TAB, QUAD, r(0, 0, 23, 0)),
    2 -> a(MAP_TAB, QUAD, r(0, 1, 23, 1)),
    3 -> a(MAP_TAB, QUAD, r(0, 2, 23, 2)),
    4 -> a(MAP_TAB, QUAD, r(0, 3, 23, 3)),
    5 -> a(MAP_TAB, QUAD, r(0, 4, 23, 4)),
    6 -> a(MAP_TAB, QUAD, r(0, 5, 23, 5)),
    7 -> a(MAP_TAB, QUAD, r(0, 6, 23, 6)),
    8 -> a(MAP_TAB, QUAD, r(0, 7, 23, 7)),
    9 -> a(MAP_TAB, QUAD, r(0, 8, 23, 8)),
    10 -> a(MAP_TAB, QUAD, r(0, 9, 23, 9)),
    11 -> a(MAP_TAB, QUAD, r(0, 10, 23, 11)),
    12 -> a(MAP_TAB, QUAD, r(0, 12, 23, 13)),
    13 -> a(MAP_TAB, QUAD, r(0, 14, 23, 15)),
    14 -> a(MAP_TAB, QUAD, r(0, 16, 23, 17)),
    15 -> a(MAP_TAB, QUAD, r(0, 18, 23, 18)),
    16 -> a(MAP_TAB, QUAD, r(0, 19, 23, 19))
  )

  val TALISMAN_ALLOCATION = collection.immutable.HashMap(
    1 -> a(TALISMAN_TAB, NORMAL, r(0, 0, 11, 4)),
    2 -> a(TALISMAN_TAB, NORMAL, r(0, 5, 11, 8)),
    3 -> a(TALISMAN_TAB, NORMAL, r(0, 9, 11, 10)),
    4 -> a(TALISMAN_TAB, NORMAL, r(0, 11, 11, 11))
  )

  val BOOT_ALLOCATION: Allocation = a(CHAOS_GEAR_60_TAB, QUAD, r(0, 0, 23, 3))
  val GLOVE_ALLOCATION: Allocation = a(CHAOS_GEAR_60_TAB, QUAD, r(0, 4, 23, 7))
  val HELMET_ALLOCATION: Allocation = a(CHAOS_GEAR_60_TAB, QUAD, r(0, 8, 23, 11))
  val BODY_ALLOCATION: Allocation = a(CHAOS_GEAR_60_TAB, QUAD, r(0, 12, 23, 15))
  val WEAPON_ALLOCATION: Allocation = a(CHAOS_GEAR_60_TAB, QUAD, r(0, 16, 23, 23))
  val RING_ALLOCATION: Allocation = a(CHAOS_JEWELRY_60_TAB, NORMAL, r(0, 0, 11, 4))
  val AMULET_ALLOCATION: Allocation = a(CHAOS_JEWELRY_60_TAB, NORMAL, r(0, 5, 11, 7))
  val BELT_ALLOCATION: Allocation = a(CHAOS_JEWELRY_60_TAB, NORMAL, r(0, 8, 11, 11))

  val BOOT_75_ALLOCATION: Allocation = a(REGAL_GEAR_75_TAB, NORMAL, r(0, 0, 11, 1))
  val GLOVE_75_ALLOCATION: Allocation = a(REGAL_GEAR_75_TAB, NORMAL, r(0, 2, 11, 3))
  val HELMET_75_ALLOCATION: Allocation = a(REGAL_GEAR_75_TAB, NORMAL, r(0, 4, 11, 5))
  val BODY_75_ALLOCATION: Allocation = a(REGAL_GEAR_75_TAB, NORMAL, r(0, 6, 11, 7))
  val WEAPON_75_ALLOCATION: Allocation = a(REGAL_GEAR_75_TAB, NORMAL, r(0, 8, 11, 11))
  val RING_75_ALLOCATION: Allocation = a(REGAL_JEWELRY_75_TAB, NORMAL, r(0, 0, 11, 4))
  val AMULET_75_ALLOCATION: Allocation = a(REGAL_JEWELRY_75_TAB, NORMAL, r(0, 5, 11, 7))
  val BELT_75_ALLOCATION: Allocation = a(REGAL_JEWELRY_75_TAB, NORMAL, r(0, 8, 11, 11))

  // 1080p Default
  var NORMAL_TAB_TOP_LEFT_COORD: (Int, Int) = (42, 188)
  var NORMAL_TAB_BOTTOM_RIGHT_COORD: (Int, Int) = (622, 767)
  var QUAD_TAB_TOP_LEFT_COORD: (Int, Int) = (30, 175)
  var QUAD_TAB_BOTTOM_RIGHT_COORD: (Int, Int) = (635, 780)
  var INVENTORY_TOP_LEFT_COORD: (Int, Int) = (1350, 615)
  var INVENTORY_BOTTOM_RIGHT_COORD: (Int, Int) = (1878, 825)
  var CENTER: (Int, Int) = (953, 452)
  var NORMAL_TAB_CELL_RADIUS: Int = 20
  var QUAD_TAB_CELL_RADIUS: Int = 10

  // 1200p
  if (RESOLUTION == ScreenResolution.P1200) {
    NORMAL_TAB_TOP_LEFT_COORD = (42, 188)
    NORMAL_TAB_BOTTOM_RIGHT_COORD = (622, 767)
    QUAD_TAB_TOP_LEFT_COORD = (30, 175)
    QUAD_TAB_BOTTOM_RIGHT_COORD = (635, 780)
    INVENTORY_TOP_LEFT_COORD = (1350, 615)
    INVENTORY_BOTTOM_RIGHT_COORD = (1877, 825)
    CENTER = (953, 452)
    NORMAL_TAB_CELL_RADIUS = 20
    QUAD_TAB_CELL_RADIUS = 10
  } else if (RESOLUTION == ScreenResolution.P1440) {
    NORMAL_TAB_TOP_LEFT_COORD = (58, 251)
    NORMAL_TAB_BOTTOM_RIGHT_COORD = (831, 1024)
    QUAD_TAB_TOP_LEFT_COORD = (40, 233)
    QUAD_TAB_BOTTOM_RIGHT_COORD = (846, 1041)
    INVENTORY_TOP_LEFT_COORD = (1801, 820)
    INVENTORY_BOTTOM_RIGHT_COORD = (2503, 1100)
    CENTER = (1330, 660)
    NORMAL_TAB_CELL_RADIUS = 31
    QUAD_TAB_CELL_RADIUS = 13
  }

  val NORMAL_TAB_WIDTH: Int = 12
  val NORMAL_TAB_HEIGHT: Int = 12

  val QUAD_TAB_WIDTH: Int = 24
  val QUAD_TAB_HEIGHT: Int = 24

  val INVENTORY_HEIGHT: Int = 5
  val INVENTORY_WIDTH: Int = 11
  val INVENTORY_CELL_RADIUS: Int = NORMAL_TAB_CELL_RADIUS

  private def a(tabIndex: Int, tabType: TabType, region: Option[Region] = None): Allocation = {
    new Allocation(tabIndex, tabType, region)
  }

  private def r(startRow: Int, startColumn: Int, endRow: Int, endColumn: Int): Option[Region] = Option(
    new Region(
      new Position(startRow, startColumn),
      new Position(endRow, endColumn)
    )
  )

  /**
    * Generates the next int index
    * @return
    */

  private def idx(): Int = {
    tabIndex += 1
    tabIndex
  }

}
