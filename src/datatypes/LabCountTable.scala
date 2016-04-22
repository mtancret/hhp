package datatypes

import scala.collection.mutable.HashMap

class LabCountTable (private var countForMemberId: HashMap[(Int, Year), List[LabCountTuple]])
		extends Iterable [LabCountTuple] {

	def this () = this (HashMap ())
	
	def +=(count: LabCountTuple) = {
		val key = (count.memberId, count.year)
		countForMemberId += (key -> (count :: countForMemberId.getOrElse (key, List ())))
	}
	
	def forMemberId (id: Int, year: Year): Iterator[LabCountTuple] =
		countForMemberId.getOrElse ((id, year), List ()).iterator
	
	def iterator: Iterator[LabCountTuple] =
		countForMemberId.valuesIterator.map (_.iterator).flatten
}