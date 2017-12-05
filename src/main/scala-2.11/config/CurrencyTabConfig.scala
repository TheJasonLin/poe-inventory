package config

import structures.PixelPosition

object CurrencyTabConfig {
  val SHORT_HORIZONTAL_GAP: Int = 60
  val SHORT_VERTICAL_GAP: Int = 53
  val LONG_HORIZONTAL_GAP: Int = 70

  val SCROLL_FRAGMENT_COORDS: (Int, Int) = (56, 254)
  val WHETSTONE_COORDS: (Int, Int) = (375, 255)
  val TRANSMUTATION_COORDS: (Int, Int) = (56, 332)
  val JEWELLERS_COORDS: (Int, Int) = (114, 477)
  val SCOURING_COORDS: (Int, Int) = (430, 476)

  private val shg: Int = SHORT_HORIZONTAL_GAP
  private val svg = SHORT_VERTICAL_GAP
  private val lhg = LONG_HORIZONTAL_GAP
  // section starts
  private val ss: Seq[(Int, Int)] = Seq(
    SCROLL_FRAGMENT_COORDS,
    WHETSTONE_COORDS,
    TRANSMUTATION_COORDS,
    JEWELLERS_COORDS,
    SCOURING_COORDS
  )

  def getCoords(section: Int, shortHorizontalOffsets: Int, longHorizontalOffsets: Int, verticalOffsets: Int): PixelPosition = {
    new PixelPosition(ss(section)._1 + shortHorizontalOffsets*shg + longHorizontalOffsets*lhg, ss(section)._2 + verticalOffsets*svg)
  }

  val CURRENCY_TAB_POSITIONS: Map[String, PixelPosition] = Map[String, PixelPosition](
    "Scroll Fragment" -> getCoords(0, 0, 0, 0),
    "Scroll of Wisdom" -> getCoords(0, 1, 0, 0),
    "Portal Scroll" -> getCoords(0, 2, 0, 0),

    "Blacksmith's Whetstone" -> getCoords(1, 0, 0, 0),
    "Armourer's Scrap" -> getCoords(1, 1, 0, 0),
    "Glassblower's Bauble" -> getCoords(1, 2, 0, 0),
    "Gemcutter's Prism" -> getCoords(1, 3, 0, 0),
    "Cartographer's Chisel" -> getCoords(1, 4, 0, 0),

    "Orb of Transmutation" -> getCoords(2, 0, 0, 0),
    "Orb of Alteration" -> getCoords(2, 1, 0, 0),
    "Orb of Annulment" -> getCoords(2, 2, 0, 0),
    "Orb of Chance" -> getCoords(2, 3, 0, 0),
    "Exalted Orb" -> getCoords(2, 3, 1, 0),
    "Mirror of Kalandra" -> getCoords(2, 4, 1, 0),
    "Regal Orb" -> getCoords(2, 4, 2, 0),
    "Orb of Alchemy" -> getCoords(2, 5, 2, 0),
    "Chaos Orb" -> getCoords(2, 6, 2, 0),
    "Blessed Orb" -> getCoords(2, 7, 2, 0),
    // new row
    "Transmutation Shard" -> getCoords(2, 0, 0, 1),
    "Alteration Shard" -> getCoords(2, 1, 0, 1),
    "Annulment Shard" -> getCoords(2, 2, 0, 1),
    "Orb of Augmentation" -> getCoords(2, 3, 0, 1),
    "Exalted Shard" -> getCoords(2, 3, 1, 1),
    "Mirror Shard" -> getCoords(2, 4, 1, 1),
    "Regal Shard" -> getCoords(2, 4, 2, 1),
    "Alchemy Shard" -> getCoords(2, 5, 2, 1),
    "Chaos Shard" -> getCoords(2, 6, 2, 1),
    "Divine Orb" -> getCoords(2, 7, 2, 1),

    "Jeweller's Orb" -> getCoords(3, 0, 0, 0),
    "Orb of Fusing" -> getCoords(3, 1, 0, 0),
    "Chromatic Orb" -> getCoords(3, 2, 0, 0),
    // new row
    "Perandus Coin" -> getCoords(3, 1, 0, 1),
    "Silver Coin" -> getCoords(3, 3, 0, 1),

    "Scouring Orb" -> getCoords(4, 0, 0, 0),
    "Orb of Regret" -> getCoords(4, 1, 0, 0),
    "Vaal Orb" -> getCoords(4, 2, 0, 0),
    // new row
    "Apprentice Cartographer's Sextant" -> getCoords(4, 0, 0, 1),
    "Journeyman Cartographer's Sextant" -> getCoords(4, 1, 0, 1),
    "Master Cartographer's Sextant" -> getCoords(4, 2, 0, 1)
  )

}
