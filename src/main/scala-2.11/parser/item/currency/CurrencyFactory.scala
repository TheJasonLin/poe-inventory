package parser.item.currency

import parser.knowninfo.KnownInfo

object CurrencyFactory {
  def create(knownInfo: KnownInfo): Option[Currency] = {
    if (knownInfo.isEssence) return Option(new Essence(knownInfo))
    if (knownInfo.isFossil) return Option(new Fossil(knownInfo))
    if (knownInfo.isResonator) return Option(new Resonator(knownInfo))
    if (knownInfo.isOil) return Option(new Oil(knownInfo))
    if (knownInfo.isBasicCurrency) return Option(new BasicCurrency(knownInfo))
    None
  }
}
