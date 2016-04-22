package ensemble
import features.ClaimDateFeatures
import nn.Nn
import file.TargetWriter
import pattern.Pattern
import features.RealFeature
import file.FileLoader
import pattern.TargetPatternLoader
import datatypes.Year._
import datatypes.DataStore

object ClaimDateEnsemble {
		
	def go = {
		println ("Creating features...")
		val features = ClaimDateFeatures.features
		
		println ("Loading patterns...")
		val ds = FileLoader.loadAllClaimDate
		val (trainPatterns, validatePatterns) =
		  	TargetPatternLoader.loadInOutYearsSplit (ds, features, Year2, Year3)
		
		println ("Training...")
		var nn = new Nn (trainPatterns, validatePatterns)
		nn.run
		
		println ("Loading target patterns...")
		val targetPatterns =
		  	TargetPatternLoader.loadInOutYears (ds, features, Year3, Year4)
		nn.computeEntry (targetPatterns)
		
		println ("Writing target file...")
		TargetWriter.writeTarget (targetPatterns, "entry/2012-11-14.csv")
	}
}