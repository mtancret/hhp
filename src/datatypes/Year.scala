package datatypes

case class Year (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object Year extends Enum[Year] {
	case object Year1 extends Year ("Y1", "Year1", 1)
	case object Year2 extends Year ("Y2", "Year2", 2)
	case object Year3 extends Year ("Y3", "Year3", 3)
	case object Year4 extends Year ("Y4", "Year4", 4)
	case object YearUnknown extends Year("YearUnknown", "YearUnknown", -1)
  
	val values = List (Year1, Year2, Year3, Year4, YearUnknown)
}