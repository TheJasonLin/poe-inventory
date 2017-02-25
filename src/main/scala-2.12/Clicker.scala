import java.awt.datatransfer.{Clipboard, DataFlavor}
import java.awt.{Robot, Toolkit}
import java.awt.event.{InputEvent, KeyEvent}

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
    val x = pixelPosition.getX
    val y = pixelPosition.getY
    val colorPreClick = robot.getPixelColor(x, y)
//    println("Pre Click Color: " + colorPreClick.toString)
    robot.mouseMove(x, y)
    if(ctrlMod) robot keyPress KeyEvent.VK_CONTROL
    Thread sleep 50
    robot mousePress InputEvent.BUTTON1_MASK
    Thread sleep 50
    robot mouseRelease InputEvent.BUTTON1_MASK
    Thread sleep 50
    if(ctrlMod) robot keyRelease KeyEvent.VK_CONTROL
    val colorPostClick = robot getPixelColor(x, y)
//    println("Post Click Color: " + colorPreClick.toString)
    !colorPreClick.equals(colorPostClick)
  }

  def getItemInfo(pixelPosition: PixelPosition): String = {
    val x = pixelPosition.getX
    val y = pixelPosition.getY
    robot mouseMove(x, y)
    Thread sleep 50
    robot keyPress KeyEvent.VK_CONTROL
    Thread sleep 50
    robot keyPress KeyEvent.VK_C
    Thread sleep 50
    robot keyRelease KeyEvent.VK_C
    Thread sleep 50
    robot keyRelease KeyEvent.VK_CONTROL
    clipboard.getData(DataFlavor.stringFlavor).asInstanceOf[String]
  }
}
