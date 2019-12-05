package parser.item.equipment.armour

import parser.knowninfo.KnownInfo

class Shield(
              knownInfo: KnownInfo,
              val tallShield: Boolean,
              val shortShield: Boolean
            ) extends Armour(knownInfo) {
  override def height(): Int = {
    if (tallShield) {
      4
    } else if (shortShield) {
      2
    } else {
      3
    }
  }
}

object Shield {
  val identifiers = Array(
    "Shield",
    "Buckler",
    "Bundle"
  )

  val tallShieldIdentifiers = Array(
    "Tower Shield"
  )

  val shortShieldIdentifiers = Array(
    "Buckler",
    "Spirit Shield",
    "Spiked Shield",
    "Spiked Bundle"
  )
}
