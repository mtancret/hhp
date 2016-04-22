package pattern
import datatypes.DataStore
import features.Feature
import datatypes.Year
import features.RealFeature

object TargetPatternLoader {  
	def loadInOutYears (ds: DataStore, features: List[RealFeature],
			inYear: Year, outYear: Year): Pattern = {
		val numPatterns = ds.daysInHospitalTable.iterator (outYear).length
		val numFeatures = features.length
		val pattern = new Pattern (numPatterns, numFeatures)
		
		ds.daysInHospitalTable.iterator (outYear).zipWithIndex.foreach {
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
	
	/**
	 * Splits data into training and validation patterns
	 */
	def loadInOutYearsSplit (ds: DataStore, features: List[RealFeature],
			inYear: Year, outYear: Year): (Pattern, Pattern) = {
		val numPatterns = ds.daysInHospitalTable.iterator (outYear).length
		val numFeatures = features.length
		val numTrainPatterns = (numPatterns * 0.8).toInt
		val numValidatePatterns = numPatterns - numTrainPatterns
		val trainPattern = new Pattern (numTrainPatterns, numFeatures)
		val validatePattern = new Pattern (numValidatePatterns, numFeatures)
		
		ds.daysInHospitalTable.iterator (outYear).zipWithIndex.foreach {
			case (daysInHospital, patternIdx) =>
			
			if (patternIdx < numTrainPatterns) {
				features.zipWithIndex.foreach {case (feature, featureIdx) =>
				  	trainPattern.setInput (
					    patternIdx,
					    featureIdx,
					    feature.get (ds, daysInHospital.memberId, inYear))
		    		}
	
				trainPattern.setOutput (
				    patternIdx,
				    Math.log(daysInHospital.days + 1.0),
				    daysInHospital.memberId)
			} else {
				features.zipWithIndex.foreach {case (feature, featureIdx) =>
				  	validatePattern.setInput (
					    patternIdx - numTrainPatterns,
					    featureIdx,
					    feature.get (ds, daysInHospital.memberId, inYear))
		    		}
	
				validatePattern.setOutput (
				    patternIdx - numTrainPatterns,
				    Math.log(daysInHospital.days + 1.0),
				    daysInHospital.memberId)
			}
		}

		(trainPattern, validatePattern)
	}
}