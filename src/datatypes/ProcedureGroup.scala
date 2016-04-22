package datatypes

case class ProcedureGroup (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object ProcedureGroup extends Enum[ProcedureGroup] {
	case object UnknownProcedureGroup extends
		ProcedureGroup ("", "UnknownProcedureGroup", -1)
	case object Med extends ProcedureGroup ("MED", "Med", 0)
	case object Em extends ProcedureGroup ("EM", "Em", 1)
	case object Scs extends ProcedureGroup ("SCS", "Scs", 2)
	case object Anes extends ProcedureGroup ("ANES", "Anes", 3)
	case object Rad extends ProcedureGroup ("RAD", "Rad", 4)
	case object Sds extends ProcedureGroup ("SDS", "Sds", 5)
	case object Pl extends ProcedureGroup ("PL", "Pl", 6)
	case object Sas extends ProcedureGroup ("SAS", "Sas", 7)
	case object Sus extends ProcedureGroup ("SUS", "Sus", 8)
	case object Sms extends ProcedureGroup ("SMS", "Sms", 9)
	case object Sis extends ProcedureGroup ("SIS", "Sis", 10)
	case object Sgs extends ProcedureGroup ("SGS", "Sgs", 11)
	case object Sns extends ProcedureGroup ("SNS", "Sns", 12)
	case object Seoa extends ProcedureGroup ("SEOA", "Seoa", 13)
	case object Srs extends ProcedureGroup ("SRS", "Srs", 14)
	case object Smcd extends ProcedureGroup ("SMCD", "Smcd", 15)
	case object So extends ProcedureGroup ("SO", "So", 16)
  
	val values = List (UnknownProcedureGroup, Med, Em, Scs, Anes,
	    Rad, Sds, Pl, Sas, Sus, Sms, Sis, Sgs, Sns, Seoa, Srs,
	    Smcd, So)
}
