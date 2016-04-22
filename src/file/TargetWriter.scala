package file
import datatypes.DaysInHospitalTable
import java.io._
import pattern.Pattern

object TargetWriter {
  
	def writeTarget (targetPattern: Pattern, fileName: String) = {
		val writer = new java.io.PrintWriter (fileName)
		
		try {
			writer.println ("MemberID,DaysInHospital")
			for(i <- 0 until targetPattern.getNumPatterns ()) {
				writer.print (targetPattern.getId (i))
				writer.print (",")
				writer.println ("%f" format targetPattern.getOutput (i))
			}
		} finally {
			writer.close ()
		}
	}
}