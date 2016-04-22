package datatypes

case class CharlsonIndex (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object CharlsonIndex extends Enum[CharlsonIndex] {
	case object Index0 extends CharlsonIndex ("0", "Index0", 0)
	case object Index1_2 extends CharlsonIndex ("1-2", "Index1_2", 1)
	case object Index2_3 extends	CharlsonIndex ("2-3", "Index2_3", 2)
	case object Index3_4 extends CharlsonIndex ("3-4", "Index3_4", 3)
	case object Index4_5 extends CharlsonIndex ("4-5", "Index4_5", 4)
	case object Index5p extends CharlsonIndex ("5+", "Index5p", 5)
	
	val values = List (Index0, Index1_2, Index2_3, Index3_4,
	    Index4_5, Index5p)
	val max = values.map (_.code).max
}
