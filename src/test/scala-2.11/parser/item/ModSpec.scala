package parser.item

import org.scalatest.{FlatSpec, Matchers}

class ModSpec extends FlatSpec with Matchers {
  "Mod" should "parse single value mod string" in {
    val modString: String = "+15 to all Attributes"
    val mod: Mod = Mod.parse(modString)

    assert(mod.text == "+# to all Attributes")
    assert(mod.values.size == 1)
    assert(mod.values.head == 15)
  }

  "Mod" should "parse double value mod string" in {
    val modString: String = "Adds 10 to 22 Physical Damage to Attacks"
    val mod: Mod = Mod.parse(modString)

    assert(mod.text == "Adds # to # Physical Damage to Attacks")
    assert(mod.values.size == 2)
    assert(mod.values.head == 10)
    assert(mod.values(1) == 22)
  }

  "Mod" should "parse value with special characters" in {
    val mod: Mod = Mod.parse("+40% to Fire Resistance")

    assert(mod.text == "+#% to Fire Resistance")
    assert(mod.values.size == 1)
    assert(mod.values.head == 40)
  }

  "Mod" should "parse negative value and substitute +" in {
    val mod: Mod = Mod.parse("-14% to Cold Resistance")

    assert(mod.text == "+#% to Cold Resistance")
    assert(mod.values.size == 1)
    assert(mod.values.head == -14)
  }
}
