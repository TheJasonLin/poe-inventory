import java.awt.Robot
import java.awt.event.KeyEvent

import scala.concurrent.ExecutionContext.Implicits.global

object Stash {
  var currentTab: Int = 0
  val robot = new Robot
  val chaosTab: Tab = new Tab

  def activateCurrencyTab(): Unit = activateTab(Config.CURRENCY_TAB)
  def activateChaosTab(): Unit = activateTab(Config.CHAOS_RECIPE_TAB)
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
