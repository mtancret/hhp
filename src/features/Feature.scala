package features
import datatypes.DataStore
import datatypes.Enum
import datatypes.Year

abstract class Feature[T] {
	def get (ds: DataStore, memberId: Int, year: Year): T
	def toString (value: T): String
	def typeString: String
}

abstract class IntegerFeature extends Feature[Int] {
	override def toString (value: Int) =
		value.toString
	override def typeString = "INTEGER"
}

abstract class RealFeature extends Feature[Double] {
	override def toString (value: Double) =
		"%0.5f" format value
	override def typeString = "REAL"
}

abstract class NominalFeature[T] (enum: Enum[_]) extends Feature[T] {
	override def toString (value: T) =
		value.toString
	override def typeString: String = enum.valuesToString
}