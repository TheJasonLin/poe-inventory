import java.awt.Robot
import java.awt.event.KeyEvent

import items.{Item, Leaguestone, MapItem}
import screen.Screen

import scala.collection.mutable

object Stash {
  val robot = new Robot

  val helmetAllocation: Allocation = Config.HELMET_ALLOCATION
  val bootAllocation: Allocation = Config.BOOT_ALLOCATION
  val gloveAllocation: Allocation = Config.GLOVE_ALLOCATION
  val bodyAllocation: Allocation = Config.BODY_ALLOCATION
  val weaponAllocation: Allocation = Config.WEAPON_ALLOCATION
  val ringAllocation: Allocation = Config.RING_ALLOCATION
  val amuletAllocation: Allocation = Config.AMULET_ALLOCATION
  val beltAllocation: Allocation = Config.BELT_ALLOCATION

  private val _generalAllocations = collection.immutable.HashMap(
    TabContents.CURRENCY -> Config.CURRENCY_ALLOCATION,
    TabContents.ESSENCE -> Config.ESSENCE_ALLOCATION,
    TabContents.DIVINATION -> Config.DIVINATION_ALLOCATION,
    TabContents.HELMET -> Config.HELMET_ALLOCATION,
    TabContents.BOOT -> Config.BOOT_ALLOCATION,
    TabContents.GLOVE -> Config.GLOVE_ALLOCATION,
    TabContents.BODY -> Config.BODY_ALLOCATION,
    TabContents.WEAPON -> Config.WEAPON_ALLOCATION,
    TabContents.RING -> Config.RING_ALLOCATION,
    TabContents.AMULET -> Config.AMULET_ALLOCATION,
    TabContents.BELT -> Config.BELT_ALLOCATION
  )

  private val _chaos75Allocations = collection.immutable.HashMap(
    TabContents.HELMET -> Config.HELMET_75_ALLOCATION,
    TabContents.BOOT -> Config.BOOT_75_ALLOCATION,
    TabContents.GLOVE -> Config.GLOVE_75_ALLOCATION,
    TabContents.BODY -> Config.BODY_75_ALLOCATION,
    TabContents.WEAPON -> Config.WEAPON_75_ALLOCATION,
    TabContents.RING -> Config.RING_75_ALLOCATION,
    TabContents.AMULET -> Config.AMULET_75_ALLOCATION,
    TabContents.BELT -> Config.BELT_75_ALLOCATION
  )

  val mapAllocations = Config.MAP_ALLOCATION
  val leaguestoneAllocations = Config.LEAGUESTONE_ALLOCATION

  val qualityFlaskAllocations = Config.QUALITY_FLASK_ALLOCATION
  val qualityGemAllocations = Config.QUALITY_GEM_ALLOCATION

  val miscAllocations = Config.MISC_ALLOCATION

  val tabs: Seq[Tab] = createTabs()
  var currentTabIndex: Int = 0

  def currentTab(): Option[Tab] = tabs.find((tab: Tab) => {
    tab.index == currentTabIndex
  })

  def getAllocation(tabContents: TabContents, level75: Boolean = false): Allocation = {
    if(!level75) {
      _generalAllocations(tabContents)
    } else {
      _chaos75Allocations(tabContents)
    }
  }

  /**
    * Changes the tab and reads the contents according to mode
    * NOTE: Not used for Maps, since maps can be in different tabs
    * @param tabContents
    * @param mode
    */
  def activateTab(tabContents: TabContents, mode: Mode, use75Allocations: Boolean): Unit = {
    var allocation: Allocation = null
    if(use75Allocations) allocation = _chaos75Allocations(tabContents)
    else allocation = _generalAllocations(tabContents)
    activateTab(allocation, mode)
  }

  /**
    * Activates the tab for an allocation
    * @param allocation
    */
  def activateTab(allocation: Allocation, mode: Mode): Unit = {
    val tabIndex: Int = allocation.tabIndex
    activateTab(tabIndex, mode)
  }

