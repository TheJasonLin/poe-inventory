package parser.knowninfo

import constants.Rarity
import parser.item.currency.{BasicCurrency, Oil}
import parser.item.{Fragment, OwnerInfo, StackSize}


/**
  * A representation of all known information about an item. This should closely resemble the representation of an item
  * given by POE. The purpose is to standardize the information passed to the parser.ItemFactory, which will handle parsing
  * and generating the appropriate meta data
  */
class KnownInfo (var typeLine: String, var rarity: Rarity) {
  var id: Option[String] = None
  var name: Option[String] = None
  var itemLevel: Option[Int] = None
  var identified: Option[Boolean] = None
  var quality: Option[Int] = None
  var mapInfo: Option[MapInfo] = None
  var talismanTier: Option[Int] = None
  var implicits: Option[Seq[String]] = None
  var explicits: Option[Seq[String]] = None
  var ownerInfo: Option[OwnerInfo] = None
  var positionX: Option[Int] = None
  var positionY: Option[Int] = None
  var stackSize: Option[StackSize] = None
  var corrupted: Boolean = false

  /**
    * This cluster of checks can safely be used as soon as the KnownInfo is created,
    * because they only rely on typeLine and rarity
    */
  def isGem: Boolean = rarity == Rarity.GEM
  def isDivinationCard: Boolean = rarity == Rarity.DIVINATION_CARD
  def isMap: Boolean = typeLine.contains("Map")
  def isTalisman: Boolean = typeLine.contains("Talisman")
  def isLeaguestone: Boolean = typeLine.contains("Leaguestone")
  def isEssence: Boolean = typeLine.contains("Essence")
  def isBasicCurrency: Boolean = BasicCurrency.identifiers.indexOf(typeLine) >= 0
  def isFossil: Boolean = typeLine.contains("Fossil")
  def isResonator: Boolean = typeLine.contains("Resonator")
  def isFragment: Boolean = typeLine.contains("Scarab") || Fragment.typeLines.contains(typeLine)
  def isOil: Boolean = Oil.typeLines.contains(typeLine)

  /**
    * These methods should only be called after validating that the item is an equipment
    */
  object AssumeEquipment {
    def isFlask: Boolean = typeLine.contains("Flask")
    def isJewel: Boolean = typeLine.contains("Jewel")
  }

  /**
    * This cluser of checks infer information assuming that everything is populated to the best
    */
}
