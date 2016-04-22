package pattern
import datatypes.DataStore
import features.Feature
import datatypes.Year
import datatypes.MemberTuple
import datatypes.DaysInHospitalTuple
import features.RealFeature

object EwmaPatternLoader {  
	def loadInOutYears (ds: DataStore, features: List[RealFeature],
			memberDays: Iterator[DaysInHospitalTuple],
			inYear: Year, outYear: Year): Pattern = {
		val numPatterns = ds.daysInHospitalTable.iterator (outYear).length
		val numFeatures = features.length
		var pattern = new Pattern (numPatterns, numFeatures)
		
		memberDays.zipWithIndex.foreach {
			case (daysInHospital, patternIdx) =>
	
			features.zipWithIndex.foreach {
			  	case (feature, featureIdx) =>
				
			  	pattern.setInput (
				    patternIdx,
				    featureIdx,
				    feature.get (ds, daysInHospital.memberId, inYear))
	    		}

			pattern.setOutput (
			    patternIdx,
			    Math.log(daysInHospital.days + 1.0),
			    daysInHospital.memberId)
		}

		pattern
	}
}