  /**
    * Changes the tab and reads the contents according to the mode
    * @param tabIndex
    * @param mode
    */
  def activateTab(tabIndex: Int, mode: Mode): Unit = {
    var tabChanged: Boolean = false

    while (currentTabIndex < tabIndex) {
      nextTab()
      Thread sleep 50
      tabChanged = true
    }

    while (currentTabIndex > tabIndex) {
      prevTab()
      Thread sleep 50
      tabChanged = true
    }

    if (tabChanged) {
      Thread sleep 200
      // update screen
      Screen.update()
      // update tab if there's an actual tab
      val tabOption = currentTab()
      if(tabOption.isDefined) {
        val tab = tabOption.get
        // don't try reading Currency / Essence / Div Tabs
        if(!tab.upToDate && tab.tabType != TabType.SPECIAL) {
          mode match {
            case Mode.READ_POSITIONS => {
              tab.updateOccupancy()
            }
            case Mode.READ_POSITIONS_AND_ITEMS => {
              tab.updateOccupancyAndItems()
            }
            case Mode.NO_READ => {
              // do nothing
            }
          }
        }
      }
    }
  }

  def findMapAllocation(map: MapItem): Option[Allocation] = {
    val pair: Option[(Int, Allocation)] = mapAllocations
        .find((pair: (Int, Allocation)) => {
          map.tier == pair._1
        })
    if(pair.isEmpty) return None
    val allocation: Allocation = pair.get._2
    Option(allocation)
  }

  def findLeaguestoneAllocation(leaguestone: Leaguestone): Option[Allocation] = {
    val pair: Option[(String, Allocation)] = leaguestoneAllocations
      .find((pair: (String, Allocation)) => {
        leaguestone.base.contains(pair._1)
      })
    if(pair.isEmpty) return None
    val allocation: Allocation = pair.get._2
    Option(allocation)
  }

  def findMiscAllocation(item: Item): Allocation = {
    val baseName = item.base
    miscAllocations(baseName)
  }

  def nextTab(): Unit = {
    robot.keyPress(KeyEvent.VK_RIGHT)
    Thread sleep 5
    robot.keyRelease(KeyEvent.VK_RIGHT)
    currentTabIndex += 1
  }

  def prevTab(): Unit = {
    robot.keyPress(KeyEvent.VK_LEFT)
    Thread sleep 5
    robot.keyRelease(KeyEvent.VK_LEFT)
    currentTabIndex -= 1
  }

  //@TODO: Merge the general and map allocations
  def createTabs(): Seq[Tab] = {
    // using a set to avoid duplicates
    val tabInfos: mutable.HashSet[(Int, TabType)] = new mutable.HashSet[(Int, TabType)]

    def addAllocation[K](allocations: Map[K, Allocation]): Unit = {
      allocations.foreach((pair) => {
        val allocation: Allocation = pair._2
        val tabInfo: (Int, TabType) = (allocation.tabIndex, allocation.tabType)
        tabInfos += tabInfo
      })
    }

    addAllocation[TabContents](_generalAllocations)
    addAllocation[TabContents](_chaos75Allocations)

    addAllocation[Int](mapAllocations)
    addAllocation[String](leaguestoneAllocations)

    tabInfos += (qualityFlaskAllocations.tabIndex, qualityFlaskAllocations.tabType).asInstanceOf[(Int, TabType)]

    tabInfos += (qualityGemAllocations.tabIndex, qualityGemAllocations.tabType).asInstanceOf[(Int, TabType)]

    tabInfos.toList.map((tabInfo: (Int, TabType)) => {
      val index = tabInfo._1
      val tabType = tabInfo._2
      new Tab(index, tabType)
    })
  }

  def resetTab(): Unit = {
    for (_ <- 1 to 25) prevTab()
    currentTabIndex = 0
    Screen.update()
  }

  def markTabsOutOfDate(): Unit = {
    tabs.foreach(_.upToDate = false)
  }
}
