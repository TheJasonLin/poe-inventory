package config

object CurrencyValues {
  private val Bauble = 1f/6
  private val GCP = 5f/7
  private val Chisel = 1f/4.9
  private val Alt = 1f/21
  private val Annul = 4
  private val Chance = 1f/10.9
  private val Exalt = 41
  private val Mirror_inExalts = 62.5
  private val Regal = 0.9375f
  private val Alch = 10f/34
  private val Blessed = 0.4f
  private val Divine = 6.5f
  private val Jeweller = 1f/10.8
  private val Fusing = 1f/2.7
  private val Chromat = 1f/14.5
  private val Perandus = 0
  private val Silver = 1f/3.7
  private val Scour = 1f/2.6
  private val Regret = 1f/1.7
  private val Vaal = 1f/1.9
  private val Apprentice = 1f/1.75
  private val Journyman = 1.8f
  private val Master = 3.66f

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
