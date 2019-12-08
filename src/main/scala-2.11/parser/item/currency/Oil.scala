package parser.item.currency

import parser.knowninfo.KnownInfo

class Oil(
           knownInfo: KnownInfo
         ) extends Currency(knownInfo) {

}

object Oil {
  final val typeLines: Seq[String] = Seq[String](
    "Clear Oil",
    "Sepia Oil",
    "Amber Oil",
    "Verdant Oil",
    "Teal Oil",
    "Azure Oil",
    "Violet Oil",
    "Crimson Oil",
    "Black Oil",
    "Opalescent Oil",
    "Silver Oil",
    "Golden Oil"
  )
}