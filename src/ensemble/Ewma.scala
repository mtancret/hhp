package ensemble
import file.FileLoader
import datatypes.PrimaryConditionGroup
import datatypes.DataStore
import datatypes.Year._
import queries.CommonQuery
import datatypes.Year
import pattern.EwmaPatternLoader
import features.EwmaFeatures
import nn.Nn

object Ewma {
	def go = {
		println ("Loading data...")
		
		val trainInYear = Year2
		val trainOutYear = Year3
		val validateInYear = Year1
		val validateOutYear = Year2
		
		val trainDs = FileLoader.loadInOutYears (trainInYear, trainOutYear)
		val validateDs = FileLoader.loadInOutYears (validateInYear, validateOutYear)
		
		val alphas = PrimaryConditionGroup.values.map (findOptimalAlpha (trainDs,
		    validateDs, _, trainInYear, trainOutYear, validateInYear, validateOutYear))
		    
		println (alphas)
	}
	
	def findOptimalAlpha (trainDs: DataStore, validateDs: DataStore,
			condition: PrimaryConditionGroup, trainInYear: Year, trainOutYear: Year,
			validateInYear: Year, validateOutYear: Year): Double = {

		testAlpha (trainDs, validateDs, condition, trainInYear, trainOutYear,
		    validateInYear, validateOutYear)
	}
	
	def testAlpha (trainDs: DataStore, validateDs: DataStore,
			condition: PrimaryConditionGroup, trainInYear: Year, trainOutYear: Year,
			validateInYear: Year, validateOutYear: Year): Double = {
		val features = EwmaFeatures.features (List (condition))
		
		val trainDihWithCondition =
		  	trainDs.daysInHospitalTable.iterator (trainOutYear).
		  		filter (dih => CommonQuery.hasPrimaryConditionGroup (
		  			trainDs, dih.memberId, condition) == 1)
		val trainPatterns = EwmaPatternLoader.loadInOutYears (trainDs, features,
		    trainDihWithCondition, trainInYear, trainOutYear)
		
		val validateDihWithCondition =
		  	validateDs.daysInHospitalTable.iterator (validateOutYear).
		  		filter (dih => CommonQuery.hasPrimaryConditionGroup (
		  			validateDs, dih.memberId, condition) == 1)
		val validatePatterns = EwmaPatternLoader.loadInOutYears (validateDs, features,
		    validateDihWithCondition, validateInYear, validateOutYear)
		
		var nn = new Nn (trainPatterns, validatePatterns)
		nn.run
		nn.calcValidationError
	}
}