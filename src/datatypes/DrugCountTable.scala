package datatypes

import scala.collection.mutable.HashMap

class DrugCountTable (private var countForMemberId: HashMap[(Int, Year), List[DrugCountTuple]])
		extends Iterable [DrugCountTuple] {

	def this () = this (HashMap ())
	
	def +=(count: DrugCountTuple) = {
		val key = (count.memberId, count.year)
		countForMemberId += (key -> (count :: countForMemberId.getOrElse (key, List ())))
	}
	
	def forMemberId (id: Int, year: Year): Iterator[DrugCountTuple] =
		countForMemberId.getOrElse ((id, year), List ()).iterator
	
	def iterator: Iterator[DrugCountTuple] =
		countForMemberId.valuesIterator.map (_.iterator).flatten
}