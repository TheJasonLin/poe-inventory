import java.awt.Robot
import java.awt.event.KeyEvent

import parser.item.equipment.accessory.Talisman
import parser.item.{Leaguestone, MapItem}
import config.Config
import containers.{ScreenItem, _}
import screen.Screen

import scala.collection.mutable

object Stash {
  val robot = new Robot

  private val _generalAllocations = collection.immutable.HashMap(
    TabContents.CURRENCY -> Config.CURRENCY_ALLOCATION,
    TabContents.ESSENCE -> Config.ESSENCE_ALLOCATION,
    TabContents.DIVINATION -> Config.DIVINATION_ALLOCATION,
    TabContents.MAP -> Config.MAP_ALLOCATION,
    TabContents.FRAGMENT -> Config.FRAGMENT_ALLOCATION,
    TabContents.DELVE -> Config.DELVE_ALLOCATION,
    TabContents.MISC -> Config.MISC_ALLOCATION
  )

  val tabs: Seq[Tab] = createTabs()
  var currentTabIndex: Int = 0

  def currentTab(): Option[Tab] = tabs.find((tab: Tab) => {
    tab.index == currentTabIndex
  })

  def getAllocation(tabContents: TabContents): Option[Allocation] = {
      _generalAllocations(tabContents)
  }

  /**
    * Changes the tab and reads the contents according to mode
    * NOTE: Not used for Maps, since maps can be in different tabs
    * @param tabContents
    * @param mode
    */
  def activateTab(tabContents: TabContents, mode: Mode): Boolean = {
    val allocation = _generalAllocations(tabContents)
    if (allocation.isEmpty) return false
    activateTab(allocation, mode)
    true
  }

  /**
    * Activates the tab for an allocation
    * @param allocation
    */
  def activateTab(allocation: Option[Allocation], mode: Mode): Boolean = {
    if (allocation.isEmpty)return false
    val tabIndex: Int = allocation.get.tabIndex
    activateTab(tabIndex, mode)
    true
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
      Thread sleep Config.TAB_CHANGE_DELAY
      // move mouse so we can read without any highlighting in the way'
      Clicker.center()
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

  def createTabs(): Seq[Tab] = {
    // using a set to avoid duplicates
    val tabInfos: mutable.HashSet[(Int, TabType)] = new mutable.HashSet[(Int, TabType)]

    def addAllocation(allocation: Option[Allocation]): Unit = {
      if (allocation.isEmpty) return
      tabInfos += (allocation.get.tabIndex, allocation.get.tabType).asInstanceOf[(Int, TabType)]
    }

    def addAllocations(allocations: Map[TabContents, Option[Allocation]]): Unit = {
      allocations.foreach((pair) => {
        val allocation = pair._2
        addAllocation(allocation)
      })
    }
    addAllocations(_generalAllocations)

    addAllocation(Config.RUN_ALLOCATION)
    addAllocation(Config.DUMP_ALLOCATION)
    addAllocation(Config.QUICK_SELL_ALLOCATION)

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
