package datatypes

class MembersTable (private var membersForId: scala.collection.Map[Int, MemberTuple])
		extends Iterable [MemberTuple] {

	def this () = this (Map ())
	
	def +=(member: MemberTuple) = {
		membersForId += (member.memberId -> member)
	}
	
	def forMemberId (id: Int): MemberTuple = membersForId (id)
	
	def iterator: Iterator[MemberTuple] = membersForId.values.iterator
}