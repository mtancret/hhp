package datatypes

case class DrugCount (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object DrugCount extends Enum[DrugCount] {
	case object DrugCount1 extends DrugCount ("1", "DrugCount1", 1)
	case object DrugCount2 extends DrugCount ("2", "DrugCount2", 2)
	case object DrugCount3 extends DrugCount ("3", "DrugCount3", 3)
	case object DrugCount4 extends DrugCount ("4", "DrugCount4", 4)
	case object DrugCount5 extends DrugCount ("5", "DrugCount5", 5)
	case object DrugCount6 extends DrugCount ("6", "DrugCount6", 6)
	case object DrugCount7p extends DrugCount ("7+", "DrugCount7p", 7)
  
	override val values = List (DrugCount1, DrugCount2, DrugCount3,
	    DrugCount4, DrugCount5, DrugCount6, DrugCount7p)
}
