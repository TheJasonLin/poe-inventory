package parser

import constants.Rarity
import parser.knowninfo.{KnownInfo, MapInfo}
import org.scalatest.{FlatSpec, Matchers}

class ClipboardParserSpec extends FlatSpec with Matchers {
  "parseKnownInfo" should "parse normal map" in {
    val clipboard: String = "Rarity: Normal\nArid Lake Map\n--------\nMap Tier: 3\n--------\nItem Level: 78\n--------\nTravel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once."

    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)
    val knownInfo = knownInfoOption.get

    assert(knownInfo.rarity == Rarity.NORMAL)
    assert(knownInfo.typeLine == "Arid Lake Map")
    assert(knownInfo.name.isEmpty)
    assert(knownInfo.itemLevel.contains(78))
    assert(knownInfo.identified.contains(true))
    assert(knownInfo.quality.isEmpty)
    val expectedMapInfo = MapInfo(3, 0, 0, 0)
    assert(knownInfo.mapInfo.contains(expectedMapInfo))
    assert(knownInfo.talismanTier.isEmpty)
    assert(knownInfo.implicits.isEmpty)
    assert(knownInfo.explicits.isEmpty)
    assert(knownInfo.ownerInfo.isEmpty)
    assert(!knownInfo.corrupted)
  }

  "parseKnownInfo" should "parse magic map" in {
    val clipboard: String = "Rarity: Magic\nMirrored Factory Map of Impotence\n--------\nMap Tier: 2\nItem Quantity: +28% (augmented)\nItem Rarity: +14% (augmented)\nMonster Pack Size: +10% (augmented)\n--------\nItem Level: 69\n--------\nMonsters reflect 13% of Elemental Damage\nPlayers have 15% less Area of Effect\n--------\nTravel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once."

    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)
    val knownInfo = knownInfoOption.get

    val expectedExplicits: Seq[String] = Array(
      "Monsters reflect 13% of Elemental Damage",
      "Players have 15% less Area of Effect"
    )

    assert(knownInfo.rarity == Rarity.MAGIC)
    assert(knownInfo.typeLine == "Mirrored Factory Map of Impotence")
    assert(knownInfo.name.isEmpty)
    assert(knownInfo.itemLevel.contains(69))
    assert(knownInfo.identified.contains(true))
    assert(knownInfo.quality.isEmpty)
    val expectedMapInfo = MapInfo(2, 28, 14, 10)
    assert(knownInfo.mapInfo.contains(expectedMapInfo))
    assert(knownInfo.talismanTier.isEmpty)
    assert(knownInfo.implicits.isEmpty)
    assert(knownInfo.explicits.contains(expectedExplicits))
    assert(knownInfo.ownerInfo.isEmpty)
    assert(!knownInfo.corrupted)
  }

  "parseKnownInfo" should "parse rare map" in {
    val clipboard: String = "Rarity: Rare\nGrim Waste\nShaped Dunes Map\n--------\nMap Tier: 10\nItem Quantity: +75% (augmented)\nItem Rarity: +36% (augmented)\nMonster Pack Size: +24% (augmented)\nQuality: +5% (augmented)\n--------\nItem Level: 81\n--------\nArea is inhabited by 2 additional Rogue Exiles\nPlayers are Cursed with Temporal Chains\nSlaying Enemies close together can attract monsters from Beyond\n+20% Monster Chaos Resistance\n+30% Monster Elemental Resistance\nPlayers gain 40% reduced Flask Charges\n--------\nTravel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once."

    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)
    val knownInfo = knownInfoOption.get

    val expectedExplicits: Seq[String] = Array(
      "Area is inhabited by 2 additional Rogue Exiles",
      "Players are Cursed with Temporal Chains",
      "Slaying Enemies close together can attract monsters from Beyond",
      "+20% Monster Chaos Resistance",
      "+30% Monster Elemental Resistance",
      "Players gain 40% reduced Flask Charges"
    )

    assert(knownInfo.rarity == Rarity.RARE)
    assert(knownInfo.typeLine == "Shaped Dunes Map")
    assert(knownInfo.name.contains("Grim Waste"))
    assert(knownInfo.itemLevel.contains(81))
    assert(knownInfo.identified.contains(true))
    assert(knownInfo.quality.contains(5))
    val expectedMapInfo = MapInfo(10, 75, 36, 24)
    assert(knownInfo.mapInfo.contains(expectedMapInfo))
    assert(knownInfo.talismanTier.isEmpty)
    assert(knownInfo.implicits.isEmpty)
    assert(knownInfo.explicits.contains(expectedExplicits))
    assert(knownInfo.ownerInfo.isEmpty)
    assert(!knownInfo.corrupted)
  }

  "parseKnownInfo" should "parse corrupted map" in {
    val clipboard: String = "Rarity: Rare\nRapture Caress\nEmbroidered Gloves\n--------\nQuality: +8% (augmented)\nEnergy Shield: 55 (augmented)\n--------\nRequirements:\nLevel: 36\nInt: 54\n--------\nSockets: W-W B \n--------\nItem Level: 42\n--------\nTrigger Word of Flames on Hit\n--------\n1 Life Regenerated per second\n+15 to maximum Mana\n11% increased Energy Shield\n+22 to maximum Energy Shield\n+20% to Cold Resistance\n+26% to Lightning Resistance\n--------\nCorrupted"
    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)
    val knownInfo = knownInfoOption.get
    assert(knownInfo.corrupted)
  }

  "parseKnownInfo" should "parse shaped map" in {
    val clipboard: String = "Rarity: Normal\nShaped Lookout Map\n--------\nMap Tier: 6 (augmented)\n--------\nItem Level: 77\n--------\nTravel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once."
    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)
    val knownInfo = knownInfoOption.get
    assert(knownInfo.mapInfo.get.tier == 6)
  }

  "parseKnownInfo" should "parse Stack of Chromatic Orb" in {
    val clipboard: String = "Rarity: Currency\nChromatic Orb\n--------\nStack Size: 15/20\n--------\nReforges the colour of sockets on an item\n--------\nRight click this item then left click a socketed item to apply it.\nShift click to unstack."

    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)
    val knownInfo = knownInfoOption.get

    assert(knownInfo.stackSize.isDefined)
    val stackSize = knownInfo.stackSize.get
    assert(stackSize.size == 15)
    assert(stackSize.max == 20)
  }

  "parseKnownInfo" should "parse Rare Ring" in {
    val clipboard: String = "Rarity: Rare\nBlood Hold\nDiamond Ring\n--------\nRequirements:\nLevel: 48\n--------\nItem Level: 75\n--------\n29% increased Global Critical Strike Chance\n--------\n+11 to Strength\n+62 to maximum Life\n+15% to all Elemental Resistances\n+34% to Lightning Resistance\nAdds 12 to 20 Cold Damage to Attacks"
    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)
    val knownInfo = knownInfoOption.get

    val expectedImplicits: Seq[String] = Array(
      "29% increased Global Critical Strike Chance"
    )
    val expectedExplicits: Seq[String] = Array(
      "+11 to Strength",
      "+62 to maximum Life",
      "+15% to all Elemental Resistances",
      "+34% to Lightning Resistance",
      "Adds 12 to 20 Cold Damage to Attacks"
    )

    assert(knownInfo.rarity == Rarity.RARE)
    assert(knownInfo.name.contains("Blood Hold"))
    assert(knownInfo.typeLine.contains("Diamond Ring"))
    assert(knownInfo.itemLevel.contains(75))
    assert(knownInfo.implicits.contains(expectedImplicits))
    assert(knownInfo.explicits.contains(expectedExplicits))
  }

  "parseKnownInfo" should "parse corrupted unique item" in {
    val clipboard: String = "Rarity: Unique\nPresence of Chayula\nOnyx Amulet\n--------\nRequirements:\nLevel: 60\n--------\nItem Level: 72\n--------\n+1 to Maximum Frenzy Charges\n--------\n30% increased Rarity of Items found\n+60% to Chaos Resistance\nCannot be Stunned\n20% of Maximum Life Converted to Energy Shield\n--------\nThe dreamer stirs, the world trembles. \n--------\nCorrupted"
    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)
    val knownInfo = knownInfoOption.get

    val expectedImplicits: Seq[String] = Array(
      "+1 to Maximum Frenzy Charges"
    )
    val expectedExplicits: Seq[String] = Array(
      "30% increased Rarity of Items found",
      "+60% to Chaos Resistance",
      "Cannot be Stunned",
      "20% of Maximum Life Converted to Energy Shield"
    )

    assert(knownInfo.rarity == Rarity.UNIQUE)
    assert(knownInfo.name.contains("Presence of Chayula"))
    assert(knownInfo.typeLine.contains("Onyx Amulet"))
    assert(knownInfo.itemLevel.contains(72))
    assert(knownInfo.implicits.contains(expectedImplicits))
    assert(knownInfo.explicits.contains(expectedExplicits))
    assert(knownInfo.corrupted)
  }

  "parseKnownInfo" should "parse item with two implicits" in {
    val clipboard: String = "Rarity: Unique\nEyes of the Greatwolf\nGreatwolf Talisman\n--------\nRequirements:\nLevel: 52\n--------\nItem Level: 78\n--------\nTalisman Tier: 4\n--------\n46% increased Lightning Damage\n10% additional Physical Damage Reduction\n--------\nImplicit Modifier magnitudes are doubled\n--------\nI am but a vessel for a greater force.\nIt acts through me. Speaks through me.\nDecides what lives and dies through me.\nAnd will change the world through me.\n--------\nCorrupted"
    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)
    val knownInfo = knownInfoOption.get

    val expectedImplicits: Seq[String] = Array(
      "46% increased Lightning Damage",
      "10% additional Physical Damage Reduction"
    )
    val expectedExplicits: Seq[String] = Array(
      "Implicit Modifier magnitudes are doubled"
    )

    assert(knownInfo.rarity == Rarity.UNIQUE)
    assert(knownInfo.name.contains("Eyes of the Greatwolf"))
    assert(knownInfo.typeLine.contains("Greatwolf Talisman"))
    assert(knownInfo.itemLevel.contains(78))
    assert(knownInfo.talismanTier.contains(4))
    assert(knownInfo.implicits.contains(expectedImplicits))
    assert(knownInfo.explicits.contains(expectedExplicits))
    assert(knownInfo.corrupted)
  }

  "parseKnownInfo" should "parse unidentified Void Sceptre" in {
    val clipboard: String = "Rarity: Rare\nVoid Sceptre\n--------\nOne Handed Mace\nPhysical Damage: 50-76\nCritical Strike Chance: 6.20%\nAttacks per Second: 1.25\nWeapon Range: 9\n--------\nRequirements:\nStr: 104\nInt: 122\n--------\nSockets: R-B-B \n--------\nItem Level: 74\n--------\n40% increased Elemental Damage\n--------\nUnidentified"
    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)
    val knownInfo = knownInfoOption.get

    assert(knownInfo.typeLine.contains("Void Sceptre"))
  }

  "parseKnownInfo" should "parse Reinforced Steel Net" in {
    val clipboard: String = "Rarity: Currency\nReinforced Steel Net\n--------\nStack Size: 87/100\nNet Tier: 8\n--------\nEffective against Beasts of levels 60 to 75.\nActivate to use this type of Net when capturing Beasts.\n--------\nShift click to unstack."
    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)
    val knownInfo = knownInfoOption.get

    assert(knownInfo.stackSize.isDefined)
    assert(knownInfo.stackSize.get.max == 100)
    assert(knownInfo.stackSize.get.size == 87)
  }
}
