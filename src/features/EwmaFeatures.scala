package features
import datatypes.PrimaryConditionGroup._
import datatypes.PrimaryConditionGroup

object EwmaFeatures {
	def features (conditions: List[PrimaryConditionGroup]): List[RealFeature] = {
		conditions.map (PrimaryConditionMovingAvg).toList :::
		List (Bias)
	}
}