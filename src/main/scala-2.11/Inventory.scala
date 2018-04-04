import com.poe.constants.Rarity
import com.poe.parser.item.currency.{BasicCurrency, Currency, Essence}
import com.poe.parser.item.equipment.{Equipment, Flask}
import com.poe.parser.item.equipment.accessory.{Accessory, Quiver, Talisman}
import com.poe.parser.item._
import com.poe.parser.item.equipment.armour.{Armour, Shield}
import com.poe.parser.item.equipment.weapon.{Dagger, Wand}
import structures.{PixelPosition, Position, ScreenItem}

object Inventory extends Container {
  val xCeil: Int = Config.INVENTORY_BOTTOM_RIGHT_COORD._1
  val yCeil: Int = Config.INVENTORY_BOTTOM_RIGHT_COORD._2
  val pixelWidth: Int = xCeil - xBase().get
  val pixelHeight: Int = yCeil - yBase().get

  override def xBase(): Option[Int] = Option(Config.INVENTORY_TOP_LEFT_COORD._1)

  override def yBase() = Option(Config.INVENTORY_TOP_LEFT_COORD._2)

  override def cellRadius() = Option(Config.INVENTORY_CELL_RADIUS)

  override def xCellOffset(): Option[Double] = Option(pixelWidth.asInstanceOf[Double] / (width().get - 1).asInstanceOf[Double])

  override def width() = Option(Config.INVENTORY_WIDTH)

  override def yCellOffset(): Option[Double] = Option(pixelHeight.asInstanceOf[Double] / (height().get - 1).asInstanceOf[Double])

  override def height() = Option(Config.INVENTORY_HEIGHT)

  /**
    * Moves an item to an allocation if possible.
    * requirements: should already be on the tab
    *
    * @param item
    * @param allocation
    * @return
    */
  def sendItemToAllocation(item: ScreenItem, allocation: Allocation): Boolean = {
    if (item.positions.isEmpty) throw new IllegalArgumentException("Item has no position")
    val currentTabOption: Option[Tab] = Stash.currentTab()
    if (currentTabOption.isEmpty) throw new IllegalArgumentException("Current Tab layout isn't defined")
    val currentTab: Tab = currentTabOption.get
    // get location in the current tab that's open, given the provided allocation
    val positionInfoOption: Option[(PixelPosition, Position)] = currentTab.findOpenPositionInAllocation(item, allocation)
    if (positionInfoOption.isEmpty) return false
    val dropPosition: PixelPosition = positionInfoOption.get._1
    val topLeftPosition: Position = positionInfoOption.get._2

    // move item from A to B
    val moveSuccess: Boolean = Clicker.move(getPixels(item.position.get), dropPosition)
    // inform tab of new item
    if (moveSuccess) {
      currentTab.addItem(item, topLeftPosition)
    }
    moveSuccess
  }

  def basicCurrencies: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[BasicCurrency]
    })
  }

  def essences: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Essence]
    })
  }

  def divinationCards: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[DivinationCard]
    })
  }

  def maps: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[MapItem]
    })
  }

  def leaguestones: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Leaguestone]
    })
  }

  def talismans: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Talisman]
    })
  }

  def qualityFlasks: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Flask]
    }).filter((item: ScreenItem) => {
      val flask = item.data.asInstanceOf[Flask]
      flask.quality.isDefined && flask.quality.get > 0
    })
  }

  def qualityGems: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      item.data.isInstanceOf[Gem]
    }).filter((item: ScreenItem) => {
      val gem = item.data.asInstanceOf[Gem]
      gem.quality.isDefined && gem.quality.get > 0
    })
  }

  def miscItems: Seq[ScreenItem] = {
    val keys: Seq[String] = Stash.miscAllocations.keys.toList
    items.filter((item: ScreenItem) => {
      keys.contains(item.data.typeLine)
    })
  }

  final private val fragmentTypelines: Seq[String] = Seq[String](
    "Offering to the Goddess",
    "Sacrifice at Dusk",
    "Sacrifice at Midnight",
    "Sacrifice at Dawn",
    "Sacrifice at Noon",
    "Divine Vessel",
    // TODO missing 2
    "Mortal Hope",
    "Mortal Ignorance",
    // TODO what are the others?
    "Fragment of the Minotaur",
    "Fragment of the Chimera",
    "Fragment of the Phoenix",
    "Fragment of the Hydra",
    "Splinter of Chayula",
    "Splinter of Tul",
    "Splinter of Xoph",
    "Splinter of Esh",
    "Splinter of Uul-Netol",
    // TODO is this right?
    "Chayula's Breachstone",
    "Tul's Breachstone",
    "Xoph's Breachstone",
    "Esh's Breachstone",
    "Uul-Netol's Breachstone"
  )

  // TODO make this based on parser results
  def fragments: Seq[ScreenItem] = {
    items.filter((item: ScreenItem) => {
      fragmentTypelines.contains(item.data.typeLine)
    })
  }

  def fullSetEquipment(chaos: Boolean, regal: Boolean): Seq[ScreenItem] = {
    if (!chaos && !regal) throw new IllegalArgumentException("Need at least one to be true")
    val min = if (chaos) 60 else 75
    val max = if (!regal) 75 else 100

    items
      // make sure it's rare
      .filter((item: ScreenItem) => item.data.rarity == Rarity.RARE)
      // make sure it's equipment
      .filter((item: ScreenItem) => item.data.isInstanceOf[Equipment])
      // make sure within item level range
      .filter((item: ScreenItem) => {
      val equipment: Equipment = item.data.asInstanceOf[Equipment]
      equipment.itemLevel >= min && equipment.itemLevel <= max
    })
      // make sure its unidentified
      .filter((item: ScreenItem) => {
      val equipment: Equipment = item.data.asInstanceOf[Equipment]
      !equipment.identified
    })
      // make sure it's desired for chaos recipe
      .filter((item: ScreenItem) => {
      val equipment: Equipment = item.data.asInstanceOf[Equipment]
      val isArmour: Boolean = equipment.isInstanceOf[Armour] && !equipment.isInstanceOf[Shield]
      val isAccessory: Boolean = equipment.isInstanceOf[Accessory] && !equipment.isInstanceOf[Quiver]
      val isSmallWeapon: Boolean = equipment.isInstanceOf[Dagger] || equipment.isInstanceOf[Wand]
      isArmour || isAccessory || isSmallWeapon
    })
  }

  def findCurrency(name: String): Option[ScreenItem] = {
    basicCurrencies.find((item: ScreenItem) => {
      item.data.typeLine == name
    })
  }
}
