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
  val tabs: Seq[Tab] = createTabs()
  var currentTabIndex: Int = 0

  def currentTab(): Option[Tab] = tabs.find((tab: Tab) => {
    tab.index == currentTabIndex
  })

  /**
    * In addition to activating, will update if it isn't already updated
    */
  def activateCurrencyTab(mode: Mode): Unit = activateTab(Config.CURRENCY_TAB, mode)

  def activateEssenceTab(mode: Mode): Unit = activateTab(Config.ESSENCE_TAB, mode)

  def activateDivinationTab(mode: Mode): Unit = activateTab(Config.DIVINATION_TAB, mode)

  def activateHelmetTab(mode: Mode): Unit = activateTab(helmetAllocation.tabIndex, mode)

  def activateBootTab(mode: Mode): Unit = activateTab(bootAllocation.tabIndex, mode)

  def activateGloveTab(mode: Mode): Unit = activateTab(gloveAllocation.tabIndex, mode)

  def activateBodyTab(mode: Mode): Unit = activateTab(bodyAllocation.tabIndex, mode)

  def activateWeaponTab(mode: Mode): Unit = activateTab(weaponAllocation.tabIndex, mode)

  def activateRingTab(mode: Mode): Unit = activateTab(ringAllocation.tabIndex, mode)

  def activateAmuletTab(mode: Mode): Unit = activateTab(amuletAllocation.tabIndex, mode)

  def activateBeltTab(mode: Mode): Unit = activateTab(beltAllocation.tabIndex, mode)

  /**
    * Changes the tab and reads the contents according to mode
    * @param tab
    * @param mode
    */
  def activateTab(tab: Int, mode: Mode): Unit = {
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
    tabIndexes += helmetAllocation.tabIndex
    tabIndexes += bootAllocation.tabIndex
    tabIndexes += gloveAllocation.tabIndex
    tabIndexes += bodyAllocation.tabIndex
    tabIndexes += weaponAllocation.tabIndex
    tabIndexes += ringAllocation.tabIndex
    tabIndexes += amuletAllocation.tabIndex
    tabIndexes += beltAllocation.tabIndex

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
