package features
import datatypes.DataStore
import datatypes.AgeAtFirstClaim
import datatypes.CharlsonIndex
import datatypes.PlaceSvc
import datatypes.PrimaryConditionGroup
import datatypes.Specialty
import datatypes.ProcedureGroup
import datatypes.Sex
import datatypes.Dsfs
import datatypes.LengthOfStay
import datatypes.PayDelay
import datatypes.Year
import queries.CommonQuery
import datatypes.PrimaryConditionGroup._

object CommonFeatures {
	val features: List[RealFeature] = {
	  AgeAtFirstClaim.values.map (Age) :::
	  Sex.values.map (SexFeature) :::
	  List (NumClaims) :::
	  List (LastCharlsonIndex) :::
	  List (MaxClaimDsfs) :::
	  List (TotalUrgentCareInpatientHospitalLos) :::
	  //List (TotalEmergencyLengthOfStay):::
	  List (TotalInternalLengthOfStay):::
	  //List (AvgPayDelay)::: 
	  PlaceSvc.values.map (PlaceSvcCount) :::
	  PrimaryConditionGroup.values.map (PrimaryConditionGroupCount) :::
	  ProcedureGroup.values.map (ProcedureGroupCount) :::
	  Specialty.values.map (SpecialtyCount) :::
	  //List (NumDrugsCounts) :::
	  List (NumLabCounts) :::
	  List (Bias)	
	}
}

object Bias extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		1.0
	}
	override def toString: String = "Bias"
}

object NumClaims extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val claims = CommonQuery.numClaims (ds, memberId, year)
		claims / 40.0
 		//Math.log (claims + 1.0) / 3.5
	}
}

object LastCharlsonIndex extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val index = CommonQuery.lastCharlsonIndex (ds, memberId, year) match {
			case Some (charlsonIndex) => charlsonIndex.code
			case _ => 0.0
		}
		index / CharlsonIndex.max
	}
}

object FirstCharlsonIndex extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val index = CommonQuery.firstCharlsonIndex (ds, memberId, year) match {
			case Some (charlsonIndex) => charlsonIndex.code
			case _ => 0.0
		}
		index / CharlsonIndex.max
	}
}

object MaxClaimDsfs extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val days = CommonQuery.maxClaimDsfs (ds, memberId, year) match {
			case Some (dsfs) => dsfs.code
			case _ => 0.0
		}
		days / Dsfs.max
	}
}

object TotalLengthOfStay extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val total = CommonQuery.totalLengthOfStay (ds, memberId, year)
		//total / 15.0
		Math.log (total + 1.0) / 2.7
	}
}

case class TotalLengthOfStayLastMonths (lastMonths: Int) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val total = CommonQuery.totalLengthOfStayLastMonths (ds, memberId, year, lastMonths)
		//total / 15.0
		Math.log (total + 1.0) / 2.7
	}
}

/**
 * This should be close to days in hospital
 */
object TotalUrgentCareInpatientHospitalLos extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val total = CommonQuery.totalUrgentCareLengthOfStay (ds, memberId, year) +
				CommonQuery.totalInpatientHospitalLengthOfStay (ds, memberId, year)
		//total / 15.0
		Math.log (total + 1.0) / 2.7
	}
}

case class TotalUrgentCareInpatientHospitalLosLastMonths (lastMonths: Int) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val total = CommonQuery.totalUrgentCareLengthOfStayLastMonths (ds, memberId, year, lastMonths) +
				CommonQuery.totalInpatientHospitalLengthOfStayLastMonths (ds, memberId, year, lastMonths)
		//total / 15.0
		Math.log (total + 1.0) / 2.7
	}
}

object TotalEmergencyLengthOfStay extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val total = CommonQuery.totalEmergencyLengthOfStay (ds, memberId, year)
		Math.log (total + 1.0) / 2.7
	}
}

object TotalInternalLengthOfStay extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val total = CommonQuery.totalInternalLengthOfStay (ds, memberId, year)
		Math.log (total + 1.0) / 2.7
	}
}

object TotalSurgeryLengthOfStay extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val total = CommonQuery.totalSurgeryLengthOfStay (ds, memberId, year)
		Math.log (total + 1.0) / 2.7
	}
}

object AvgPayDelay extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val avg = CommonQuery.avgPayDelay (ds, memberId, year)
		Math.log (avg + 1.0) / 5.0
	}
}

case class HasPlaceSvcLastMonths (placeSvc: PlaceSvc, lastMonths: Int) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		CommonQuery.hasPlaceSvcLastMonths (ds, memberId, year, placeSvc, lastMonths)
	}
} 

case class PlaceSvcCount (placeSvc: PlaceSvc) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val count = CommonQuery.placeSvcCount (ds, memberId, year, placeSvc)
		Math.log (count + 1.0) / 3.5
	}
}

case class LastPlaceSvc (placeSvc: PlaceSvc) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double =
		CommonQuery.isLastPlaceSvc (ds, memberId, year, placeSvc)
}

