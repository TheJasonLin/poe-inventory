package items.currency

import structures.Position

object CurrencyFactory {
  def create(position: Position, rarity: String, base: String, name: Option[String]): Option[Currency] = {
    if(base.contains("Essence")) {
      return Option(new Essence(position, rarity, base, name))
    }

    val basicCurrencyIndex = BasicCurrency.identifiers.indexOf(base)
    if(basicCurrencyIndex >= 0) {
      return Option(new BasicCurrency(position, rarity, base, name))
    }

    None
  }
}
