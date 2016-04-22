package datatypes

case class PlaceSvc (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object PlaceSvc extends Enum[PlaceSvc] {
	case object UnknownPlaceSvc extends PlaceSvc ("", "UnknownPlaceSvc", -1)
	case object Office extends PlaceSvc ("Office", "Office", 0)
	case object IndependentLab extends PlaceSvc ("Independent Lab", "IndependentLab", 1)
	case object OutpatientHospital extends
		PlaceSvc ("Outpatient Hospital", "OutpatientHospital", 2)
	case object InpatientHospital extends
		PlaceSvc ("Inpatient Hospital", "InpatientHospital", 3)
	case object UrgentCare extends PlaceSvc ("Urgent Care", "UrgentCare", 4)
	case object Ambulance extends PlaceSvc ("Ambulance", "Ambulance", 5)
	case object Home extends PlaceSvc ("Home", "Home", 6)
	case object Other extends PlaceSvc ("Other", "Other", 7)
  
	val values = List (UnknownPlaceSvc, Office, IndependentLab,
	    OutpatientHospital, InpatientHospital, UrgentCare, Ambulance,
	    Home, Other)
}
