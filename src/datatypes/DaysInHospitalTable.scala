package datatypes

import scala.collection.mutable.HashMap

class DaysInHospitalTable (private var daysForMemberId: HashMap[(Int, Year), List[DaysInHospitalTuple]])
		extends Iterable [DaysInHospitalTuple] {
	
	def this () = this (HashMap ())
	
	def +=(days: DaysInHospitalTuple) = {
		val key = (days.memberId, days.year)
		daysForMemberId += (key -> (days :: daysForMemberId.getOrElse (key, List ())))
	}
	
	def ++=(newDays: DaysInHospitalTable) = {
	  newDays.iterator foreach (this += _)
	}
	
	def forMemberId (id: Int, year: Year): Iterator[DaysInHospitalTuple] =
		daysForMemberId.getOrElse ((id, year), List ()).iterator
	
	def iterator: Iterator[DaysInHospitalTuple] =
		daysForMemberId.valuesIterator.map (_.iterator).flatten
	
	def iterator (year: Year): Iterator[DaysInHospitalTuple] =
	  	iterator.filter (_.year == year)
}