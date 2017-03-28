import java.awt.datatransfer.{Clipboard, DataFlavor}
import java.awt.{Robot, Toolkit}
import java.awt.event.{InputEvent, KeyEvent}

import structures.PixelPosition

object Clicker {
  val robot: Robot = new Robot()
  val clipboard: Clipboard = Toolkit.getDefaultToolkit.getSystemClipboard

  /**
    *
    * @param pixelPosition
    * @param ctrlMod
    * @return if the color changed after the click
    */
  def click(pixelPosition: PixelPosition, ctrlMod: Boolean = false): Boolean = {
    val x = pixelPosition.x
    val y = pixelPosition.y
    val colorPreClick = robot.getPixelColor(x, y)
    robot.mouseMove(x, y)
    if(ctrlMod) robot keyPress KeyEvent.VK_CONTROL
    Thread sleep 20
    click()
    Thread sleep 20
    if(ctrlMod) robot keyRelease KeyEvent.VK_CONTROL
    val colorPostClick = robot getPixelColor(x, y)
    !colorPreClick.equals(colorPostClick)
  }

  def getItemInfo(pixelPosition: PixelPosition): String = {
    val x = pixelPosition.x
    val y = pixelPosition.y
    robot mouseMove(x, y)
    quickSleep()
    robot keyPress KeyEvent.VK_CONTROL
    quickSleep()
    robot keyPress KeyEvent.VK_C
    quickSleep()
    robot keyRelease KeyEvent.VK_C
    quickSleep()
    robot keyRelease KeyEvent.VK_CONTROL
    Thread sleep 100

    val clipboardText = clipboard.getData(DataFlavor.stringFlavor).asInstanceOf[String]
    clipboardText
  }

  /**
    * moves an item from source to destination
    * @param source
    * @param destination
    * @return
    */
  def move(source: PixelPosition, destination: PixelPosition): Boolean = {
    // move mouse to source
    robot mouseMove(source.x, source.y)
    quickSleep()
    // pick up item at source
    click()
    Thread sleep 100
    // move mouse to destination
    robot mouseMove(destination.x, destination.y)
    quickSleep()
    // drop item off at destination
    click()
    Thread sleep 100
    true
  }

  // moves the mouse to the center of the screen to avoid any tooltip from blocking the screen
  def center(): Unit = {
    robot mouseMove(Config.CENTER._1, Config.CENTER._2)
    quickSleep()
  }

  private def click(): Unit = {
    robot mousePress InputEvent.BUTTON1_MASK
    quickSleep
    robot mouseRelease InputEvent.BUTTON1_MASK
  }

  private def quickSleep() = Thread sleep Config.QUICK_SLEEP
}
