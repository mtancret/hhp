package datatypes

import scala.collection.mutable.HashMap

class ClaimsTable (private var claimsForMemberYear: HashMap[(Int, Year), List[ClaimTuple]])
		extends Iterable [ClaimTuple] {

	def this () = this (HashMap ())
	
	def +=(claim: ClaimTuple) = {
		val key = (claim.memberId, claim.year)
		claimsForMemberYear += (key -> (claim :: claimsForMemberYear.getOrElse (key, List ())))
	}
	
	def +=(memberId: Int, year: Year, claims: List[ClaimTuple]) = {
		claimsForMemberYear += ((memberId, year) -> claims)
	}
	
	def forMemberId (id: Int, year: Year): Iterator[ClaimTuple] =
		claimsForMemberYear.getOrElse ((id, year), List ()).iterator
		
	def forMemberId (id: Int): Iterator[ClaimTuple] =
	  	Year.values.map (forMemberId (id, _)).flatten.toIterator
	
	def iterator: Iterator[ClaimTuple] =
		claimsForMemberYear.valuesIterator.map (_.iterator).flatten
		
	def iterator (year: Year): Iterator[ClaimTuple] =
	  	iterator.filter (_.year == year)
	  	
	def memberYearClaimsIterator: Iterator[((Int, Year), List[ClaimTuple])] =
	  claimsForMemberYear.iterator
	
	def memberClaimsIterator (year: Year): Iterator[Iterator[ClaimTuple]] = {
	  	val keys = claimsForMemberYear.keysIterator.filter (_._2 == year)
	  	keys.map (claimsForMemberYear (_).toIterator)
	}
}