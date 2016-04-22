package datatypes

case class Sex (t: String, n: String, c: Double) extends EnumType[Double] (t, n, c)

object Sex extends Enum[Sex] {
	case object SexUnknown extends Sex ("", "SexUnknown", 0.5)
	case object Female extends Sex ("F", "Female", 0.0)
	case object Male extends Sex ("M", "Male", 1.0)
  
	val values = List (SexUnknown, Female, Male)
}
