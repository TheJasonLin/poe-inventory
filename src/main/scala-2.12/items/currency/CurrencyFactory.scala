package items.currency

import structures.Position

object CurrencyFactory {
  def create(rarity: String, base: String, name: Option[String]): Option[Currency] = {
    if(base.contains("Essence")) {
      return Option(new Essence(rarity, base, name))
    }

    val basicCurrencyIndex = BasicCurrency.identifiers.indexOf(base)
    if(basicCurrencyIndex >= 0) {
      return Option(new BasicCurrency(rarity, base, name))
    }

    None
  }
}
