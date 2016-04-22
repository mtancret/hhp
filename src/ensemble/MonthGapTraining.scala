package ensemble
import file.FileLoader
import preprocess.Preprocess
import pattern.MonthGapPatternLoader
import features.MonthGapFeatures
import file.ArffWriter

object MonthGapTraining {
	def go = {
		println ("Loading data...")
		val dsRaw = FileLoader.loadAll
		
		println ("Preprocessing data...")
		val dsOrdred = Preprocess.orderClaimsDsfs (dsRaw)
		val ds = Preprocess.filterMonthGap (dsOrdred)
		
		println ("Creating patterns...")
		val features = MonthGapFeatures.features
		val output = MonthGapFeatures.output
		val patterns = MonthGapPatternLoader.loadAll (ds, features, output)
		
		println ("Writing patterns to data/weka/monthGap.arff...")
		ArffWriter.write ("data/weka/monthGap.arff", patterns)
	}
}