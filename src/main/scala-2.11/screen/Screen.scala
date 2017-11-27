package screen

import java.awt.image.BufferedImage
import java.awt.{Color, Rectangle, Robot, Toolkit}
import java.io.File
import javax.imageio.ImageIO

import structures.PixelPosition

/**
  * Reference: http://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
  */
object Screen {
  val robot: Robot = new Robot
  val screenRect: Rectangle = new Rectangle(Toolkit.getDefaultToolkit.getScreenSize)
  var screen: Option[BufferedImage] = None

  def update(): Unit = {
    screen = Option(robot.createScreenCapture(screenRect))
  }

  /**
    * Gets pixels as a single array
    * @param center
    * @param radius
    * @return
    */
  def getPixels(center: PixelPosition, radius: Int): Seq[Color] = {
    val xCenter = center.x
    val yCenter = center.y
    if (screen.isEmpty) {
      throw new IllegalStateException("You must call Screen::update first")
    }
    val startX: Int = xCenter - radius
    val startY: Int = yCenter - radius
    val width: Int = 2*radius + 1
    val height: Int = width

    val pixels = Array.ofDim[Color](width * height)
    var index: Int = 0
    for(xOffset <- 0 until width; yOffset <- 0 until height) {
      pixels(index) = getPixel(startX + xOffset, startY + yOffset)
      index += 1
    }
    pixels
  }

  def getPixel(x: Int, y: Int): Color = {
    if (screen.isEmpty) {
      throw new IllegalStateException("You must call Screen::update first")
    }

    new Color(screen.get.getRGB(x, y))
  }

  def getPixelsFaster(xCenter: Int, yCenter: Int, radius: Int): Seq[Color] = {
    if (screen.isEmpty) {
      throw new IllegalStateException("You must call Screen::update first")
    }
    val startX: Int = xCenter - radius
    val startY: Int = yCenter - radius
    val width: Int = 2*radius + 1
    val height: Int = width

    val rgbValues: Seq[Int] = screen.get.getRGB(startX, startY, width, height, null, 0, 0)
    val colors: Seq[Color] = rgbValues.map(new Color(_))
    colors
  }

  /**
    * Testing Purposes. saves image out
    */
  def setPixels(center: PixelPosition, radius: Int): Unit = {
    val xCenter: Int = center.x
    val yCenter: Int = center.y
    val startX: Int = xCenter - radius
    val startY: Int = yCenter - radius
    val width: Int = 2*radius + 1
    val height: Int = width
    val offset = 0
    val scanSize = 0

    val red = new Color(255, 0, 0).getRGB
    val rgbArray: Array[Int] = Array.fill(height * width){red}

    screen.get.setRGB(startX, startY, width, height, rgbArray, offset, scanSize)
  }

  def save(file: String): Unit = {
    val dotIndex = file.indexOf('.')
    val format: String = file.substring(dotIndex + 1)
    ImageIO.write(screen.get, format, new File(file))
  }
}
