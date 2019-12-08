package parser.item

import parser.knowninfo.KnownInfo

class Fragment(
                knownInfo: KnownInfo
              ) extends Item(knownInfo) {

}

object Fragment {
  final val typeLines: Seq[String] = Seq[String](
    "Sacrifice at Dusk",
    "Sacrifice at Midnight",
    "Sacrifice at Dawn",
    "Sacrifice at Noon",

    "Volkuur's Key",
    "Inya's Key",
    "Eber's Key",
    "Yriel's Key",

    "Fragment of the Minotaur",
    "Fragment of the Chimera",
    "Fragment of the Phoenix",
    "Fragment of the Hydra",

    "Mortal Grief",
    "Mortal Rage",
    "Mortal Hope",
    "Mortal Ignorance",

    "Chayula's Breachstone",
    "Tul's Breachstone",
    "Xoph's Breachstone",
    "Esh's Breachstone",
    "Uul-Netol's Breachstone",
    "Splinter of Chayula",
    "Splinter of Tul",
    "Splinter of Xoph",
    "Splinter of Esh",
    "Splinter of Uul-Netol",

    "Timeless Eternal Emblem",
    "Timeless Karui Emblem",
    "Timeless Maraketh Emblem",
    "Timeless Templar Emblem",
    "Timeless Vaal Emblem",
    "Timeless Eternal Empire Splinter",
    "Timeless Karui Splinter",
    "Timeless Maraketh Splinter",
    "Timeless Templar Splinter",
    "Timeless Vaal Splinter",

    "Divine Vessel",

    "Offering to the Goddess"
  )
}