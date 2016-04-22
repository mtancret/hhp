package file
import datatypes.DataStore

object SanitizedWriter {
	def write (ds: DataStore) = {
		writeMembers (ds, "data/Sanitized/Members.csv")
	}
	
	def writeMembers (ds: DataStore, fileName: String) = {
		val writer = new java.io.PrintWriter (fileName)
		
		try {
			writer.println ("MemberID,AgeAtFirstClaim,Sex")
			ds.membersTable.iterator.foreach { member =>
				writer.print (member.memberId)
				writer.print (",")
				writer.print (member.ageAtFirstClaim.name)
				writer.print (",")
				writer.println (member.sex.name)
			}
		} finally {
			writer.close ()
		}
	}
}