package file

import datatypes.DaysInHospitalTable
import java.io._
import pattern.Pattern
import pattern.ScalaPattern
import pattern.PatternSet

object ArffWriter {
	def write (fileName: String, patternSet: PatternSet) = {
		val writer = new java.io.PrintWriter (fileName)
		val numInputs = patternSet.features.length
		
		try {
			writer.println ("@RELATION patterns")
 
			patternSet.features.foreach {feature =>
				writer.print ("@ATTRIBUTE ")
				writer.print (feature)
				writer.print (" ")
				writer.println (feature.typeString)
			}
			
			writer.print ("@ATTRIBUTE ")
			writer.print (patternSet.output)
			writer.print (" ")
			writer.println (patternSet.output.typeString)
			
			writer.println ("@DATA")
			patternSet.patterns.foreach {pattern =>
			  	writer.print (pattern.inputs.head)
			  	pattern.inputs.tail.foreach {input =>
			  		writer.print (",")
			  		writer.print (input)
			  	}
			  	writer.print (",")
			  	writer.println (pattern.output)
			}
		} finally {
			writer.close ()
		}
	}
}