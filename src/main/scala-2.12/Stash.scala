import java.awt.Robot
import java.awt.event.KeyEvent

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

  def activateCurrencyTab(): Unit = activateTab(Config.CURRENCY_TAB)

  def activateTab(tab: Int): Unit = {
    while (currentTabIndex < tab) {
      nextTab()
      Thread sleep 250
    }

    while (currentTabIndex > tab) {
      prevTab()
      Thread sleep 250
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

  def currentTab(): Option[Tab] = tabs.find((tab: Tab) => {
    tab.index == currentTabIndex
  })

  def activateEssenceTab(): Unit = activateTab(Config.ESSENCE_TAB)

  def activateDivinationTab(): Unit = activateTab(Config.DIVINATION_TAB)

  def activateHelmetTab(): Unit = activateTab(helmetAllocation.tabIndex)
  def activateBootTab(): Unit = activateTab(bootAllocation.tabIndex)
  def activateGloveTab(): Unit = activateTab(gloveAllocation.tabIndex)
  def activateBodyTab(): Unit = activateTab(bodyAllocation.tabIndex)
  def activateWeaponTab(): Unit = activateTab(weaponAllocation.tabIndex)
  def activateRingTab(): Unit = activateTab(ringAllocation.tabIndex)
  def activateAmuletTab(): Unit = activateTab(amuletAllocation.tabIndex)
  def activateBeltTab(): Unit = activateTab(beltAllocation.tabIndex)

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
  }

}
