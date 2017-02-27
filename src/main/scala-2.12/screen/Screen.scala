package screen

import java.awt.image.BufferedImage
import java.awt.{Color, Rectangle, Robot, Toolkit}

/**
  * Reference: http://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
  */
object Screen {
  val robot: Robot = new Robot
  val screenRect: Rectangle = new Rectangle(Toolkit.getDefaultToolkit.getScreenSize)
  var screen: Option[BufferedImage] = None

  def update: Unit = {
    screen = Option(robot.createScreenCapture(screenRect))
  }

  /**
    * Gets pixels as a single array
    * @param xCenter
    * @param yCenter
    * @param radius
    * @return
    */
  def getPixels(xCenter: Int, yCenter: Int, radius: Int): Seq[Color] = {
    if (screen.isEmpty) {
      throw new IllegalStateException("You must call Screen::update first")
    }
    val startX: Int = xCenter - radius
    val startY: Int = yCenter - radius
    val width: Int = 2*radius + 1
    val height: Int = width

    val pixels = Array.ofDim[Color](width * height)
    for(xOffset <- 0 until width; yOffset <- 0 until height) {
      val index: Int = yOffset * width + xOffset
      pixels(index) = getPixel(startX + xOffset, startY + yOffset)
    }
    pixels
  }

  def getPixel(x: Int, y: Int): Color = {
    if (screen.isEmpty) {
      throw new IllegalStateException("You must call Screen::update first")
    }

    new Color(screen.get.getRGB(x, y))
  }
}
