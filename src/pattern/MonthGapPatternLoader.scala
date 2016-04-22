package pattern
import datatypes.DataStore
import features.Feature
import datatypes.Year
import datatypes.Dsfs._

object MonthGapPatternLoader {
	def loadAll (ds: DataStore, features: List[Feature[_]], outputFeature: Feature[_]):
			PatternSet = {
		val patterns = ds.claimsTable.memberYearClaimsIterator.map {
		  		case ((memberId, year), claims) =>
			val inputs = features.map (_.get (ds, memberId, year))
		  	val output = outputFeature.get (ds, memberId, year)
			val patternId = memberId * 4 + year.code
		  	ScalaPattern (patternId, inputs, output)
		}.toList
		PatternSet (features, outputFeature, patterns)
	}
}