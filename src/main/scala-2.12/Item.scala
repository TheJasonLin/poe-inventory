import Items.Currency

class Item(
          private val position: Position,
          clipboard: String
          ) {
  val rarity: String = parseRarity(clipboard)
  val name: String = parseName(clipboard)

  def parseRarity(clipboard: String): String = {
    val spaceIndex = clipboard.indexOf(' ')
    val newLineIndex = clipboard.indexOf('\n')
    clipboard.substring(spaceIndex + 1, newLineIndex)
  }

  def getPosition: Position = {
    position
  }

  def parseName(clipboard: String): String = {
    val fstNewLineIndex = clipboard.indexOf('\n')
    val sndNewLineIndex = clipboard.indexOf('\n', fstNewLineIndex + 1)
    clipboard.substring(fstNewLineIndex + 1, sndNewLineIndex)
  }

  def isCurrency(): Boolean = {
    Currency.is(name)
  }
}
