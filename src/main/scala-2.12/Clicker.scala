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
    Thread sleep 50
    robot mousePress InputEvent.BUTTON1_MASK
    Thread sleep 50
    robot mouseRelease InputEvent.BUTTON1_MASK
    Thread sleep 50
    if(ctrlMod) robot keyRelease KeyEvent.VK_CONTROL
    val colorPostClick = robot getPixelColor(x, y)
    !colorPreClick.equals(colorPostClick)
  }

  def getItemInfo(pixelPosition: PixelPosition): String = {
    val x = pixelPosition.x
    val y = pixelPosition.y
    robot mouseMove(x, y)
    Thread sleep 10
    robot keyPress KeyEvent.VK_CONTROL
    Thread sleep 10
    robot keyPress KeyEvent.VK_C
    Thread sleep 10
    robot keyRelease KeyEvent.VK_C
    Thread sleep 10
    robot keyRelease KeyEvent.VK_CONTROL
    Thread sleep 100
    val clipboardText = clipboard.getData(DataFlavor.stringFlavor).asInstanceOf[String]
    clipboardText
  }
}
