package constants

import java.util.Optional

import org.scalatest.{FlatSpec, Matchers}

class ValueTypeSpec extends FlatSpec with Matchers {
  "ValueType" should "return ValueType with key" in {
    val valueType: Optional[ValueType] = ValueType.getByKey(4)
    assert(valueType.isPresent)
    assert(valueType.get() == ValueType.FIRE)
  }
}
