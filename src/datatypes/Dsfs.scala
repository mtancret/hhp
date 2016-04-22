package datatypes

case class Dsfs (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object Dsfs extends Enum[Dsfs] {
	case object Dsfs0_1m extends Dsfs ("0- 1 month", "Dsfs0_1m", 0)
	case object Dsfs1_2m extends Dsfs ("1- 2 months", "Dsfs1_2m", 1)
	case object Dsfs2_3m extends Dsfs ("2- 3 months", "Dsfs2_3m", 2)
	case object Dsfs3_4m extends Dsfs ("3- 4 months", "Dsfs3_4m", 3)
	case object Dsfs4_5m extends Dsfs ("4- 5 months", "Dsfs4_5m", 4)
	case object Dsfs5_6m extends Dsfs ("5- 6 months", "Dsfs5_6m", 5)
	case object Dsfs6_7m extends Dsfs ("6- 7 months", "Dsfs6_7m", 6)
	case object Dsfs7_8m extends Dsfs ("7- 8 months", "Dsfs7_8m", 7)
	case object Dsfs8_9m extends Dsfs ("8- 9 months", "Dsfs8_9m", 8)
	case object Dsfs9_10m extends Dsfs ("9-10 months", "Dsfs9_10m", 9)
	case object Dsfs10_11m extends Dsfs ("10-11 months", "Dsfs10_11m", 10)
	case object Dsfs11_12m extends Dsfs ("11-12 months", "Dsfs11_12m", 11)
	case object UnknownDsfs extends Dsfs ("", "UnknownDsfs", 12)
  
	val values = List (UnknownDsfs, Dsfs0_1m, Dsfs1_2m, Dsfs2_3m, Dsfs3_4m,
	    Dsfs4_5m, Dsfs5_6m, Dsfs6_7m, Dsfs7_8m, Dsfs8_9m, Dsfs9_10m,
	    Dsfs10_11m, Dsfs11_12m)
	val max = values.map (_.code).max
}
