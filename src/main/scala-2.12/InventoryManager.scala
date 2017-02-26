import items.DivinationCard
import items.currency.Currency

object InventoryManager {
  val rowCount: Int = Inventory.height
  val columnCount: Int = Inventory.width

  val rows: List[Int] = List.range(0, rowCount)
  val columns: List[Int] = List.range(0, columnCount)


  def emptyInventory(): Unit = {
    Stash.resetTab()
    Inventory.update()
    Stash.activateCurrencyTab()
    Thread sleep 200
    dumpCurrencies()
    Stash.activateEssenceTab()
    Thread sleep 200
    dumpEssences()
    Stash.activateDivinationTab()
    Thread sleep 200
    dumpDivinationCards()
  }

  def updateChaosTab(): Unit = {
    Stash.resetTab()
    Stash.activateChaosTab()
    Stash.chaosTab.update()
  }

  private def dumpCurrencies(): Unit = {
    Inventory.basicCurrencies.foreach((currency: Currency) => {
      Inventory.sendItemToStash(currency)
    })
  }

  private def dumpEssences(): Unit = {
    Inventory.essences.foreach((essence) => {
      Inventory.sendItemToStash(essence)
    })
  }

  private def dumpDivinationCards(): Unit = {
    Inventory.divinationCards.foreach((divinationCard: DivinationCard) => {
      Inventory.sendItemToStash(divinationCard)
    })
  }
}
