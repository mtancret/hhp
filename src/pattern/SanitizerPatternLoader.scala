package pattern
import features.Feature
import datatypes.DataStore
import datatypes.Year._
import datatypes.Year
import datatypes.MemberTuple
import datatypes.AgeAtFirstClaim
import features.RealFeature

object SanitizerPatternLoader {
	def loadForSex (ds: DataStore, members: Iterator[MemberTuple],
			features: List[RealFeature]): Pattern = {
		val (members1, members2) = members.duplicate
		val numPatterns = members1.length
		val numFeatures = features.length
		var pattern = new Pattern (numPatterns, numFeatures)
		
		members2.zipWithIndex.foreach { case (member, patternIdx) =>
			features.zipWithIndex.foreach { case (feature, featureIdx) =>
			  	pattern.setInput (
				    patternIdx,
				    featureIdx,
				    feature.get (ds, member.memberId, YearUnknown))
	    		}

			pattern.setOutput (
			    patternIdx,
			    member.sex.code,
			    member.memberId)
		}

		pattern
	}
	
	def loadForAge (ds: DataStore, members: Iterator[MemberTuple],
			features: List[RealFeature], age: AgeAtFirstClaim): Pattern = {
		val (members1, members2) = members.duplicate
		val numPatterns = members1.length
		val numFeatures = features.length
		var pattern = new Pattern (numPatterns, numFeatures)
		
		members2.zipWithIndex.foreach { case (member, patternIdx) =>
			features.zipWithIndex.foreach { case (feature, featureIdx) =>
			  	pattern.setInput (
				    patternIdx,
				    featureIdx,
				    feature.get (ds, member.memberId, YearUnknown))
	    		}

			pattern.setOutput (
			    patternIdx,
			    if (member.ageAtFirstClaim == age) 1.0 else 0.0,
			    member.memberId)
		}

		pattern
	}
}