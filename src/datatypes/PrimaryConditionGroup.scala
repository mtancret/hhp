package datatypes

case class PrimaryConditionGroup (t: String, n: String, c: Int) extends EnumType[Int] (t, n, c)

object PrimaryConditionGroup extends Enum[PrimaryConditionGroup] {
	case object PrimaryConditionUnknown extends
		PrimaryConditionGroup ("", "PrimaryConditionUnknown", -1)
	case object Neument extends PrimaryConditionGroup ("NEUMENT", "Neument", 0)
	case object Metab3 extends PrimaryConditionGroup ("METAB3", "Metab3", 1)
	case object Fxdislc extends PrimaryConditionGroup ("FXDISLC", "Fxdislc", 2)
	case object Trauma extends PrimaryConditionGroup ("TRAUMA", "Trauma", 3)
	case object Respr4 extends PrimaryConditionGroup ("RESPR4", "Respr4", 4)
	case object Infec4 extends PrimaryConditionGroup ("INFEC4", "Infec4", 5)
	case object Mischrt extends PrimaryConditionGroup ("MISCHRT", "Mischrt", 6)
	case object Arthspin extends PrimaryConditionGroup ("ARTHSPIN", "Arthspin", 7)
	case object Roami extends PrimaryConditionGroup ("ROAMI", "Roami", 8)
	case object Heart2 extends PrimaryConditionGroup ("HEART2", "Heart2", 9)	
	case object Hemtol extends PrimaryConditionGroup ("HEMTOL", "Hemtol", 10)
	case object Msc2a3 extends PrimaryConditionGroup ("MSC2a3", "Msc2a3", 11)
	case object Sknaut extends PrimaryConditionGroup ("SKNAUT", "Sknaut", 12)
	case object Ami extends PrimaryConditionGroup ("AMI", "Ami", 13)
	case object Gynec1 extends PrimaryConditionGroup ("GYNEC1", "Gynec1", 14)
	case object Gibleed extends PrimaryConditionGroup ("GIBLEED", "Gibleed", 15)
	case object Cancra extends PrimaryConditionGroup ("CANCRA", "Cancra", 16)
	case object Pervalv extends PrimaryConditionGroup ("PERVALV", "Pervalv", 17)
	case object Uti extends PrimaryConditionGroup ("UTI", "Uti", 18)
	case object Copd extends PrimaryConditionGroup ("COPD", "Copd", 19)
	case object Heart4 extends PrimaryConditionGroup ("HEART4", "Heart4", 20)
	case object Cancrb extends PrimaryConditionGroup ("CANCRB", "Cancrb", 21)
	case object Renal3 extends PrimaryConditionGroup ("RENAL3", "Renal3", 22)
	case object Renal2 extends PrimaryConditionGroup ("RENAL2", "Renal2", 23)
	case object Odabnca extends PrimaryConditionGroup ("ODaBNCA", "Odabnca", 24)
	case object Miscl5 extends PrimaryConditionGroup ("MISCL5", "Miscl5", 25)
	case object Appchol extends PrimaryConditionGroup ("APPCHOL", "Appchol", 26)
	case object Catast extends PrimaryConditionGroup ("CATAST", "Catast", 27)
	case object Seizure extends PrimaryConditionGroup ("SEIZURE", "Seizure", 28)
	case object Prgncy extends PrimaryConditionGroup ("PRGNCY", "Prgncy", 29)
	case object Chf extends PrimaryConditionGroup ("CHF", "Chf", 30)
	case object Miscl1 extends PrimaryConditionGroup ("MISCL1", "Miscl1", 31)
	case object Renal1 extends PrimaryConditionGroup ("RENAL1", "Renal1", 32)
	case object Hipfx extends PrimaryConditionGroup ("HIPFX", "Hipfx", 33)
	case object Gyneca extends PrimaryConditionGroup ("GYNECA", "Gyneca", 34)
	case object Flaelec extends PrimaryConditionGroup ("FLaELEC", "Flaelec", 35)
	case object Liverdz extends PrimaryConditionGroup ("LIVERDZ", "Liverdz", 36)
	case object Metab1 extends PrimaryConditionGroup ("METAB1", "Metab1", 37)
	case object Pneum extends PrimaryConditionGroup ("PNEUM", "Pneum", 38)
	case object Stroke extends PrimaryConditionGroup ("STROKE", "Stroke", 39)
	case object Giobsent extends PrimaryConditionGroup ("GIOBSENT", "Giobsent", 40)
	case object Cancrm extends PrimaryConditionGroup ("CANCRM", "Cancrm", 41)
	case object Pncrdz extends PrimaryConditionGroup ("PNCRDZ", "Pncrdz", 42)
	case object Perintl extends PrimaryConditionGroup ("PERINTL", "Perintl", 43)
	case object Sepsis extends PrimaryConditionGroup ("SEPSIS", "Sepsis", 44)

	val values = List (PrimaryConditionUnknown, Neument, Metab3, Fxdislc,
	    Trauma, Respr4, Infec4, Mischrt, Arthspin, Roami, Heart2, Hemtol,
	    Msc2a3, Sknaut, Ami, Gynec1, Gibleed, Cancra, Pervalv, Uti, Copd,
	    Heart4, Cancrb, Renal3, Renal2, Odabnca, Miscl5, Appchol, Catast,
	    Seizure, Prgncy, Chf, Miscl1, Renal1, Hipfx, Gyneca, Flaelec,
	    Liverdz, Metab1, Pneum, Stroke, Giobsent, Cancrm, Pncrdz, Perintl,
	    Sepsis)
}
