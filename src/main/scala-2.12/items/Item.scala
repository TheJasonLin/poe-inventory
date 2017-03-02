package items

import structures.Position

abstract class Item(val rarity: String, val base: String, val name: Option[String]) {
  def width(): Int = 1
  def height(): Int = 1

  var positions: Seq[Position] = List.empty[Position]
  def position: Option[Position] = if(positions.isEmpty) None else Option(positions(0))


  override def toString: String = getClass + s"($positions, $rarity, $base, $name)"
}

object Item {
  def matchesIdentifier(base: String, identifiers: Array[String]): Boolean = {
    val baseWords = base.split(" ")
    baseWords.exists((baseWord) => {
      identifiers.contains(baseWord)
    })
  }
}