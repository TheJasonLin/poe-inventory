package items.equipment.weapon

class Dagger(
              rarity: String,
              base: String,
              name: Option[String],
              itemLevel: Int,
              identified: Boolean,
              quality: Int
            ) extends Weapon(rarity, base, name, itemLevel, identified, quality) {

}

object Dagger {
  val identifiers = Array(
    "Shank",
    "Knife",
    "Stiletto",
    "Kris",
    "Skean",
    "Dagger",
    "Poignard",
    "Blade",
    "Trisula",
    "Ambusher",
    "Sai"
  )

  val oneHandedBases = Array(
    "Boot Blade"
  )
}
