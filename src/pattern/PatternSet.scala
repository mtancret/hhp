package pattern
import features.Feature

case class PatternSet (features: List[Feature[_]], output: Feature[_],
		patterns: List[ScalaPattern]) {
  
	def iterator: Iterator[Iterator[(Any, Feature[_])]] = {
		patterns.iterator.map {pattern =>
			pattern.inputs.iterator.zip (features.iterator) ++
			Iterator ((pattern.output, output))
		}  
	}
	
	/*
	override def toString: String = {
		iterator.foreach {patternFeature =>
		  	patternFeature.foreach {case (value, feature) =>
		  	  	feature.toString (value)
		  	}
		}
	}
	*/
}