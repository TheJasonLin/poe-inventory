package config

object CurrencyValues {
  private val Bauble = 1f/10
  private val GCP = 1f/0.8
  private val Chisel = 1f/2.5
  private val Alt = 1f/12
  private val Annul = 10
  private val Chance = 1f/7.7
  private val Exalt = 95
  private val Mirror_inExalts = 200
  private val Regal = 1f/1.8
  private val Alch = 1f/3
  private val Blessed = 1f/2.7
  private val Divine = 18
  private val Jeweller = 1f/8
  private val Fusing = 1f/2
  private val Chromat = 1f/10
  private val Perandus = 1f/42
  private val Silver = 1f/4.5
  private val Scour = 1f/1.6
  private val Regret = 1f/1
  private val Vaal = 1f/0.8
  private val Apprentice = 1f/1.3
  private val Journyman = 2
  private val Master = 5

  val values:Map[String, Double] = Map[String, Double](
    "Scroll Fragment" -> 0,
    "Scroll of Wisdom" -> 0,
    "Portal Scroll" -> 0,

    "Blacksmith's Whetstone" -> 0,
    "Armourer's Scrap" -> 0,
    "Glassblower's Bauble" -> Bauble,
    "Gemcutter's Prism" -> GCP,
    "Cartographer's Chisel" -> Chisel,

    "Orb of Transmutation" -> 0,
    "Orb of Alteration" -> Alt,
    "Orb of Annulment" -> Annul,
    "Orb of Chance" -> Chance,
    "Exalted Orb" -> Exalt,
    "Mirror of Kalandra" -> Mirror_inExalts * Exalt,
    "Regal Orb" -> Regal,
    "Orb of Alchemy" -> Alch,
    "Chaos Orb" -> 1,
    "Blessed Orb" -> Blessed,
    // new row
    "Transmutation Shard" -> 0/20,
    "Alteration Shard" -> Alt/20,
    "Annulment Shard" -> Annul/20,
    "Orb of Augmentation" -> 0,
    "Exalted Shard" -> Exalt/20,
    "Mirror Shard" -> (Mirror_inExalts * Exalt)/20,
    "Regal Shard" -> Regal/20,
    "Alchemy Shard" -> Alch/20,
    "Chaos Shard" -> 1/20,
    "Divine Orb" -> Divine,

    "Jeweller's Orb" -> Jeweller,
    "Orb of Fusing" -> Fusing,
    "Chromatic Orb" -> Chromat,
    // new row
    "Perandus Coin" -> Perandus,
    "Silver Coin" -> Silver,

    "Scouring Orb" -> Scour,
    "Orb of Regret" -> Regret,
    "Vaal Orb" -> Vaal,
    // new row
    "Apprentice Cartographer's Sextant" -> Apprentice,
    "Journeyman Cartographer's Sextant" -> Journyman,
    "Master Cartographer's Sextant" -> Master
  )
}
