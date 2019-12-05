package parser.item.currency

import parser.knowninfo.KnownInfo

object CurrencyFactory {
  def create(knownInfo: KnownInfo): Option[Currency] = {
    if(knownInfo.isEssence) {
      return Option(new Essence(knownInfo))
    }

    if(knownInfo.isBasicCurrency) {
      return Option(new BasicCurrency(knownInfo))
    }

    None
  }
}
