package queries
import datatypes.DataStore
import datatypes.Year

case class CommonStats (avgNumClaims: Double)

object CommonStats {
  /*
	def computeCommonStats (ds: DataStore, year: Year): CommonStats = {
	  	CommonStats (
	  		avgNumClaims (ds, year)
	  	)
	}
	
	def avgNumClaims (ds: DataStore, year: Year) = {
		val numMembers = ds.claimsTable.iterator (year).length
		val sumClaims = ds.claimsTable.iterator (year).map (_.length).sum
		sumClaims.toDouble / numMembers
	}
	*/
}