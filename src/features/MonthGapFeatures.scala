package features
import datatypes.Year
import datatypes.DataStore
import queries.MonthQuery
import datatypes.Dsfs
import datatypes.PlaceSvc
import datatypes.PrimaryConditionGroup
import datatypes.ProcedureGroup
import datatypes.Specialty
import datatypes.AgeAtFirstClaim
import datatypes.Sex
import datatypes.Month
import datatypes.Month._

object MonthGapFeatures {
	val features: List[Feature[_]] = {
		List (1, 2).map (NumClaimsNthDsfs) :::
		List (1, 2).map (CharlsonIndexNthDsfs) :::
		List (1, 2).map (TotalUrgentCareInpatientHospitalLosNthDsfs) :::
		List (1, 2).map (AvgPayDelayNthDsfs) :::
		PlaceSvc.values.map (place =>
		  	List (1, 2).map (PlaceSvcCountNthDsfs (place, _))).flatten :::
		PrimaryConditionGroup.values.map (condition =>
		  	List (1, 2).map (PrimaryConditionCountNthDsfs (condition, _))).flatten :::
		ProcedureGroup.values.map (procedure =>
		  	List (1, 2).map (ProcedureCountNthDsfs (procedure, _))).flatten :::
		Specialty.values.map (specialty =>
		  	List (1, 2).map (SpecialtyCountNthDsfs (specialty, _))).flatten :::
		List (Bias) :::
		AgeAtFirstClaim.values.map (Age) :::
		Sex.values.map (SexFeature)
	}

	val output: Feature[_] = MonthGap
	
	def getNthDsfs (ds: DataStore, memberId: Int, year: Year, n: Int): Dsfs = {
		val claims = ds.claimsTable.forMemberId (memberId, year)
		val firstDsfs = claims.next.dsfs
		List (1 until n).foldLeft (firstDsfs) ((_, currentDsfs) =>
			  claims.find (_.dsfs != currentDsfs).get.dsfs)
	}
}

object MonthGap extends NominalFeature[Month] (Month) {
	override def get (ds: DataStore, memberId: Int, year: Year): Month = {
		val claims = ds.claimsTable.forMemberId (memberId, year)
		val firstMonth = claims.next.dsfs
		val secondMonth = claims.find (_.dsfs != firstMonth).get.dsfs
		//Month.forCode (secondMonth.code - firstMonth.code)
		val gap = secondMonth.code - firstMonth.code
		if (gap <= 7) Month0 else Month6
	}
	override def toString: String = "MonthGap"
}

case class NumClaimsNthDsfs (n: Int) extends IntegerFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Int = {
		val dsfs = MonthGapFeatures.getNthDsfs (ds, memberId, year, n)
		MonthQuery.numClaimsDsfs (ds, memberId, year, dsfs)
	}
}

//TODO: may be more than one Charlson Index in a single month
case class CharlsonIndexNthDsfs (n: Int) extends IntegerFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Int = {
		val dsfs = MonthGapFeatures.getNthDsfs (ds, memberId, year, n)
		MonthQuery.charlsonIndexDsfs (ds, memberId, year, dsfs).code
	}
}

/**
 * This should be close to days in hospital
 */
case class TotalUrgentCareInpatientHospitalLosNthDsfs (n: Int) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val dsfs = MonthGapFeatures.getNthDsfs (ds, memberId, year, n)
		MonthQuery.totalUrgentCareLosDsfs (ds, memberId, year, dsfs) +
			MonthQuery.totalInpatientHospitalLosDsfs (ds, memberId, year, dsfs)
	}
}

case class AvgPayDelayNthDsfs (n: Int) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val dsfs = MonthGapFeatures.getNthDsfs (ds, memberId, year, n)
		MonthQuery.avgPayDelayDsfs (ds, memberId, year, dsfs)
	}
}

case class PlaceSvcCountNthDsfs (placeSvc: PlaceSvc, n: Int) extends IntegerFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Int = {
		val dsfs = MonthGapFeatures.getNthDsfs (ds, memberId, year, n)
		MonthQuery.placeSvcCountDsfs (ds, memberId, year, placeSvc, dsfs)
	}
	override def toString: String = "PlaceSvcCountNthDsfs("+placeSvc+";"+n+")"
}

case class PrimaryConditionCountNthDsfs (condition: PrimaryConditionGroup,
		n: Int) extends IntegerFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Int = {
		val dsfs = MonthGapFeatures.getNthDsfs (ds, memberId, year, n)
		MonthQuery.primaryConditionCountDsfs (ds, memberId, year, condition, dsfs)
	}
	override def toString: String = "PrimaryConditionCountNthDsfs("+condition+";"+n+")"
}

case class ProcedureCountNthDsfs (procedure: ProcedureGroup,
		n: Int) extends IntegerFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Int = {
		val dsfs = MonthGapFeatures.getNthDsfs (ds, memberId, year, n)
		MonthQuery.procedureCountDsfs (ds, memberId, year, procedure, dsfs)
	}
	override def toString: String = "ProcedureCountNthDsfs("+procedure+";"+n+")"
}

case class SpecialtyCountNthDsfs (specialty: Specialty, n: Int) extends IntegerFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Int = {
		val dsfs = MonthGapFeatures.getNthDsfs (ds, memberId, year, n)
		MonthQuery.specialtyCountDsfs (ds, memberId, year, specialty, dsfs)
	}
	override def toString: String = "SpecialtyCountNthDsfs("+specialty+";"+n+")"
}