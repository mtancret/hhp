package ensemble
import file.FileLoader
import datatypes.ClaimsTable
import datatypes.Year._
import datatypes.ClaimTuple
import datatypes.Month._
import datatypes.Dsfs._
import datatypes.Month
import datatypes.DataStore
import file.ClaimDateWriter

object ClaimDate {
	def run = {
		println ("Loading data...")
		val ds = FileLoader.loadAllUnsanitized
		
		println ("Updating dates...")
		val newClaimsTable = updateClaimsTable (ds)
		val newDs = DataStore (newClaimsTable,
				ds.daysInHospitalTable,
				ds.drugCountTable,
				ds.labCountTable,
				ds.membersTable)
		
		println ("Writing new claims file...")
		ClaimDateWriter.write (newDs)
	}
	
	def updateClaimsTable (ds: DataStore): ClaimsTable = {
		val newClaimsTable = new ClaimsTable
	      
		ds.membersTable.iterator.foreach {member =>
			val memberClaims = ds.claimsTable.forMemberId (member.memberId).toList
			val claimsYear1 = memberClaims.filter (_.year == Year1).toList
			val claimsYear2 = memberClaims.filter (_.year == Year2).toList
			val claimsYear3 = memberClaims.filter (_.year == Year3).toList
			
			if (claimsYear1 != Nil) {
				addClaimsCenter (claimsYear1, newClaimsTable)
			}
			
			if (claimsYear2 != Nil) {
				if (claimsYear1 != Nil) {
					addClaimsFront (claimsYear2, newClaimsTable)
				} else {
					addClaimsCenter (claimsYear2, newClaimsTable)
				}
			}
			
			if (claimsYear3 != Nil) {
				if (claimsYear2 != Nil) {
					addClaimsFront (claimsYear3, newClaimsTable)
				} else {
					addClaimsCenter (claimsYear3, newClaimsTable)
				}
			}
		}
		
		newClaimsTable
	}
	
	def addClaimsCenter (claims: List[ClaimTuple], claimsTable: ClaimsTable) = {
		val dsfsClaims = claims.filter (_.dsfs != UnknownDsfs)
		val min =
		  	if (dsfsClaims != Nil) dsfsClaims.minBy (_.dsfs.code).dsfs.code
			else 0
		val max =
		  	if (dsfsClaims != Nil) dsfsClaims.maxBy (_.dsfs.code).dsfs.code
		  	else 0
		val mid = (max + min) / 2
		
		claims.foreach {claim =>
		  	val month =
		  	  	if (claim.dsfs == UnknownDsfs) UnknownMonth
		  		else Month.forCode (5 - mid + claim.dsfs.code)
			claimsTable += updateClaim (claim, month)
		}
	}
	
	def addClaimsOffCenter (claims: List[ClaimTuple], claimsTable: ClaimsTable) = {
		val dsfsClaims = claims.filter (_.dsfs != UnknownDsfs)
		val min =
		  	if (dsfsClaims != Nil) dsfsClaims.minBy (_.dsfs.code).dsfs.code
			else 0
		val max =
		  	if (dsfsClaims != Nil) dsfsClaims.maxBy (_.dsfs.code).dsfs.code
		  	else 0
		val mid = (max + min) * 3 / 4
		
		claims.foreach {claim =>
		  	val month =
		  	  	if (claim.dsfs == UnknownDsfs) UnknownMonth
		  		else Month.forCode (8 - mid + claim.dsfs.code)
			claimsTable += updateClaim (claim, month)
		}
	}
	
	def addClaimsFront (claims: List[ClaimTuple], claimsTable: ClaimsTable) = {
		val dsfsClaims = claims.filter (_.dsfs != UnknownDsfs)
		val min =
		  	if (dsfsClaims != Nil) dsfsClaims.minBy (_.dsfs.code).dsfs.code
			else 0
		
		claims.foreach {claim =>
		  	val month =
		  	  	if (claim.dsfs == UnknownDsfs) UnknownMonth
		  		else Month.forCode (claim.dsfs.code - min)
			claimsTable += updateClaim (claim, month)
		}
	}
	
	def addClaimsEnd (claims: List[ClaimTuple], claimsTable: ClaimsTable) = {
		val dsfsClaims = claims.filter (_.dsfs != UnknownDsfs)
		val max =
		  	if (dsfsClaims != Nil) dsfsClaims.maxBy (_.dsfs.code).dsfs.code
		  	else 0
		
		claims.foreach {claim =>
		  	val month =
		  	  	if (claim.dsfs == UnknownDsfs) UnknownMonth
		  		else Month.forCode (11 - max + claim.dsfs.code)
			claimsTable += updateClaim (claim, month)
		}
	}
	
	def updateClaim (claim: ClaimTuple, month: Month): ClaimTuple = {
		ClaimTuple (claim.claimId,
					claim.memberId,
					claim.providerId,
					claim.vendor,
					claim.pcp,
					claim.year,
					claim.specialty,
					claim.placeSvc,
					claim.payDelay,
					claim.lengthOfStay,
					claim.dsfs,
					month,
					claim.primaryConditionGroup,
					claim.charlsonIndex,
					claim.procedureGroup,
					claim.supLos)
	}
}