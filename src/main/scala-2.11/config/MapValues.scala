package config

object MapValues {
  val MaxMapTier: Int = 16
  val MinMapTierWithValue: Int = 10
  private val cc: Double = CurrencyValues.values("Cartographer's Chisel")
  private val a: Double = CurrencyValues.values("Orb of Alchemy")
  val values: Map[String, Double] = Map[String, Double](
    "Shaped Dunes Map" -> 3,
    "Shaped Mesa Map" -> 4*cc,
    "Colonnade Map" -> 2*cc,
    "Underground River Map" -> a,
    "Port Map" -> a,
    "Shaped Racecourse Map" -> 4*cc,
    "Shaped Canyon Map" -> 3,
    "Shaped Mud Geyser Map" -> 2*cc,
    "Shaped Tropical Island Map" -> 4,
    "Shaped Reef Map" -> 4
  )
}
