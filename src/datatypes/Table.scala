package datatypes

/**
 * T is a type of Tuple.
 */
abstract class Table[T] {
	def iterator: Iterator[T]
}