package queries
import datatypes.Specialty._
import datatypes.PlaceSvc._
import datatypes.Year
import datatypes.DataStore
import datatypes.MemberTuple
import datatypes.ClaimTuple
import datatypes.Specialty
import datatypes.CharlsonIndex
import datatypes.Dsfs
import datatypes.PlaceSvc
import datatypes.ProcedureGroup
import datatypes.PrimaryConditionGroup
import datatypes.DrugCountTuple
import datatypes.LabCountTuple
import datatypes.AgeAtFirstClaim
import datatypes.Sex
import datatypes.Sex._
import datatypes.AgeAtFirstClaim._

object CommonQuery {
	/*************************************************************
	 * Claims Queries
	 *************************************************************/
	
	def numClaims (ds: DataStore, memberId: Int, year: Year): Int = {
		ds.claimsTable.forMemberId (memberId, year).length
	}
	
	def overXClaims (ds: DataStore, x: Int, year: Year): Iterator[MemberTuple] = {
		ds.membersTable.iterator filter (member =>
		  	numClaims (ds, member.memberId, year) > x)
	}
	
	/**
	 * Guesses the last claim for a member based on days since first seen (dsfs).
	 */
	def lastClaim (ds: DataStore, memberId: Int, year: Year): Option[ClaimTuple] = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		if (memberClaims.isEmpty)
			None
		else
			Some (memberClaims.maxBy (_.dsfs.code))
	}
	
	def lastClaimForSpecialty (ds: DataStore, memberId: Int,
			year: Year, specialty: Specialty) = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val specialtyClaims = memberClaims.filter (_.specialty == specialty)
		if (specialtyClaims.isEmpty)
			None
		else
			Some (specialtyClaims.maxBy (_.dsfs.code))
	}
	
	/**
	 * Guesses the first claim for a member based on days since first seen (dsfs).
	 */
	def firstClaim (ds: DataStore, memberId: Int, year: Year): Option[ClaimTuple] = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		if (memberClaims.isEmpty)
			None
		else
			Some (memberClaims.minBy (_.dsfs.code))
	}
	
	def lastCharlsonIndex (ds: DataStore, memberId: Int, year: Year): Option[CharlsonIndex] = {
		lastClaim (ds, memberId, year) match {
			case Some (claim) => Some (claim.charlsonIndex)
			case None => None
		}
	}
	
	def firstCharlsonIndex (ds: DataStore, memberId: Int, year: Year): Option[CharlsonIndex] = {
		firstClaim (ds, memberId, year) match {
			case Some (claim) => Some (claim.charlsonIndex)
			case None => None
		}
	}
	
	/**
	 * The maximum days since first seen (dsfs) from claims.
	 */
	def maxClaimDsfs (ds: DataStore, memberId: Int, year: Year): Option[Dsfs] = {
		lastClaim (ds, memberId, year) match {
			case Some (claim) => Some (claim.dsfs)
			case None => None
		}
	}
	
	def totalLengthOfStay (ds: DataStore, memberId: Int, year: Year): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		memberClaims.map (_.lengthOfStay.code).sum
	}
	
	def lastDsfs (ds: DataStore, memberId: Int, year: Year): Int = {
		val claims = ds.claimsTable.forMemberId (memberId, year)
		if (claims.isEmpty) 0
		else claims.map (_.dsfs.code).max
	}
	
	def totalLengthOfStayLastMonths (ds: DataStore, memberId: Int, year: Year,
			numMonths: Int): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val excludedMonth = lastDsfs (ds, memberId, year) - numMonths
		memberClaims.filter (_.dsfs.code > excludedMonth).map (_.lengthOfStay.code).sum
	}
	
	def totalUrgentCareLengthOfStay (ds: DataStore, memberId: Int, year: Year): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val urgentCareClaims = memberClaims.filter (_.placeSvc == UrgentCare)
		urgentCareClaims.map (_.lengthOfStay.code).sum
	}
	
	def totalUrgentCareLengthOfStayLastMonths (ds: DataStore, memberId: Int, year: Year,
			numMonths: Int): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val urgentCareClaims = memberClaims.filter (_.placeSvc == UrgentCare)
		val excludedMonth = lastDsfs (ds, memberId, year) - numMonths
		urgentCareClaims.filter (_.dsfs.code > excludedMonth).map (_.lengthOfStay.code).sum
	}
	  
	def totalInpatientHospitalLengthOfStay (ds: DataStore, memberId: Int, year: Year): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val inpatientClaims = memberClaims.filter (_.placeSvc == InpatientHospital)
		inpatientClaims.map (_.lengthOfStay.code).sum
	}
	
	def totalInpatientHospitalLengthOfStayLastMonths (ds: DataStore, memberId: Int,
			year: Year, numMonths: Int): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val inpatientClaims = memberClaims.filter (_.placeSvc == InpatientHospital)
		val excludedMonth = lastDsfs (ds, memberId, year) - numMonths
		inpatientClaims.filter (_.dsfs.code > excludedMonth).map (_.lengthOfStay.code).sum
	}
	
	def totalEmergencyLengthOfStay (ds: DataStore, memberId: Int, year: Year): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val emergencyMemberClaims = memberClaims.filter (_.specialty == Emergency)
		emergencyMemberClaims.map (_.lengthOfStay.code).sum
	}
	
	def totalInternalLengthOfStay (ds: DataStore, memberId: Int, year: Year): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val internalMemberClaims = memberClaims.filter (_.specialty == Internal)
		internalMemberClaims.map (_.lengthOfStay.code).sum
	}
	
	def totalSurgeryLengthOfStay (ds: DataStore, memberId: Int, year: Year): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val surgeryMemberClaims = memberClaims.filter (_.specialty == Surgery)
		surgeryMemberClaims.map (_.lengthOfStay.code).sum
	}
	
	def avgPayDelay (ds: DataStore, memberId: Int, year: Year): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val length = ds.claimsTable.forMemberId (memberId, year).length
		if (length == 0)
			0.0 // is this right to do when there are no claims?
		else
			memberClaims.map (_.payDelay.code).sum / length
	}
	
	def hasPlaceSvcLastMonths (ds: DataStore, memberId: Int, year: Year,
			placeSvc: PlaceSvc, numMonths: Int): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val svcClaims = memberClaims.filter (_.placeSvc == placeSvc)
		val excludedMonth = lastDsfs (ds, memberId, year) - numMonths
		if (svcClaims.filter (_.dsfs.code > excludedMonth).isEmpty) 0 else 1
	}
	
	def placeSvcCount (ds: DataStore, memberId: Int, year: Year, placeSvc: PlaceSvc): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		memberClaims.count (_.placeSvc == placeSvc)
	}
	
	def isLastPlaceSvc (ds: DataStore, memberId: Int, year: Year, placeSvc: PlaceSvc): Int = {
		lastClaim (ds, memberId, year) match {
			case Some (claim) if (claim.placeSvc == placeSvc) => 1
			case _ => 0
		}
	}
	
	def hasPrimaryConditionGroupLastMonths (ds: DataStore, memberId: Int, year: Year,
			condition: PrimaryConditionGroup, numMonths: Int): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val conditionClaims = memberClaims.filter (_.primaryConditionGroup == condition)
		val excludedMonth = lastDsfs (ds, memberId, year) - numMonths
		if (conditionClaims.filter (_.dsfs.code > excludedMonth).isEmpty) 0 else 1
	}
	
	def hasPrimaryConditionGroup (ds: DataStore, memberId: Int, year: Year,
			group: PrimaryConditionGroup): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		if (memberClaims.exists (_.primaryConditionGroup == group)) 1 else 0
	}
	
	def hasPrimaryConditionGroup (ds: DataStore, memberId: Int,
			group: PrimaryConditionGroup): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId)
		if (memberClaims.exists (_.primaryConditionGroup == group)) 1 else 0
	}
	
	def primaryConditionGroupCount (ds: DataStore, memberId: Int, year: Year, 
			primaryConditionGroup: PrimaryConditionGroup): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		memberClaims.count (_.primaryConditionGroup == primaryConditionGroup)
	}
	
	def isLastPrimaryConditionGroup (ds: DataStore, memberId: Int, year: Year,
			primaryConditionGroup: PrimaryConditionGroup): Int = {
		lastClaim (ds, memberId, year) match {
			case Some (claim) if (claim.primaryConditionGroup == primaryConditionGroup) => 1
			case _ => 0
		}
	}
	
	def isLastPrimaryConditionGroupForSpecialty (ds: DataStore, memberId: Int, year: Year, 
			primaryConditionGroup: PrimaryConditionGroup, specialty: Specialty): Int = {
		lastClaimForSpecialty (ds, memberId, year, specialty) match {
			case Some (claim) if (claim.primaryConditionGroup == primaryConditionGroup) => 1
			case _ => 0
		}
	}
	
	def primaryConditionMovingAvg (ds: DataStore, memberId: Int, year: Year,
			primaryCondition: PrimaryConditionGroup): Double = {
		
		val lastMonthOption = maxClaimDsfs (ds, memberId, year)
		val avg = lastMonthOption match {
		  	case Some (lastMonth) /*if lastMonth == 6*/ =>
		  		Dsfs.values.foldLeft (0.0) {(avg, month) =>
		  		  	if (month.code > lastMonth.code) avg
		  			else avg / 1.0 + ds.claimsTable.forMemberId (memberId, year).count (
		  			    claim =>
		  				claim.dsfs == month && claim.primaryConditionGroup == primaryCondition)
		  		}
		  	case _ =>
		  		0.0
		}
		Math.log (avg + 1.0)
	}
	
	def hasProcedureGroupLastMonths (ds: DataStore, memberId: Int, year: Year,
			procedure: ProcedureGroup, numMonths: Int): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val procedureClaims = memberClaims.filter (_.procedureGroup == procedure)
		val excludedMonth = lastDsfs (ds, memberId, year) - numMonths
		if (procedureClaims.filter (_.dsfs.code > excludedMonth).isEmpty) 0 else 1
	}
	
	def hasProcedureGroup (ds: DataStore, memberId: Int, year: Year,
			procedureGroup: ProcedureGroup): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		if (memberClaims.exists (_.procedureGroup == procedureGroup)) 1 else 0
	}
	
	def hasProcedureGroup (ds: DataStore, memberId: Int,
			group: ProcedureGroup): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId)
		if (memberClaims.exists (_.procedureGroup == group)) 1 else 0
	}
	
	def procedureGroupCount (ds: DataStore, memberId: Int, year: Year,
			procedureGroup: ProcedureGroup): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		memberClaims.count (_.procedureGroup == procedureGroup)
	}
	
	def isLastProcedureGroup (ds: DataStore, memberId: Int, year: Year,
			procedureGroup: ProcedureGroup): Int = {
		lastClaim (ds, memberId, year) match {
			case Some (claim) if (claim.procedureGroup == procedureGroup) => 1
			case _ => 0
		}
	}
	
	def isLastProcedureGroupForSpecialty (ds: DataStore, memberId: Int, year: Year,
			procedureGroup: ProcedureGroup, specialty: Specialty): Int = {
		lastClaimForSpecialty (ds, memberId, year, specialty) match {
			case Some (claim) if (claim.procedureGroup == procedureGroup) => 1
			case _ => 0
		}
	}
	
	def hasSpecialtyLastMonths (ds: DataStore, memberId: Int, year: Year,
			specialty: Specialty, numMonths: Int): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val specialtyClaims = memberClaims.filter (_.specialty == specialty)
		val excludedMonth = lastDsfs (ds, memberId, year) - numMonths
		if (specialtyClaims.filter (_.dsfs.code > excludedMonth).isEmpty) 0 else 1
	}
	
	def hasSpecialty (ds: DataStore, memberId: Int, year: Year,
			specialty: Specialty): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		if (memberClaims.exists (_.specialty == specialty)) 1 else 0
	}
	
	def hasSpecialty (ds: DataStore, memberId: Int,
			specialty: Specialty): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId)
		if (memberClaims.exists (_.specialty == specialty)) 1 else 0
	}
	
	def specialtyCount (ds: DataStore, memberId: Int, year: Year,
			specialty: Specialty): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		memberClaims.count (_.specialty == specialty)
	}
	
	def isLastSpecialty (ds: DataStore, memberId: Int, year: Year,
			specialty: Specialty): Int = {
		lastClaim (ds, memberId, year) match {
			case Some (claim) if (claim.specialty == specialty) => 1
			case _ => 0
		}
	}
	
	/*************************************************************
	 * DaysInHospital Queries
	 *************************************************************/
	
	def daysInHospital (ds: DataStore, memberId: Int, year: Year): Double = {
		ds.daysInHospitalTable.forMemberId (memberId, year).next ().days
	}
	
	def isClaimsTruncated (ds: DataStore, memberId: Int, year: Year): Int = {
		ds.daysInHospitalTable.forMemberId (memberId, year).next ().claimsTruncated
	}
	
	/*************************************************************
	 * DrugCount Queries
	 *************************************************************/
	
	def numDrugCounts (ds: DataStore, memberId: Int, year: Year): Int = {
		ds.drugCountTable.forMemberId (memberId, year).length
	}
	
	def overXDrugCounts (ds: DataStore, x: Int, year: Year): Iterator[MemberTuple] = {
		ds.membersTable.iterator filter (member =>
		  	numDrugCounts (ds, member.memberId, year) > x)
	}
	
	/**
	 * Last drug count for a member based on days since first seen (dsfs).
	 */
	def lastDrugCount (ds: DataStore, memberId: Int,
			year: Year): Option[DrugCountTuple] = {
		val memberDrugCounts = ds.drugCountTable.forMemberId (memberId, year)
		if (memberDrugCounts.isEmpty)
			None
		else
			Some (memberDrugCounts.maxBy (_.dsfs.code))
	}
	
	/**
	 * First drug count for a member based on days since first seen (dsfs).
	 */
	def firstDrugCount (ds: DataStore, memberId: Int,
			year: Year): Option[DrugCountTuple] = {
		val memberDrugCounts = ds.drugCountTable.forMemberId (memberId, year)
		if (memberDrugCounts.isEmpty)
			None
		else
			Some (memberDrugCounts.minBy (_.dsfs.code))
	}
	
	/*************************************************************
	 * LabCount Queries
	 *************************************************************/
	
	def numLabCounts (ds: DataStore, memberId: Int, year: Year): Int = {
		ds.labCountTable.forMemberId (memberId, year).length
	}
	
	def overXLabCounts (ds: DataStore, x: Int, year: Year): Iterator[MemberTuple] = {
		ds.membersTable.iterator filter (member =>
		  	numLabCounts (ds, member.memberId, year) > x)
	}
	
	/**
	 * Last lab count for a member based on days since first seen (dsfs).
	 */
	def lastLabCount (ds: DataStore, memberId: Int, year: Year): Option[LabCountTuple] = {
		val memberLabCounts = ds.labCountTable.forMemberId (memberId, year)
		if (memberLabCounts.isEmpty)
			None
		else
			Some (memberLabCounts.maxBy (_.dsfs.code))
	}
	
	/**
	 * First lab count for a member based on days since first seen (dsfs).
	 */
	def firstLabCount (ds: DataStore, memberId: Int, year: Year): Option[LabCountTuple] = {
		val memberLabCounts = ds.labCountTable.forMemberId (memberId, year)
		if (memberLabCounts.isEmpty)
			None
		else
			Some (memberLabCounts.minBy (_.dsfs.code))
	}
	
	/*************************************************************
	 * Member Queries
	 *************************************************************/
	
	def isAge (ds: DataStore, memberId: Int, age: AgeAtFirstClaim): Int = {
		if (ds.membersTable.forMemberId (memberId).ageAtFirstClaim == age) 1 else 0
	}
	
	def isSex (ds: DataStore, memberId: Int, sex: Sex): Int = {
		if (ds.membersTable.forMemberId (memberId).sex == sex) 1 else 0
	}
}