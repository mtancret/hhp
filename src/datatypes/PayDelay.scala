package datatypes

case class PayDelay (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object PayDelay extends Enum[PayDelay] {
	override val values = Nil
	val max = 162.0
	
	override def forString (delayStr: String): PayDelay = PayDelay (
	    delayStr,
	    delayStr,
	    delayStr match {
	      case "162+" => 162
	      case _ => delayStr.toInt
	    }
	)
}
