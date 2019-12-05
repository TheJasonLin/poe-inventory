package parser.item

import scala.util.Try

case class Mod(text: String, values: Seq[Int])

object Mod {
  def parse(modString: String): Mod = {
    val text: String = modString
      .replaceAll("[0-9]+", "#")
      .replaceAll("-", "+")

    val values: Seq[Int] = modString
      .replaceAll("[^-?0-9]+", " ")
      .trim()
      .split(" ")
      .toSeq
      .map((valueString: String) => {
        Try(valueString.toInt)
      })
      .filter(_.isSuccess)
      .map(_.get)

    Mod(text, values)
  }
}
