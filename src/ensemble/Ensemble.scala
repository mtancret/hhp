package ensemble

import file.FileLoader
import datatypes.Year._
import pattern.Pattern
import pattern.TargetPatternLoader
import nn.Nn
import features.Feature
import file.TargetWriter
import features.CommonFeatures
import features.RealFeature

object Ensemble {
  
	def go = {
		println ("Creating features...")
		val features = CommonFeatures.features
		
		println ("Loading patterns...")
		val ds = FileLoader.loadAll
		val (trainPatterns, validatePatterns) =
		  	TargetPatternLoader.loadInOutYearsSplit (ds, features, Year2, Year3)
		
		println ("Training...")
		var nn = new Nn (trainPatterns, validatePatterns)
		nn.run
		
		println ("Loading target patterns...")
		val targetPatterns =
		  	TargetPatternLoader.loadInOutYears (ds, features, Year3, Year4)
		nn.computeEntry (targetPatterns)
		
		println ("Writing target file...")
		TargetWriter.writeTarget (targetPatterns, "entry/2012-11-14.csv")
	}
}

/*
	def go = {
		println ("Creating features...")
		val features = CommonFeatures.features
		
		println ("Loading training patterns...")
		val trainPatterns = loadTrainPatterns (features)
		
		println ("Loading validation patterns...")
		val validatePatterns = loadValidatePatterns (features)
		
		println ("Training...")
		var nn = new Nn (trainPatterns, validatePatterns)
		nn.run
		
		println ("Loading target patterns...")
		val targetPatterns = loadTargetPatterns (features)
		nn.computeEntry (targetPatterns)
		
		println ("Writing target file...")
		TargetWriter.writeTarget (targetPatterns, "entry/2012-11-12.csv")
	}
	
	def loadTrainPatterns (features: List[RealFeature]): Pattern = {
		val trainData = FileLoader.loadInOutYears (Year2, Year3)
		TargetPatternLoader.loadInOutYears (trainData, features, Year2, Year3)
	}
	
	def loadValidatePatterns (features: List[RealFeature]): Pattern = {
		val validateData = FileLoader.loadInOutYears (Year1, Year2)
		TargetPatternLoader.loadInOutYears (validateData, features, Year1, Year2)
	}
	
	def loadTargetPatterns (features: List[RealFeature]): Pattern = {
		val targetData = FileLoader.loadInOutYears (Year3, Year4)
		TargetPatternLoader.loadInOutYears (targetData, features, Year3, Year4)
	}
*/

/*
import java.util.Random;

import pattern2.AgeSexPatternTable;
import pattern2.BigPatternTable;
import pattern2.PatternTable;
import datatypes2.ClaimsTable;
import datatypes2.DaysInHospital;
import datatypes2.DaysInHospitalTable;
import datatypes2.Entry;
import datatypes2.EntryTable;
import datatypes2.MembersTable;
import datatypes2.Table;
import file2.CsvParser;
import nn2.Nn;
import nn2.OutputError;

public class Ensemble {
	public static Random rand = new Random(11);
	
	public static void splitTable(Table tableIn, Table tableOut1, Table tableOut2, double split) {
		int tableSize = tableIn.size();
		int tableOut1Goal = (int) (tableSize * split);
		//int tableOut2Goal = tableSize - tableOut1Goal;
		
		for (int i=0; i<tableSize; i++) {
			if (rand.nextDouble() < split && tableOut1.size() < tableOut1Goal) {
				tableOut1.add(tableIn.get(i));
			} else {
				tableOut2.add(tableIn.get(i));
			}
		}
	}
	
	public static void main(String args[]) {
		CsvParser parser = new CsvParser();
		MembersTable membersTable = parser.parseMembersFile("data/HHP_release3/Members.csv");
		DaysInHospitalTable daysInHospitalTable = new DaysInHospitalTable();
		parser.parseDaysInHospitalFile("data/HHP_release3/DaysInHospital_Y2.csv", daysInHospitalTable);
		parser.parseDaysInHospitalFile("data/HHP_release3/DaysInHospital_Y3.csv", daysInHospitalTable);
		DaysInHospitalTable trainDaysInHospitalTable = new DaysInHospitalTable();
		DaysInHospitalTable validateDaysInHospitalTable = new DaysInHospitalTable();
		splitTable(daysInHospitalTable, trainDaysInHospitalTable, validateDaysInHospitalTable, 0.7);
		ClaimsTable claimsTable = parser.parseClaimsFile("data/HHP_release3/Claims.csv");
		EntryTable entryTable = parser.parseTargetFile("data/HHP_release3/Target.csv");
		
		//PatternTable trainTable = new AgeSexPatternTable<DaysInHospital>(membersTable, trainDaysInHospitalTable);
		//PatternTable validateTable = new AgeSexPatternTable<DaysInHospital>(membersTable, validateDaysInHospitalTable);
		PatternTable trainTable = new BigPatternTable<DaysInHospital>(membersTable, trainDaysInHospitalTable, claimsTable);
		PatternTable validateTable = new BigPatternTable<DaysInHospital>(membersTable, validateDaysInHospitalTable, claimsTable);
		Nn nn = new Nn(trainTable, validateTable);
		nn.run();
		
		//PatternTable entryPatternTable = new AgeSexPatternTable<Entry>(membersTable, entryTable);
		PatternTable entryPatternTable = new BigPatternTable<Entry>(membersTable, entryTable, claimsTable);
		double[][] entryPatterns = entryPatternTable.getPatterns();
		for (int i=0; i<entryPatterns.length; i++) {
			OutputError output = nn.calcNet(entryPatterns[i], 0);
			Entry entry = entryTable.get(i);
			entry.days = Math.pow(Math.E, output.output) - 1.0; // 0.209179;
			if (entry.days < 0.0) {
				entry.days = 0.0;
			} else if (entry.days > 15.0) {
				entry.days = 15.0;
			}
		}
		
		parser.genEntry("entry/2011-11-12.csv", entryTable);
	}
}
*/