import java.awt.Robot
import java.awt.event.KeyEvent

import scala.concurrent.ExecutionContext.Implicits.global

object Stash {
  var currentTab: Int = 0
  val robot = new Robot
  val chaosTab: Tab = new Tab

  val helmetAllocation: Allocation = Config.HELMET_ALLOCATION
  val bootAllocation: Allocation = Config.BOOT_ALLOCATION
  val gloveAllocation: Allocation = Config.GLOVE_ALLOCATION
  val bodyAllocation: Allocation = Config.BODY_ALLOCATION
  val weaponAllocation: Allocation = Config.WEAPON_ALLOCATION
  val ringAllocation: Allocation = Config.RING_ALLOCATION
  val amuletAllocation: Allocation = Config.AMULET_ALLOCATION
  val beltAllocation: Allocation = Config.BELT_ALLOCATION

  def activateCurrencyTab(): Unit = activateTab(Config.CURRENCY_TAB)
  def activateEssenceTab(): Unit = activateTab(Config.ESSENCE_TAB)
  def activateDivinationTab(): Unit = activateTab(Config.DIVINATION_TAB)

  def resetTab(): Unit = {
    for (_ <- 1 to 25) prevTab()
    currentTab = 0
  }

  def activateTab(tab: Int): Unit = {
    while(currentTab < tab) {
      nextTab()
      Thread sleep 100
    }

    while(currentTab > tab) {
      prevTab()
      Thread sleep 100
    }
  }

  def nextTab(): Unit = {
    robot.keyPress(KeyEvent.VK_RIGHT)
    Thread sleep 5
    robot.keyRelease(KeyEvent.VK_RIGHT)
    currentTab += 1
  }

  def prevTab(): Unit = {
    robot.keyPress(KeyEvent.VK_LEFT)
    Thread sleep 5
    robot.keyRelease(KeyEvent.VK_LEFT)
    currentTab -= 1
  }

}
