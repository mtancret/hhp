package datatypes

case class LengthOfStay (t: String, n: String, c: Double) extends EnumType[Double] (t, n, c)

object LengthOfStay extends Enum[LengthOfStay] {
	case object LengthOfStay0d extends LengthOfStay ("", "LengthOfStay0d", 0)
	case object LengthOfStay1d extends LengthOfStay ("1 day", "LengthOfStay1d", 1)
	case object LengthOfStay2d extends LengthOfStay ("2 days", "LengthOfStay2d", 2)
	case object LengthOfStay3d extends LengthOfStay ("3 days", "LengthOfStay3d", 3)
	case object LengthOfStay4d extends LengthOfStay ("4 days", "LengthOfStay4d", 4)
	case object LengthOfStay5d extends LengthOfStay ("5 days", "LengthOfStay5d", 5)
	case object LengthOfStay6d extends LengthOfStay ("6 days", "LengthOfStay6d", 6)
	case object LengthOfStay1_2w extends LengthOfStay ("1- 2 weeks", "LengthOfStay1_2w", 10.5)
	case object LengthOfStay2_4w extends LengthOfStay ("2- 4 weeks", "LengthOfStay2_4w", 21)
	case object LengthOfStay4_8w extends LengthOfStay ("4- 8 weeks", "LengthOfStay4_8w", 42)
	case object LengthOfStay8_12w extends LengthOfStay ("8-12 weeks", "LengthOfStay8_12w", 70)
	case object LengthOfStay12_26w extends LengthOfStay ("12-26 weeks", "LengthOfStay12_26w", 133)
	case object LengthOfStay26pw extends LengthOfStay ("26+ weeks", "LengthOfStay26pw", 200)
  
	val values = List (LengthOfStay0d, LengthOfStay1d, LengthOfStay2d,
	    LengthOfStay3d, LengthOfStay4d, LengthOfStay5d, LengthOfStay6d,
	    LengthOfStay1_2w, LengthOfStay2_4w, LengthOfStay4_8w, LengthOfStay8_12w,
	    LengthOfStay12_26w, LengthOfStay26pw)
}
