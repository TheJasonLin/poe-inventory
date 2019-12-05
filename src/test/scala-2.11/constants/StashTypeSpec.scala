package constants

import java.util.Optional

import org.scalatest.{FlatSpec, Matchers}

class StashTypeSpec extends FlatSpec with Matchers {
  "StashType" should "return StashType with key" in {
    val stashType: Optional[StashType] = StashType.getByKey("NormalStash")
    assert(stashType.isPresent)
    assert(stashType.get() == StashType.NORMAL)
  }
}
