package screen

class Pixel (
            val alpha: Int,
            val red: Int,
            val green: Int,
            val blue: Int
            ){

  override def toString = s"Pixel($alpha, $red, $green, $blue)"
}
