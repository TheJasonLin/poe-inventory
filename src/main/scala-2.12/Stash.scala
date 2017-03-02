import java.awt.Robot
import java.awt.event.KeyEvent

import items.map.MapItem
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

  val generalAllocations = collection.immutable.HashMap(
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

  val mapAllocations = Config.MAP_ALLOCATION

  val tabs: Seq[Tab] = createTabs()
  var currentTabIndex: Int = 0

  def currentTab(): Option[Tab] = tabs.find((tab: Tab) => {
    tab.index == currentTabIndex
  })

  /**
    * Changes the tab and reads the contents according to mode
    * NOTE: Not used for Maps, since maps can be in different tabs
    * @param tabContents
    * @param mode
    */
  def activateTab(tabContents: TabContents, mode: Mode): Unit = {
    val allocation = generalAllocations(tabContents)
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

    generalAllocations.foreach((pair) => {
      val allocation: Allocation = pair._2
      val tabInfo: (Int, TabType) = (allocation.tabIndex, allocation.tabType)
      tabInfos += tabInfo
    })

    mapAllocations.foreach((pair) => {
      val allocation: Allocation = pair._2
      val tabInfo: (Int, TabType) = (allocation.tabIndex, allocation.tabType)
      tabInfos += tabInfo
    })

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
