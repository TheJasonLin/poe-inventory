package maps

import com.typesafe.scalalogging.Logger
import constants.Rarity
import parser.item.MapItem

import scala.collection.mutable.ListBuffer

object Solver {
  val log = Logger("Map.Solver")

  var actions: ListBuffer[MapAction] = _

  def getSolution(map: MapItem, issues: Set[MapIssue]): Seq[MapAction] = {
    actions = new ListBuffer[MapAction]()

    if (map.corrupted) {
      throw new IllegalArgumentException(s"Map (${map.typeLine}) is corrupted. Unhandled type")
    }

    if (issues.contains(MapIssue.UNIDENTIFIED)) {
      actions += MapAction.IDENTIFY
      return actions
    }

    if (issues.contains(MapIssue.QUALITY_LOW)) {
      actions += MapAction.SCOUR
      val qualityDiff = MapRequirements.minQuality - map.quality.getOrElse(0)
      val chiselCountDecimal: Double = qualityDiff.asInstanceOf[Double] / 5
      val chiselCount: Int = scala.math.ceil(chiselCountDecimal).asInstanceOf[Int]
      for (_ <- 0 until chiselCount) {
        actions += MapAction.CHISEL
      }
      applyRarityFromNormal()
      return actions
    }

    if (!issues.contains(MapIssue.BAD_MODS) && !issues.contains(MapIssue.BAD_PROPERTIES)) {
      log.error("HUGE ERROR. Encountered a bad mod I don't know how to solve.")
      return actions
    }

    if (map.rarity.compareTo(MapRequirements.rollRarity) < 0) {
      if (map.rarity != Rarity.NORMAL) {
        actions += MapAction.SCOUR
      }

      applyRarityFromNormal()
      return actions
    }

    // augment before rolling iff roll rarity = magic
    val singleModMagic = map.rarity == Rarity.MAGIC && map.explicits.length == 1
    val magicRollRarity = MapRequirements.rollRarity == Rarity.MAGIC
    if (issues.contains(MapIssue.BAD_PROPERTIES) && !issues.contains(MapIssue.BAD_MODS)&& magicRollRarity && singleModMagic) {
      actions += MapAction.AUGMENT
      return actions
    }

    roll()
    actions
  }

  private def applyRarityFromNormal(): Unit = {
    MapRequirements.rollRarity match {
      case Rarity.NORMAL =>
      case Rarity.MAGIC => actions += MapAction.TRANSMUTE
      case Rarity.RARE => actions += MapAction.ALCHEMY
      case other => log.error(s"unhandled roll rarity: $other")
    }
  }

  private def roll(): Unit = {
    MapRequirements.rollRarity match {
      case Rarity.NORMAL =>
      case Rarity.MAGIC => actions += MapAction.ALTERATION
      case Rarity.RARE => actions += MapAction.CHAOS
      case other => log.error(s"unhandled roll rarity: $other")
    }
  }
}
