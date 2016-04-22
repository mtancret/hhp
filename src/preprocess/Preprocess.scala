package preprocess
import datatypes.DataStore
import datatypes.ClaimsTable
import datatypes.Dsfs._

object Preprocess {
	def orderClaimsDsfs (ds: DataStore): DataStore = {
		val newClaimsTable = new ClaimsTable
		
		ds.claimsTable.memberYearClaimsIterator.foreach {
		  		case ((member, year), claims) =>
		  	newClaimsTable +=
		  	  	(member, year, claims.sortWith (_.dsfs.code < _.dsfs.code))
		}
		
		DataStore (newClaimsTable,
			ds.daysInHospitalTable,
			ds.drugCountTable,
			ds.labCountTable,
			ds.membersTable)
	}
	
	def filterMonthGap (ds: DataStore): DataStore = {
		val newClaimsTable = new ClaimsTable
		
		ds.claimsTable.memberYearClaimsIterator.foreach {
		  		case ((member, year), claims) =>
		  	val firstMonthClaim = claims.find (_.dsfs != UnknownDsfs)
		  	val secondMonthClaim = firstMonthClaim match {
		  	  	case None => None
		  	  	case _ => claims.find (claim =>
		  	  	  	claim.dsfs != firstMonthClaim.get.dsfs &&
		  	  	    claim.dsfs != UnknownDsfs)
		  	}
		  	if (secondMonthClaim != None) {
		  		newClaimsTable +=
		  			(member, year, claims)
		  	}
		}
		
		DataStore (newClaimsTable,
			ds.daysInHospitalTable,
			ds.drugCountTable,
			ds.labCountTable,
			ds.membersTable)
	}
}