case class HasPrimaryConditionGroupLastMonths (condition: PrimaryConditionGroup,
		lastMonths: Int) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		CommonQuery.hasPrimaryConditionGroupLastMonths (ds, memberId, year, condition, lastMonths)
	}
}

case class HasPrimaryConditionGroup (
		primaryConditionGroup: PrimaryConditionGroup) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double =
		CommonQuery.hasPrimaryConditionGroup (ds, memberId, year, primaryConditionGroup)
}

case class PrimaryConditionGroupCount (
		primaryConditionGroup: PrimaryConditionGroup) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val count = CommonQuery.primaryConditionGroupCount (ds, memberId, year, primaryConditionGroup)
		Math.log (count + 1.0) / 3.5
	}
}

case class LastPrimaryConditionGroup (
		primaryConditionGroup: PrimaryConditionGroup) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double =
		CommonQuery.isLastPrimaryConditionGroup (ds, memberId, year, primaryConditionGroup)
}

case class LastPrimaryConditionGroupForSpecialty (
		primaryConditionGroup: PrimaryConditionGroup,
		specialty: Specialty) extends RealFeature {
  
	override def get (ds: DataStore, memberId: Int, year: Year): Double =
		CommonQuery.isLastPrimaryConditionGroupForSpecialty (
		    ds, memberId, year, primaryConditionGroup, specialty
		)
}

case class PrimaryConditionMovingAvg (
		primaryCondition: PrimaryConditionGroup) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double =
		CommonQuery.primaryConditionMovingAvg (ds, memberId, year, primaryCondition)
}

case class HasProcedureGroupLastMonths (procedure: ProcedureGroup,
		lastMonths: Int) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		CommonQuery.hasProcedureGroupLastMonths (ds, memberId, year, procedure, lastMonths)
	}
}

case class HasProcedureGroup (procedureGroup: ProcedureGroup) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double =
		CommonQuery.hasProcedureGroup (ds, memberId, year, procedureGroup)
}

case class ProcedureGroupCount (procedureGroup: ProcedureGroup) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val count = CommonQuery.procedureGroupCount (ds, memberId, year, procedureGroup)
		Math.log (count + 1.0) / 3.5
	}
}

case class LastProcedureGroup (procedureGroup: ProcedureGroup) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double =
		CommonQuery.isLastProcedureGroup (ds, memberId, year, procedureGroup)
}

case class LastProcedureGroupForSpecialty (
		procedureGroup: ProcedureGroup,
		specialty: Specialty) extends RealFeature {
  
	override def get (ds: DataStore, memberId: Int, year: Year): Double =
		CommonQuery.isLastProcedureGroupForSpecialty (
		    ds, memberId, year, procedureGroup, specialty
		)
}

case class HasSpecialtyLastMonths (specialty: Specialty,
		lastMonths: Int) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		CommonQuery.hasSpecialtyLastMonths (ds, memberId, year, specialty, lastMonths)
	}
}

case class HasSpecialty (specialty: Specialty) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double =
		CommonQuery.hasSpecialty (ds, memberId, year, specialty)
}

case class SpecialtyCount (specialty: Specialty) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val count = CommonQuery.specialtyCount (ds, memberId, year, specialty)
		Math.log (count + 1.0) / 3.5
	}
}

case class LastSpecialty (specialty: Specialty) extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double =
		CommonQuery.isLastSpecialty (ds, memberId, year, specialty)
}

object NumDrugsCounts extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val num = CommonQuery.numDrugCounts (ds, memberId, year)
		Math.log (num + 1.0) / 3.5
	}
}

object LastDrugCount extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val count = CommonQuery.lastDrugCount (ds, memberId, year) match {
			case Some (drugCountTuple) => drugCountTuple.drugCount.code
			case _ => 0.0
		}
		Math.log (count + 1.0) / 3.5
	}
}

object FirstDrugCount extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val count = CommonQuery.firstDrugCount (ds, memberId, year) match {
			case Some (drugCountTuple) => drugCountTuple.drugCount.code
			case _ => 0.0
		}
		Math.log (count + 1.0) / 3.5
	}
}

object NumLabCounts extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val num = CommonQuery.numLabCounts (ds, memberId, year)
		Math.log (num + 1.0) / 3.5
	}
}

object LastLabCount extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val count = CommonQuery.lastLabCount (ds, memberId, year) match {
			case Some (labCountTuple) => labCountTuple.labCount.code
			case _ => 0.0
		}
		Math.log (count + 1.0) / 3.5
	}
}

object FirstLabCount extends RealFeature {
	override def get (ds: DataStore, memberId: Int, year: Year): Double = {
		val count = CommonQuery.firstLabCount (ds, memberId, year) match {
			case Some (labCountTuple) => labCountTuple.labCount.code
			case _ => 0.0
		}
		Math.log (count + 1.0) / 3.5
	}
}

case class Age (age: AgeAtFirstClaim) extends RealFeature {
	override def get (ds: DataStore, id: Int, year: Year): Double =
		CommonQuery.isAge (ds, id, age)
}

case class SexFeature (sex: Sex) extends RealFeature {
	override def get (ds: DataStore, id: Int, year: Year): Double =
		CommonQuery.isSex (ds, id, sex)
}
