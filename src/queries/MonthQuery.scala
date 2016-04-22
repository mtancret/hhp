package queries
import datatypes.Year
import datatypes.DataStore
import datatypes.PlaceSvc._
import datatypes.Dsfs
import datatypes.PlaceSvc
import datatypes.PrimaryConditionGroup
import datatypes.ProcedureGroup
import datatypes.Specialty
import datatypes.CharlsonIndex
import datatypes.Month._
import datatypes.Month

/**
 * All of these queries assume claims are already sorted by increasing dsfs and that
 * there are no empty claims lists. Specifically, this can be achieved with:
 * 
 * val dsOrdred = Preprocess.orderClaimsDsfs (dsRaw)
 * val ds = Preprocess.filterMonthGap (dsOrdred)
 */
object MonthQuery {
	val quarters = Map[Int,List[Month]](1 -> List (Month0, Month1, Month2),
			2 -> List (Month3, Month4, Month5),
			3 -> List (Month6, Month7, Month8),
			4 -> List (Month9, Month10, Month11),
			5 -> List (Month12, UnknownMonth))
  
	def numClaimsDsfs (ds: DataStore, memberId: Int, year: Year, dsfs: Dsfs): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val dsfsClaims = memberClaims.filter (_.dsfs == dsfs)
		dsfsClaims.length
	}
	
	def numClaimsQuarter (ds: DataStore, memberId: Int, year: Year, quarter: Int): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val months = quarters.get (quarter).get
		val quarterClaims = memberClaims.filter (claim => months.contains (claim.month))
		quarterClaims.length
	}
	
	//TODO: may be more than one Charlson Index in a single month
	def charlsonIndexDsfs (ds: DataStore, memberId: Int, year: Year,
			dsfs: Dsfs): CharlsonIndex = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val dsfsClaims = memberClaims.filter (_.dsfs == dsfs)
		dsfsClaims.next().charlsonIndex
	}
  
	def totalInpatientHospitalLosDsfs (ds: DataStore, memberId: Int, year: Year,
			dsfs: Dsfs): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val inpatientClaims = memberClaims.filter (claim =>
		  	claim.placeSvc == InpatientHospital &&
		  	claim.dsfs == dsfs)
		inpatientClaims.map (_.lengthOfStay.code).sum
	}
	
	def totalUrgentCareLosDsfs (ds: DataStore, memberId: Int, year: Year,
			dsfs: Dsfs): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val urgentCareClaims = memberClaims.filter (claim =>
		  	claim.placeSvc == UrgentCare &&
		  	claim.dsfs == dsfs)
		urgentCareClaims.map (_.lengthOfStay.code).sum
	}
	
	def avgPayDelayDsfs (ds: DataStore, memberId: Int, year: Year, dsfs: Dsfs): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val dsfsClaims = memberClaims.filter (_.dsfs == dsfs)
		val total = dsfsClaims.map (_.payDelay.code).sum
		// TODO: What value to assume when no claims?
		if (dsfsClaims.isEmpty) 0.0 else total.toDouble / memberClaims.length
	}
	
	def placeSvcCountDsfs (ds: DataStore, memberId: Int, year: Year,
			placeSvc: PlaceSvc, dsfs: Dsfs): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val dsfsClaims = memberClaims.filter (_.dsfs == dsfs)
		dsfsClaims.count (_.placeSvc == placeSvc)
	}
	
	def primaryConditionCountDsfs (ds: DataStore, memberId: Int, year: Year, 
			condition: PrimaryConditionGroup, dsfs: Dsfs): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val dsfsClaims = memberClaims.filter (_.dsfs == dsfs)
		dsfsClaims.count (_.primaryConditionGroup == condition)
	}
		
	def primaryConditionCountQuarter (ds: DataStore, memberId: Int, year: Year, 
			condition: PrimaryConditionGroup, quarter: Int): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val months = quarters.get (quarter).get
		val quarterClaims = memberClaims.filter (claim => months.contains (claim.month))
		quarterClaims.count (_.primaryConditionGroup == condition)
	}
	
	def primaryConditionFirstMonth (ds: DataStore, memberId: Int, year: Year, 
			condition: PrimaryConditionGroup): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val conditionClaims = memberClaims.filter (_.primaryConditionGroup == condition)
		if (conditionClaims.hasNext)
			conditionClaims.minBy (_.month.code).month.code
		else
			0
	}
	
	def procedureCountDsfs (ds: DataStore, memberId: Int, year: Year,
			procedure: ProcedureGroup, dsfs: Dsfs): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val dsfsClaims = memberClaims.filter (_.dsfs == dsfs)
		dsfsClaims.count (_.procedureGroup == procedure)
	}
	
	def specialtyCountDsfs (ds: DataStore, memberId: Int, year: Year,
			specialty: Specialty, dsfs: Dsfs): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val dsfsClaims = memberClaims.filter (_.dsfs == dsfs)
		dsfsClaims.count (_.specialty == specialty)
	}
	
	def specialtyCountQuarter (ds: DataStore, memberId: Int, year: Year, 
			specialty: Specialty, quarter: Int): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val months = quarters.get (quarter).get
		val quarterClaims = memberClaims.filter (claim => months.contains (claim.month))
		quarterClaims.count (_.specialty == specialty)
	}
	
	def placeSvcCountQuarter (ds: DataStore, memberId: Int, year: Year,
			place: PlaceSvc, quarter: Int): Double = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val months = quarters.get (quarter).get
		val quarterClaims = memberClaims.filter (claim => months.contains (claim.month))
		quarterClaims.count (_.placeSvc == place)
	}
	
	def procedureCountQuarter (ds: DataStore, memberId: Int, year: Year, 
			procedure: ProcedureGroup, quarter: Int): Int = {
		val memberClaims = ds.claimsTable.forMemberId (memberId, year)
		val months = quarters.get (quarter).get
		val quarterClaims = memberClaims.filter (claim => months.contains (claim.month))
		quarterClaims.count (_.procedureGroup == procedure)
	}
}