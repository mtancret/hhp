package claimsolver

import alice.tuprolog._
import scala.collection.JavaConversions._
import datatypes.ClaimTuple
import file.FileLoader
import datatypes.Year._

object ClaimSolver {
	def run = {
		val ds = FileLoader.loadAll
		ds.membersTable.iterator.foreach {member =>
		  	val claims = ds.claimsTable.forMemberId (member.memberId, Year1)
		  	if (claims.hasNext)
		  		claimsSorted (claims)
		}
	}
	
	def claimsSorted (claims: Iterator[ClaimTuple]) = {
		val claimSolverTheory = new Theory (new java.io.FileInputStream ("claimsolver/claimsolver.pl"))
	  
		println ("Sorting")
		getClaimOrder (claimSolverTheory, claims) foreach println
		println
	}

	def getClaimOrder (claimSolverTheory: Theory, claims: Iterator[ClaimTuple]): Iterator[Int] = {
		var pl = new Prolog
		pl.setTheory (claimSolverTheory)
		loadClaimFacts (pl, claims)
		
		val answer = pl.solve ("claimsSorted(X).")
		var term = answer.getTerm("X").asInstanceOf[Struct]
		var iterator = term.listIterator
		iterator.map (_.asInstanceOf[Int])
	}
	
	def loadClaimFacts (pl: Prolog, claims: Iterator[ClaimTuple]) = {
		val clauseList = claims.foldLeft (new Struct) {(list, claim) =>
		  	val clause1 = new Struct (
		  		"claim",
			    new Int (claim.claimId),
			    new Int (claim.providerId),
			    new Int (claim.dsfs.code),
			    new Struct (claim.specialty.name),
			    new Struct (claim.primaryConditionGroup.name),
			    new Struct (claim.charlsonIndex.name),
			    new Struct (claim.procedureGroup.name)
		  	)
		  	val clause2 = new Struct (
		  		"extended1",
		  		new Int (claim.claimId),
		  		new Int (claim.vendor),
		  		new Int (claim.pcp),
		  		new Struct (claim.year.name),
		  		new Struct (claim.placeSvc.name),
		  		new Int (claim.payDelay.code),
		  		new Struct (claim.lengthOfStay.name)
		  	)
		  	val clause3 = new Struct (
		  		"extended2",
		  		new Int (claim.claimId),
		  		new Int (claim.supLos)
		  	)
			new Struct (".", clause1, new Struct (".", clause2, new Struct (".", clause3, list)))
		}
		
		pl.addTheory (new Theory (clauseList))
	}
}