package constants

import java.util.Optional

import org.scalatest.{FlatSpec, Matchers}

class RaritySpec extends FlatSpec with Matchers {
  "Rarity" should "return Rarity with key" in {
    val valueType: Optional[Rarity] = Rarity.getByKey(5)
    assert(valueType.isPresent)
    assert(valueType.get() == Rarity.CURRENCY)
  }
}
