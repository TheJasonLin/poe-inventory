package parser.item.equipment.weapon

import parser.knowninfo.KnownInfo

class Dagger(
              knownInfo: KnownInfo
            ) extends Weapon(knownInfo) {

}

object Dagger {
  val identifiers = Array(
    "Shank",
    "Knife",
    "Stiletto",
    "Kris",
    "Skean",
    "Dagger",
    "Poignard",
    "Blade",
    "Trisula",
    "Ambusher",
    "Sai"
  )

  val oneHandedBases = Array(
    "Boot Blade"
  )
}
