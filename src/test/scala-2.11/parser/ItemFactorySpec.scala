package parser

import constants.Rarity
import parser.item.{Item, OwnerInfo}
import parser.item.equipment.accessory.Ring
import parser.item.equipment.armour.Shield
import parser.item.equipment.weapon.{Dagger, Mace}
import parser.knowninfo.KnownInfo
import org.scalatest.{FlatSpec, Matchers}

class ItemFactorySpec extends FlatSpec with Matchers {

  "create" should "create a ring" in {
    val ringInfo: KnownInfo = getGhoulTurnInfo

    val item: Item = ItemFactory.create(ringInfo)

    assert(item.isInstanceOf[Ring])
    val ring: Ring = item.asInstanceOf[Ring]
    assert(ring.name.equals(ringInfo.name))
    assert(ring.implicits.size == 1)
    assert(ring.explicits.size == 6)
  }

  "create" should "create a dagger" in {
    val daggerInfo = getCarrionStinger

    val item = ItemFactory.create(daggerInfo)

    assert(item.isInstanceOf[Dagger])
    val dagger: Dagger = item.asInstanceOf[Dagger]
    assert(dagger.implicits.size == 1)
    assert(dagger.explicits.size == 6)
    assert(dagger.itemLevel == 78)
  }

  "create" should "create an unidentified Void Sceptre as a one handed weapon" in {
    val clipboard: String = "Rarity: Rare\nVoid Sceptre\n--------\nOne Handed Mace\nPhysical Damage: 50-76\nCritical Strike Chance: 6.20%\nAttacks per Second: 1.25\nWeapon Range: 9\n--------\nRequirements:\nStr: 104\nInt: 122\n--------\nSockets: R-B-B \n--------\nItem Level: 74\n--------\n40% increased Elemental Damage\n--------\nUnidentified"
    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)

    val item = ItemFactory.create(knownInfoOption.get)
    assert(item.isInstanceOf[Mace])
    assert(item.asInstanceOf[Mace].oneHanded)
  }

  "create" should "create a Colassal Tower Shield as a shield" in {
    val clipboard: String = "Rarity: Rare\nColossal Tower Shield\n--------\nChance to Block: 23%\nArmour: 632\n--------\nRequirements:\nStrength: 159\n--------\nSockets: R R \n--------\nItem Level: 74\n--------\n+13 to maximum Life\n--------\nUnidentified"
    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)

    val item = ItemFactory.create(knownInfoOption.get)
    assert(item.isInstanceOf[Shield])
    assert(item.height() == 4)
  }

  "create" should "create a Spirit Shield" in {
    val clipboard: String = "Rarity: Rare\nSuperior Fossilised Spirit Shield\n--------\nQuality: +5% (augmented)\nChance to Block: 22%\nEnergy Shield: 49 (augmented)\n--------\nRequirements:\nIntelligence: 141\n--------\nSockets: B B \n--------\nItem Level: 74\n--------\n16% increased Spell Damage\n--------\nUnidentified"
    val knownInfoOption: Option[KnownInfo] = ClipboardParser.parseKnownInfo(clipboard)
    assert(knownInfoOption.isDefined)

    val item = ItemFactory.create(knownInfoOption.get)
    assert(item.isInstanceOf[Shield])
    assert(item.height() == 2)
  }

  private def getGhoulTurnInfo: KnownInfo = {
    val ringInfo: KnownInfo = new KnownInfo("Diamond Ring", Rarity.RARE)
    ringInfo.name = Option("Ghoul Turn")
    ringInfo.itemLevel = Option(77)
    ringInfo.identified = Option(true)
    ringInfo.implicits = Option(
      List(
        "29% increased Global Critical Strike Chance"
      )
    )
    ringInfo.explicits = Option(
      List(
        "Adds 3 to 40 Lightning Damage to Attacks",
        "+390 to Accuracy Rating",
        "+22 to maximum Energy Shield",
        "+13% to all Elemental Resistances",
        "+40% to Fire Resistance",
        "20% increased maximum Energy Shield"
      )
    )

    ringInfo
  }

  private def getCarrionStinger: KnownInfo = {
    val info: KnownInfo = new KnownInfo("Royal Skean", Rarity.RARE)
    info.name = Option("Carrion Stinger")
    info.itemLevel = Option(78)
    info.identified = Option(true)
    info.quality = Option(20)
    info.implicits = Option(
      List("30% increased Global Critical Strike Chance")
    )
    info.explicits = Option(
      List(
        "Adds 42 to 82 Fire Damage",
        "Adds 5 to 91 Lightning Damage to Spells",
        "19% increased Attack Speed",
        "107% increased Critical Strike Chance for Spells",
        "+23% to Global Critical Strike Multiplier",
        "43% increased Spell Damage"
      )
    )
    info.ownerInfo = Option(new OwnerInfo("foxlin", "SparkTrain", "~b/o 30 chaos", "$$", "Standard"))

    info
  }
}
