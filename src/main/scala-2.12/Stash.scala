import java.awt.Robot
import java.awt.event.KeyEvent

import scala.concurrent.ExecutionContext.Implicits.global

object Stash {
  var currentTab: Int = 0
  val robot = new Robot

  def activateCurrencyTab(): Unit = activateTab(Config.CURRENCY_TAB)
  def activateChaosTab(): Unit = activateTab(Config.CHAOS_RECIPE_TAB)
  def activateEssenceTab(): Unit = activateTab(Config.ESSENCE_TAB)
  def activateDivinationTab(): Unit = activateTab(Config.DIVINATION_TAB)

  def resetTab(): Unit = {
    for (_ <- 1 to 25) prevTab()
  }

  def activateTab(tab: Int): Unit = {
    while(currentTab < tab) {
      nextTab()
    }

    while(currentTab > tab) {
      prevTab()
    }
  }

  def nextTab(): Unit = {
    robot.keyPress(KeyEvent.VK_RIGHT)
    Thread sleep 50
    robot.keyRelease(KeyEvent.VK_RIGHT)

    currentTab += 1
  }

  def prevTab(): Unit = {
    robot.keyPress(KeyEvent.VK_LEFT)
    Thread sleep 10
    robot.keyRelease(KeyEvent.VK_LEFT)

    currentTab -= 1
  }

}
