package datatypes

case class Month (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object Month extends Enum[Month] {
	case object Month0 extends Month ("0months", "Month0", 0)
	case object Month1 extends Month ("1month", "Month1", 1)
	case object Month2 extends Month ("2months", "Month2", 2)
	case object Month3 extends Month ("3months", "Month3", 3)
	case object Month4 extends Month ("4months", "Month4", 4)
	case object Month5 extends Month ("5months", "Month5", 5)
	case object Month6 extends Month ("6months", "Month6", 6)
	case object Month7 extends Month ("7months", "Month7", 7)
	case object Month8 extends Month ("8months", "Month8", 8)
	case object Month9 extends Month ("9months", "Month9", 9)
	case object Month10 extends Month ("10months", "Month10", 10)
	case object Month11 extends Month ("11months", "Month11", 11)
	case object Month12 extends Month ("12months", "Month12", 12)
	case object UnknownMonth extends Month ("", "UnknownMonth", 12)
  
	val values = List (Month0, Month1, Month2, Month3, Month4,
	    Month5, Month6, Month7, Month8, Month9, Month10,
	    Month11, Month12, UnknownMonth)
}
