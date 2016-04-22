package ensemble

import file.FileLoader
import datatypes.Year._
import pattern.Pattern
import pattern.TargetPatternLoader
import nn.Nn
import features.Feature
import file.TargetWriter
import features.CommonFeatures
import features.RealFeature

object Ensemble {
  
	def go = {
		println ("Creating features...")
		val features = CommonFeatures.features
		
		println ("Loading patterns...")
		val ds = FileLoader.loadAll
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

