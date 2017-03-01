import java.awt.Robot
import java.awt.event.KeyEvent

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

  val allocations = collection.immutable.HashMap(
    TabType.HELMET -> Config.HELMET_ALLOCATION,
    TabType.BOOT -> Config.BOOT_ALLOCATION,
    TabType.GLOVE -> Config.GLOVE_ALLOCATION,
    TabType.BODY -> Config.BODY_ALLOCATION,
    TabType.WEAPON -> Config.WEAPON_ALLOCATION,
    TabType.RING -> Config.RING_ALLOCATION,
    TabType.AMULET -> Config.AMULET_ALLOCATION,
    TabType.BELT -> Config.BELT_ALLOCATION
  )

  val tabIndexes = collection.immutable.HashMap(
    TabType.CURRENCY -> Config.CURRENCY_TAB,
    TabType.ESSENCE -> Config.ESSENCE_TAB,
    TabType.DIVINATION -> Config.DIVINATION_TAB,
    TabType.HELMET -> Config.HELMET_ALLOCATION.tabIndex,
    TabType.BOOT -> Config.BOOT_ALLOCATION.tabIndex ,
    TabType.GLOVE -> Config.GLOVE_ALLOCATION.tabIndex ,
    TabType.BODY -> Config.BODY_ALLOCATION.tabIndex ,
    TabType.WEAPON -> Config.WEAPON_ALLOCATION.tabIndex ,
    TabType.RING -> Config.RING_ALLOCATION.tabIndex ,
    TabType.AMULET -> Config.AMULET_ALLOCATION.tabIndex ,
    TabType.BELT -> Config.BELT_ALLOCATION.tabIndex 
  )

  val tabs: Seq[Tab] = createTabs()
  var currentTabIndex: Int = 0

  def currentTab(): Option[Tab] = tabs.find((tab: Tab) => {
    tab.index == currentTabIndex
  })

  /**
    * Changes the tab and reads the contents according to mode
    * @param tabType
    * @param mode
    */
  def activateTab(tabType: TabType, mode: Mode): Unit = {
    val tab: Int = tabIndexes(tabType)
    var tabChanged: Boolean = false

    while (currentTabIndex < tab) {
      nextTab()
      Thread sleep 50
      tabChanged = true
    }

    while (currentTabIndex > tab) {
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
        if(!tab.upToDate) {
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
    val tabIndexes: mutable.HashSet[Int] = new mutable.HashSet[Int]

    allocations.foreach((pair) => {
      val allocation = pair._2
      tabIndexes += allocation.tabIndex
    })

    tabIndexes.toList.map((index: Int) => {
      new Tab(index)
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
