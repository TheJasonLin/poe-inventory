package parser.item

import parser.knowninfo.KnownInfo

abstract class CraftableItem(
                              knownInfo: KnownInfo
                            ) extends Item(knownInfo) {
  val identified: Boolean = knownInfo.identified.get
  val corrupted: Boolean = knownInfo.corrupted
  val quality: Option[Int] = knownInfo.quality

  val implicits: Seq[Mod] = if (knownInfo.implicits.isDefined) parseMods(knownInfo.implicits.get) else Seq.empty[Mod]
  val explicits: Seq[Mod] = if (knownInfo.explicits.isDefined) parseMods(knownInfo.explicits.get) else Seq.empty[Mod]


  def parseMods(modStrings: Seq[String]): Seq[Mod] = {
    modStrings.map((modString: String) => {
      Mod.parse(modString)
    })
  }

  def qualityValue(): Int = {
    if (quality.isEmpty) 0
    else quality.get
  }

  override def toString: String = super.toString() + s"[ilvl: $itemLevel, id: $identified, quality: $quality]"

  override def asDBItem: DBItem = {
    super.asDBItem.copy(
      itemLevel = Option(itemLevel),
      identified = Option(identified),
      quality = quality,
      implicits = Option(implicits),
      explicits = Option(explicits)
    )
  }
}
