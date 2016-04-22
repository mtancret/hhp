package features
import datatypes.ProcedureGroup
import datatypes.PrimaryConditionGroup
import datatypes.Specialty
import datatypes.DataStore
import datatypes.Year
import queries.CommonQuery
import CommonFeatures._
import datatypes.AgeAtFirstClaim
import datatypes.Sex

object SanitizerFeatures {
	val unknownSexFeatures: List[RealFeature] = {
		PrimaryConditionGroup.values.map (HasPrimaryConditionGroupAnyYear) :::	
		ProcedureGroup.values.map (HasProcedureGroupAnyYear) :::
		Specialty.values.map (HasSpecialtyAnyYear) :::
		AgeAtFirstClaim.values.map (Age)
	}
	
	val unknownAgeFeatures: List[RealFeature] = {
		PrimaryConditionGroup.values.map (HasPrimaryConditionGroupAnyYear) :::	
		ProcedureGroup.values.map (HasProcedureGroupAnyYear) :::
		Specialty.values.map (HasSpecialtyAnyYear) :::
		Sex.values.map (SexFeature)
	}
	
	val unknownAgeAndSexFeatures: List[RealFeature] = {
		PrimaryConditionGroup.values.map (HasPrimaryConditionGroupAnyYear) :::	
		ProcedureGroup.values.map (HasProcedureGroupAnyYear) :::
		Specialty.values.map (HasSpecialtyAnyYear)
	}
	
	case class HasPrimaryConditionGroupAnyYear (
			group: PrimaryConditionGroup) extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double =
			CommonQuery.hasPrimaryConditionGroup (ds, memberId, group)
	}
	
	case class HasProcedureGroupAnyYear (
			group: ProcedureGroup) extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double =
			CommonQuery.hasProcedureGroup (ds, memberId, group)
	}
	
	case class HasSpecialtyAnyYear (specialty: Specialty) extends RealFeature {
		override def get (ds: DataStore, memberId: Int, year: Year): Double =
			CommonQuery.hasSpecialty (ds, memberId, specialty)
	}
}