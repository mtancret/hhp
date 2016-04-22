package file
import scala.io.Source
import datatypes.DrugCountTable
import datatypes.Year
import datatypes.Dsfs
import datatypes.DrugCount
import datatypes.ClaimsTable
import datatypes.ClaimTuple
import datatypes.Specialty
import datatypes.PlaceSvc
import datatypes.PayDelay
import datatypes.LengthOfStay
import datatypes.PrimaryConditionGroup
import datatypes.CharlsonIndex
import datatypes.ProcedureGroup
import datatypes.DaysInHospitalTable
import datatypes.DaysInHospitalTuple
import datatypes.DrugCountTuple
import datatypes.LabCountTable
import datatypes.LabCountTuple
import datatypes.LabCount
import datatypes.MembersTable
import datatypes.MemberTuple
import datatypes.Sex
import datatypes.AgeAtFirstClaim
import datatypes.Month._
import datatypes.Month

object CsvParser {
	def parseClaimsTable (fileName: String, year: Option[Year]): ClaimsTable = {
		var table = new ClaimsTable
		val rowIterator = scanFile (fileName)
		rowIterator.zipWithIndex.foreach {
			case (row, claimId) => row match {
		    		case "MemberID" :: _ =>
		    		case memberIdStr :: providerIdStr :: vendorStr :: pcpStr :: yearStr ::
		    				specialtyStr :: placeSvcStr :: payDelayStr :: lengthOfStayStr ::
		    				dsfsStr :: primaryConditionStr :: charlsonIndexStr ::
		    				procedureGroupStr :: supLosStr :: Nil
		    				if (year == None || yearStr == year.get.text) =>
		    			table += ClaimTuple (claimId*4 + Year.forString (yearStr).code,
		        			memberIdStr.toInt,
			    	        if (providerIdStr == "") -1 else providerIdStr.toInt,
			    	        if (vendorStr == "") -1 else vendorStr.toInt,
			    	        if (pcpStr == "") -1 else pcpStr.toInt,
			    	        Year.forString (yearStr),
			    	        Specialty.forString (specialtyStr),
			    	        PlaceSvc.forString (placeSvcStr),
			    	        PayDelay.forString (payDelayStr),
			    	        LengthOfStay.forString (lengthOfStayStr),
			    	        Dsfs.forString (dsfsStr),
			    	        UnknownMonth,
			    	        PrimaryConditionGroup.forString (primaryConditionStr),
			    	        CharlsonIndex.forString (charlsonIndexStr),
			    	        ProcedureGroup.forString (procedureGroupStr),
			    	        supLosStr.toInt)
		    			case _ =>
		    	}
		}
		table
	}
	
	def parseClaimsTableMonth (fileName: String, year: Option[Year]): ClaimsTable = {
		var table = new ClaimsTable
		val rowIterator = scanFile (fileName)
		rowIterator.zipWithIndex.foreach {
			case (row, claimId) => row match {
		    		case "MemberID" :: _ =>
		    		case memberIdStr :: providerIdStr :: vendorStr :: pcpStr :: yearStr ::
		    				specialtyStr :: placeSvcStr :: payDelayStr :: lengthOfStayStr ::
		    				dsfsStr :: monthStr :: primaryConditionStr ::
		    				charlsonIndexStr :: procedureGroupStr :: supLosStr :: Nil
		    				if (year == None || yearStr == year.get.text) =>
		    			table += ClaimTuple (claimId*4 + Year.forString (yearStr).code,
		        			memberIdStr.toInt,
			    	        if (providerIdStr == "") -1 else providerIdStr.toInt,
			    	        if (vendorStr == "") -1 else vendorStr.toInt,
			    	        if (pcpStr == "") -1 else pcpStr.toInt,
			    	        Year.forString (yearStr),
			    	        Specialty.forString (specialtyStr),
			    	        PlaceSvc.forString (placeSvcStr),
			    	        PayDelay.forString (payDelayStr),
			    	        LengthOfStay.forString (lengthOfStayStr),
			    	        Dsfs.forString (dsfsStr),
			    	        Month.forString (monthStr),
			    	        PrimaryConditionGroup.forString (primaryConditionStr),
			    	        CharlsonIndex.forString (charlsonIndexStr),
			    	        ProcedureGroup.forString (procedureGroupStr),
			    	        supLosStr.toInt)
		    			case _ =>
		    	}
		}
		table
	}
	
	def parseDaysInHospitalTable (fileName: String, year: Year): DaysInHospitalTable = {
		var table = new DaysInHospitalTable
		val rowIterator = scanFile (fileName)
		rowIterator foreach (
		    row => row match {
		      case "MemberID" :: _ =>
		      case memberIdStr :: claimsTruncatedStr :: daysInHospitalStr :: Nil =>
		        table += DaysInHospitalTuple (memberIdStr.toInt,
		        		     claimsTruncatedStr.toInt,
		        		     if (daysInHospitalStr == "") 0.0 else daysInHospitalStr.toInt,
		        		     year)
		      case _ =>
		    }
		)
		table
	}
	
	def parseDrugCountTable (fileName: String, year: Option[Year]): DrugCountTable = {
		var table = new DrugCountTable
		val rowIterator = scanFile (fileName)
		rowIterator foreach (
		    row => row match {
		      case "MemberID" :: _ =>
		      case memberIdStr :: yearStr :: dsfsStr :: drugCountStr :: Nil
		      		if (year == None || yearStr == year.get.text) =>
		        table += DrugCountTuple (memberIdStr.toInt,
			    	        Year.forString (yearStr),
			    	        Dsfs.forString (dsfsStr),
			    	        DrugCount.forString (drugCountStr))
		      case _ =>
		    }
		)
		table
	}
	
	def parseLabCountTable (fileName: String, year: Option[Year]): LabCountTable = {
		var table = new LabCountTable
		val rowIterator = scanFile (fileName)
		rowIterator foreach (
		    row => row match {
		      case "MemberID" :: _ =>
		      case memberIdStr :: yearStr :: dsfsStr :: labCountStr :: Nil
		      		if (year == None || yearStr == year.get.text) =>
		        table += LabCountTuple (memberIdStr.toInt,
			    	        Year.forString (yearStr),
			    	        Dsfs.forString (dsfsStr),
			    	        LabCount.forString (labCountStr))
		      case _ =>
		    }
		)
		table
	}
	
	def parseMembersTable (fileName: String): MembersTable = {
		var table = new MembersTable
		val rowIterator = scanFile (fileName)
		rowIterator foreach (
		    row => row match {
		      case "MemberID" :: _ =>
		      case memberIdStr :: ageAtFirstClaimStr :: sexStr :: Nil =>
			    table += MemberTuple (memberIdStr.toInt,
			    	        AgeAtFirstClaim.forString (ageAtFirstClaimStr),
			    	        Sex.forString (sexStr))
		      case _ =>
		        print ("Did not parse member: ")
		        println (row)
		    }
		)
		table
	}
  
	def scanFile (fileName: String): Iterator[List[String]] = {
		val file = Source.fromFile (fileName)
		val lines = file.getLines()
		lines.map (scanLine)
	}
	
	def scanLine (line: String): List[String] = {
		line.split (",", -1).toList
	}
}