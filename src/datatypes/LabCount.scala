package datatypes

case class LabCount (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object LabCount extends Enum[LabCount] {
	override val values = Nil
	val max = 10
	
	override def forString (countStr: String): LabCount = LabCount (
	    countStr,
	    countStr,
	    countStr match {
	      case "10+" => 10
	      case _ => countStr.toInt
	    }
	)
}