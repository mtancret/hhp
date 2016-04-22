package file
import datatypes.DataStore

object ClaimDateWriter {
	def write (ds: DataStore) = {
		writeClaims (ds, "data/ClaimDate/Claims.csv")
	}
	
	def writeClaims (ds: DataStore, fileName: String) = {
		val writer = new java.io.PrintWriter (fileName)
		
		try {
			writer.println ("MemberID,ProviderID,Vendor,PCP,Year,Specialty,PlaceSvc," +
					"PayDelay,LengthOfStay,DSFS,month,PrimaryConditionGroup," +
					"CharlsonIndex,ProcedureGroup,SupLOS")
			ds.claimsTable.iterator.foreach { claim =>
				writer.print (claim.memberId)
				writer.print (",")
				writer.print (claim.providerId)
				writer.print (",")
				writer.print (claim.vendor)
				writer.print (",")
				writer.print (claim.pcp)
				writer.print (",")
				writer.print (claim.year.text)
				writer.print (",")
				writer.print (claim.specialty.text)
				writer.print (",")
				writer.print (claim.placeSvc.text)
				writer.print (",")
				writer.print (claim.payDelay.text)
				writer.print (",")
				writer.print (claim.lengthOfStay.text)
				writer.print (",")
				writer.print (claim.dsfs.text)
				writer.print (",")
				writer.print (claim.month.text)
				writer.print (",")
				writer.print (claim.primaryConditionGroup.text)
				writer.print (",")
				writer.print (claim.charlsonIndex.text)
				writer.print (",")
				writer.print (claim.procedureGroup.text)
				writer.print (",")
				writer.println (claim.supLos)
			}
		} finally {
			writer.close ()
		}
	}
}