package datatypes

case class Specialty (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object Specialty extends Enum[Specialty] {
	case object UnknownSpecialty extends Specialty ("", "UnknownSpecialty", -1)
	case object Surgery extends Specialty ("Surgery", "Surgery", 0)
	case object Internal extends Specialty ("Internal", "Internal", 1)
	case object Laboratory extends Specialty ("Laboratory", "Laboratory", 2)
	case object Pediatrics extends Specialty ("Pediatrics", "Pediatrics", 3)
	case object Rehabilitation extends Specialty ("Rehabilitation", "Rehabilitation", 4)
	case object DiagnosticImaging extends
		Specialty ("Diagnostic Imaging", "DiagnosticImaging", 5)
	case object Anesthesiology extends Specialty ("Anesthesiology", "Anesthesiology", 6)
	case object Emergency extends Specialty ("Emergency", "Emergency", 7)
	case object GeneralPractice extends
		Specialty ("General Practice", "GeneralPractice", 8)
	case object Other extends Specialty ("Other", "Other", 9)
	case object Pathology extends Specialty ("Pathology", "Pathology", 10)
	case object Ambulance extends Specialty ("Ambulance", "Ambulance", 11)
	case object ObstetricsGynecology extends
		Specialty ("Obstetrics and Gynecology", "ObstetricsGynecology", 12)
  
	val values = List (UnknownSpecialty, Surgery, Internal, Laboratory,
	    Pediatrics, Rehabilitation, DiagnosticImaging, Anesthesiology,
	    Emergency, GeneralPractice, Other, Pathology, Ambulance,
	    ObstetricsGynecology)
}
