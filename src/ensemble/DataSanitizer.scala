package ensemble
import file.FileLoader
import datatypes.AgeAtFirstClaim
import datatypes.AgeAtFirstClaim._
import datatypes.Sex._
import datatypes.DataStore
import datatypes.MemberTuple
import pattern.SanitizerPatternLoader
import features.SanitizerFeatures
import nn.Nn
import pattern.Pattern
import file.SanitizedWriter
import features.Feature
import features.RealFeature

object DataSanitizer {
	def go = {
		println ("Loading data...")
		val ds = FileLoader.loadAllUnsanitized
		
		println ("Sanitizing data...")
		sanitizeUnknownSex (ds, SanitizerFeatures.unknownSexFeatures)
		sanitizeUnknownAge (ds)
		sanitizeUnknownAgeAndSex (ds)
		
		println ("Writing data...")
		SanitizedWriter.write (ds)
	}
	
	def sanitizeUnknownSex (ds: DataStore, features: List[RealFeature]) = {
		println ("Learning for sex...")
		val trainMembers = knownAgeAndSex (ds)
		val trainPattern = SanitizerPatternLoader.loadForSex (ds, trainMembers, features)
		val nn = new Nn (trainPattern, trainPattern)
		nn.run
		val targetMembers = unknownSexKnownAge (ds)
		val targetPattern = SanitizerPatternLoader.loadForSex (ds, targetMembers, features)
		nn.computeEntry (targetPattern)
		updateMemberSexDataStore (ds, targetPattern)
	}
	
	def updateMemberSexDataStore (ds: DataStore, targetPattern: Pattern) = {
		println ("Updating sex...")
		for (i <- 0 until targetPattern.getNumPatterns) {
			val memberId = targetPattern.getId (i)
			val member = ds.membersTable.forMemberId (memberId)
			val updated = MemberTuple (memberId, member.ageAtFirstClaim,
				if (targetPattern.getOutput (i) > 1.1) Male
				else if (targetPattern.getOutput (i) > -0.1) SexUnknown
				else Female)
			ds.membersTable += updated
		}
	}
	
	def sanitizeUnknownAge (ds: DataStore) = {
		val targetPatterns = AgeAtFirstClaim.values.map (age =>
		  	(age, targetPatternForAge (ds, age, unknownAgeKnownSex (ds))))
		updateMemberAgeDataStore (ds, targetPatterns)
	}
	
	def targetPatternForAge (ds: DataStore, age: AgeAtFirstClaim,
			targetMembers: Iterator[MemberTuple]): Pattern = {
		println ("Learning for " + age + "...")
		val trainMembers = knownAgeAndSex (ds)
		val features = SanitizerFeatures.unknownAgeFeatures
		val trainPattern = SanitizerPatternLoader.loadForAge (ds, trainMembers, features, age)
		val nn = new Nn (trainPattern, trainPattern)
		nn.run
		val targetPattern = SanitizerPatternLoader.loadForAge (ds, targetMembers, features, age)
		nn.computeEntry (targetPattern)
		targetPattern
	}
	
	def updateMemberAgeDataStore (ds: DataStore,
			targetPatterns: List[(AgeAtFirstClaim, Pattern)]) = {
		println ("Updating age...")
		val numPatterns = targetPatterns.head._2.getNumPatterns
		for (i <- 0 until numPatterns) {
			val (mostLikelyAge, targetPattern) = targetPatterns.maxBy (_._2.getOutput (i))
			val confidence = targetPattern.getOutput (i)
			val memberId = targetPattern.getId (i)
			val member = ds.membersTable.forMemberId (memberId)
			val updated = MemberTuple (memberId, 
			    if (confidence > 1.1) mostLikelyAge else AgeUnknown,
			    member.sex)
			ds.membersTable += updated
		}
	}
	
	def sanitizeUnknownAgeAndSex (ds: DataStore) = {
		println ("\n****************************\nNow working on unknown age and sex...")
		val targetPatterns = AgeAtFirstClaim.values.map (age =>
		  	(age, targetPatternForAge (ds, age, unknownAgeAndSex (ds))))
		updateMemberAgeDataStore (ds, targetPatterns)
		
		sanitizeUnknownSex (ds, SanitizerFeatures.unknownAgeAndSexFeatures)
	}
	
	def knownAgeAndSex (ds: DataStore): Iterator[MemberTuple] = {
		ds.membersTable.iterator.filter (member =>
			(member.ageAtFirstClaim != AgeUnknown) && (member.sex != SexUnknown))
	}
	
	def unknownAgeAndSex (ds: DataStore): Iterator[MemberTuple] = {
		ds.membersTable.iterator.filter (member =>
			(member.ageAtFirstClaim == AgeUnknown) && (member.sex == SexUnknown))
	}
	
	def unknownAgeKnownSex (ds: DataStore): Iterator[MemberTuple] = {
		ds.membersTable.iterator.filter (member =>
			(member.ageAtFirstClaim == AgeUnknown) && (member.sex != SexUnknown))
	}
	
	def unknownSexKnownAge (ds: DataStore): Iterator[MemberTuple] = {
		ds.membersTable.iterator.filter (member =>
			(member.ageAtFirstClaim != AgeUnknown) && (member.sex == SexUnknown))
	}
}