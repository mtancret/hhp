package file
import datatypes.Year
import datatypes.DataStore
import datatypes.DaysInHospitalTable
import datatypes.Year._
import datatypes.DaysInHospitalTable
import datatypes.DaysInHospitalTable

object FileLoader {
	def loadInOutYears (inYear: Year, outYear: Year): DataStore = {
		val claimsTable = CsvParser.parseClaimsTable ("data/HHP_release3/Claims.csv", Some (inYear))
		val drugCountTable = CsvParser.parseDrugCountTable ("data/HHP_release3/DrugCount.csv", Some (inYear))
		val labCountTable = CsvParser.parseLabCountTable ("data/HHP_release3/LabCount.csv", Some (inYear))
		val membersTable = CsvParser.parseMembersTable ("data/HHP_release3/Members.csv")
		
		var daysInHospitalTable = outYear match {
			case Year2 =>
				CsvParser.parseDaysInHospitalTable (
					"data/HHP_release3/DaysInHospital_Y2.csv", Year2
				)
			case Year3 =>
				CsvParser.parseDaysInHospitalTable (
					"data/HHP_release3/DaysInHospital_Y3.csv", Year3
				)
			case Year4 =>
				CsvParser.parseDaysInHospitalTable (
					"data/HHP_release3/Target.csv", Year4
				)
			case _ =>
				System.err.println ("WARNING: Invalid out year passed to loadInOutYears.")
				new DaysInHospitalTable
		}
		
		DataStore (claimsTable,
			daysInHospitalTable,
			drugCountTable,
			labCountTable,
			membersTable)
	}
	
	def loadMonthInOutYears (inYear: Year, outYear: Year): DataStore = {
		val claimsTable = CsvParser.parseClaimsTableMonth ("data/ClaimDate/Claims.csv", Some (inYear))
		val drugCountTable = CsvParser.parseDrugCountTable ("data/HHP_release3/DrugCount.csv", Some (inYear))
		val labCountTable = CsvParser.parseLabCountTable ("data/HHP_release3/LabCount.csv", Some (inYear))
		val membersTable = CsvParser.parseMembersTable ("data/HHP_release3/Members.csv")
		
		var daysInHospitalTable = outYear match {
			case Year2 =>
				CsvParser.parseDaysInHospitalTable (
					"data/HHP_release3/DaysInHospital_Y2.csv", Year2
				)
			case Year3 =>
				CsvParser.parseDaysInHospitalTable (
					"data/HHP_release3/DaysInHospital_Y3.csv", Year3
				)
			case Year4 =>
				CsvParser.parseDaysInHospitalTable (
					"data/HHP_release3/Target.csv", Year4
				)
			case _ =>
				System.err.println ("WARNING: Invalid out year passed to loadInOutYears.")
				new DaysInHospitalTable
		}
		
		DataStore (claimsTable,
			daysInHospitalTable,
			drugCountTable,
			labCountTable,
			membersTable)
	}
	
	def loadAllUnsanitized: DataStore = {
		loadAllWithMembersFile ("data/HHP_release3/Members.csv")
	}
	
	def loadAll: DataStore = {
		//loadAllWithMembersFile ("data/Sanitized/Members.csv")
	  loadAllWithMembersFile ("data/HHP_release3/Members.csv")
	}
	
	def loadAllWithMembersFile (membersFileName: String) = {
		val claimsTable = CsvParser.parseClaimsTable ("data/HHP_release3/Claims.csv", None)
		val drugCountTable = CsvParser.parseDrugCountTable ("data/HHP_release3/DrugCount.csv", None)
		val labCountTable = CsvParser.parseLabCountTable ("data/HHP_release3/LabCount.csv", None)
		val membersTable = CsvParser.parseMembersTable (membersFileName)
		
		var daysInHospitalTable = new DaysInHospitalTable
		daysInHospitalTable ++= CsvParser.parseDaysInHospitalTable (
			"data/HHP_release3/DaysInHospital_Y2.csv", Year2)
		daysInHospitalTable ++= CsvParser.parseDaysInHospitalTable (
		    "data/HHP_release3/DaysInHospital_Y3.csv", Year3)
		daysInHospitalTable ++= CsvParser.parseDaysInHospitalTable (
			"data/HHP_release3/Target.csv", Year4)
		
		DataStore (claimsTable,
			daysInHospitalTable,
			drugCountTable,
			labCountTable,
			membersTable)
	}
	
	def loadAllClaimDate = {
		val claimsTable = CsvParser.parseClaimsTableMonth ("data/ClaimDate/Claims.csv", None)
		val drugCountTable = CsvParser.parseDrugCountTable ("data/HHP_release3/DrugCount.csv", None)
		val labCountTable = CsvParser.parseLabCountTable ("data/HHP_release3/LabCount.csv", None)
		val membersTable = CsvParser.parseMembersTable ("data/HHP_release3/Members.csv")
		
		var daysInHospitalTable = new DaysInHospitalTable
		daysInHospitalTable ++= CsvParser.parseDaysInHospitalTable (
			"data/HHP_release3/DaysInHospital_Y2.csv", Year2)
		daysInHospitalTable ++= CsvParser.parseDaysInHospitalTable (
		    "data/HHP_release3/DaysInHospital_Y3.csv", Year3)
		daysInHospitalTable ++= CsvParser.parseDaysInHospitalTable (
			"data/HHP_release3/Target.csv", Year4)
		
		DataStore (claimsTable,
			daysInHospitalTable,
			drugCountTable,
			labCountTable,
			membersTable)
	}
}