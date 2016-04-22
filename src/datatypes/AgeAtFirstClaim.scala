package datatypes

case class AgeAtFirstClaim (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object AgeAtFirstClaim extends Enum[AgeAtFirstClaim] {
	case object AgeUnknown extends AgeAtFirstClaim ("", "AgeUnknown", 36)
	case object AgeAtFirstClaim0_9 extends
		AgeAtFirstClaim ("0-9", "AgeAtFirstClaim0_9", 5)
	case object AgeAtFirstClaim10_19 extends
		AgeAtFirstClaim ("10-19", "AgeAtFirstClaim10_19", 15)
	case object AgeAtFirstClaim20_29 extends
		AgeAtFirstClaim ("20-29", "AgeAtFirstClaim20_29", 25)
	case object AgeAtFirstClaim30_39 extends
		AgeAtFirstClaim ("30-39", "AgeAtFirstClaim30_39", 35)
	case object AgeAtFirstClaim40_49 extends
		AgeAtFirstClaim ("40-49", "AgeAtFirstClaim40_49", 45)
	case object AgeAtFirstClaim50_59 extends
		AgeAtFirstClaim ("50-59", "AgeAtFirstClaim50_59", 55)
	case object AgeAtFirstClaim60_69 extends
		AgeAtFirstClaim ("60-69", "AgeAtFirstClaim60_69", 65)
	case object AgeAtFirstClaim70_79 extends
		AgeAtFirstClaim ("70-79", "AgeAtFirstClaim70_79", 75)
	case object AgeAtFirstClaim80p extends
		AgeAtFirstClaim ("80+", "AgeAtFirstClaim80p", 85)
  
	override val values = List (AgeUnknown, AgeAtFirstClaim0_9, AgeAtFirstClaim10_19,
	    AgeAtFirstClaim20_29, AgeAtFirstClaim30_39, AgeAtFirstClaim40_49,
	    AgeAtFirstClaim50_59, AgeAtFirstClaim60_69, AgeAtFirstClaim70_79,
	    AgeAtFirstClaim80p)
}
