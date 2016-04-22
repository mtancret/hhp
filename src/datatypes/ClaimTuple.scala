package datatypes

case class ClaimTuple (
		claimId: Int,
		memberId: Int,
		providerId: Int,
		vendor: Int,
		pcp: Int,
		year: Year,
		specialty: Specialty,
		placeSvc: PlaceSvc,
		payDelay: PayDelay,
		lengthOfStay: LengthOfStay,
		dsfs: Dsfs,
		month: Month,
		primaryConditionGroup: PrimaryConditionGroup,
		charlsonIndex: CharlsonIndex,
		procedureGroup: ProcedureGroup,
		supLos: Int
)
