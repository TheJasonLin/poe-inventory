package parser.item

import constants.Rarity
import parser.knowninfo.KnownInfo

abstract class Item(knownInfo: KnownInfo) {
  if (knownInfo != null) {

  }
  val rarity: Rarity = knownInfo.rarity
  val typeLine: String = knownInfo.typeLine
  val name: Option[String] = knownInfo.name
  val id: Option[String] = knownInfo.id
  val ownerInfo: Option[OwnerInfo] = knownInfo.ownerInfo
  val positionX: Option[Int] = knownInfo.positionX
  val positionY: Option[Int] = knownInfo.positionY
  // all items have an item level. Those that don't state them, like gems, are level 1.
  val itemLevel: Int = knownInfo.itemLevel.getOrElse(1)

  def width(): Int = 1
  def height(): Int = 1
  def className: String = this.getClass.getSimpleName

  override def toString: String = getClass + s"($typeLine, $name, $rarity, ${width()}, ${height()})"

  def asDBItem: DBItem = {
    if(id.isEmpty) throw new IllegalArgumentException("id (from GGG) must be defined to create DB Object")

    val dBOwnerInfo: Option[DBOwnerInfo] = if (ownerInfo.isDefined) Option(ownerInfo.get.asDBOwnerInfo) else None
    DBItem(id.get, className, rarity.key, typeLine, name, width(), height(), dBOwnerInfo)
  }
}

object Item {
  def matchesIdentifier(typeLine: String, identifiers: Array[String]): Boolean = {
    val baseWords = typeLine.split(" ")
    baseWords.exists((baseWord) => {
      identifiers.contains(baseWord)
    })
  }

  def containsIdentifier(typeLine: String, identifiers: Array[String]): Boolean = {
    identifiers.exists(identifier => typeLine.contains(identifier))
  }
}