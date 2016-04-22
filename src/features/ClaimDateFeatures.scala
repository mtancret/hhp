package features
import datatypes.PrimaryConditionGroup
import datatypes.ProcedureGroup
import datatypes.Specialty
import datatypes.AgeAtFirstClaim
import datatypes.Sex
import datatypes.Year
import datatypes.DataStore
import queries.CommonQuery
import queries.MonthQuery
import datatypes.Year._
import datatypes.PlaceSvc
import datatypes.PlaceSvc._

object ClaimDateFeatures {
	val features: List[RealFeature] = {
		AgeAtFirstClaim.values.map (Age) :::
		Sex.values.map (SexFeature) :::
		List (NumClaims) :::
		List (NumClaimsPrevYear) :::
		List (1, 2, 3, 4, 5).map {quarter =>
			NumClaimsQuarter (quarter)
		} :::
		List (LastCharlsonIndex) :::
		List (MaxClaimDsfs) :::
		List (TotalUrgentCareInpatientHospitalLos) :::
		List (TotalEmergencyLengthOfStay):::
		List (TotalInternalLengthOfStay):::
		List (AvgPayDelay)::: 
		PlaceSvc.values.map (PlaceSvcCount) :::
		//PlaceSvc.values.map (PlaceSvcCountPrevYear) :::
		PlaceSvc.values.map {place =>
			List (1, 2, 3, 4, 5).map {quarter =>
				PlaceSvcCountQuarter (place, quarter)
			}
		}.flatten :::
		PrimaryConditionGroup.values.map (PrimaryConditionGroupCount) :::
		//PrimaryConditionGroup.values.map (PrimaryConditionCountPrevYear) :::
		PrimaryConditionGroup.values.map {group =>
			List (1, 2, 3, 4, 5).map {quarter =>
				PrimaryConditionCountQuarter (group, quarter)
			}
		}.flatten :::
		ProcedureGroup.values.map (ProcedureGroupCount) :::
		ProcedureGroup.values.map (ProcedureCountPrevYear) :::
		ProcedureGroup.values.map {group =>
			List (1, 2, 3, 4, 5).map {quarter =>
				ProcedureCountQuarter (group, quarter)
			}
		}.flatten :::
		Specialty.values.map (SpecialtyCount) :::
		//Specialty.values.map (SpecialtyCountPrevYear) :::
		Specialty.values.map {specialty =>
			List (1, 2, 3, 4, 5).map {quarter =>
				SpecialtyCountQuarter (specialty, quarter)
			}
		}.flatten :::
		List (NumDrugsCounts) :::
		List (NumLabCounts) :::
		List (Bias)
	}
	
	case class PlaceSvcCountPrevYear (place: PlaceSvc)
			extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double = {
			val prevYear = if (year == Year2) Year1 else Year2
			val count = CommonQuery.placeSvcCount (ds, memberId, prevYear, place)
			Math.log (count + 1) / 3.5
		}
	}
	
	case class PlaceSvcCountQuarter (place: PlaceSvc, quarter: Int)
			extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double = {
			val count = MonthQuery.placeSvcCountQuarter (ds, memberId, year, place,
				quarter)
			Math.log (count + 1.0) / 3.5
		}
	}
	
	case class PrimaryConditionCountPrevYear (condition: PrimaryConditionGroup)
			extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double = {
			val prevYear = if (year == Year2) Year1 else Year2
			val count = CommonQuery.primaryConditionGroupCount (ds, memberId, prevYear,
					condition)
			Math.log (count + 1) / 3.5
		}
	}
	
	case class PrimaryConditionCountQuarter (condition: PrimaryConditionGroup,
		quarter: Int) extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double = {
				val count = MonthQuery.primaryConditionCountQuarter (ds, memberId, year,
				    condition, quarter)
				Math.log (count + 1) / 3.5
		}
	}
	
	case class PrimaryConditionFirstQuarter (condition: PrimaryConditionGroup,
	    quarter: Int) extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double = {
			val month = MonthQuery.primaryConditionFirstMonth (ds, memberId, year,
			    condition)
			if (month < quarter * 3 && month >= (quarter - 1) * 3) {
				1.0
			} else
				0.0
		}
	}
	
	case class ProcedureCountPrevYear (procedure: ProcedureGroup)
			extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double = {
			val prevYear = if (year == Year2) Year1 else Year2
			val count = CommonQuery.procedureGroupCount (ds, memberId, prevYear,
					procedure)
			Math.log (count + 1) / 3.5
		}
	}
	
	case class ProcedureCountQuarter (procedure: ProcedureGroup,
		quarter: Int) extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double = {
			val count = MonthQuery.procedureCountQuarter (ds, memberId, year,
				procedure, quarter)
			Math.log (count + 1) / 3.5
		}
	}
	
	case class SpecialtyCountPrevYear (specialty: Specialty)
			extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double = {
			val prevYear = if (year == Year2) Year1 else Year2
			val count = CommonQuery.specialtyCount (ds, memberId, prevYear,
					specialty)
			Math.log (count + 1) / 3.5
		}
	}
	
	case class SpecialtyCountQuarter (specialty: Specialty,
		quarter: Int) extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double = {
			val count = MonthQuery.specialtyCountQuarter (ds, memberId, year,
				specialty, quarter)
			Math.log (count + 1) / 3.5
		}
	}
	
	object NumClaimsPrevYear extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double = {
			val prevYear = if (year == Year2) Year1 else Year2
			val claims = CommonQuery.numClaims (ds, memberId, prevYear)
			claims / 40.0
		}
	}
	
	case class NumClaimsQuarter (quarter: Int) extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double = {
			val claims = MonthQuery.numClaimsQuarter (ds, memberId, year, quarter)
			claims / 10.0
		}
	}
}