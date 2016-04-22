import file.FileLoader
import datatypes.AgeAtFirstClaim._
import datatypes.Sex._

object RunStats extends App {
	println ("Loading files...")
	val ds = FileLoader.loadAll

	val memberCount = ds.membersTable.iterator.length
	
	val unknownAgeCount = ds.membersTable.iterator.filter (
	    _.ageAtFirstClaim == AgeUnknown).length
	
	val unknownSexCount = ds.membersTable.iterator.filter (
	    _.sex == SexUnknown).length
		
	val unknownAgeAndSexCount = ds.membersTable.iterator.filter (member =>
	  	member.ageAtFirstClaim == AgeUnknown && member.sex == SexUnknown).length
	
    print ("memberCount: ")
	println (memberCount)
	print ("unknownAgeCount: ")
	println (unknownAgeCount)
	print ("unknownSexCount: ")
	println (unknownSexCount)
	print ("unknownAgeAndSexCount: ")
	println (unknownAgeAndSexCount)
}