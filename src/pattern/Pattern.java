package pattern;

public class Pattern {
	protected int[] id;
	protected double[][] input;
	protected double[] output;
	protected int numPatterns;
	protected int numFeatures;
	
	public Pattern (int numPatterns, int numFeatures) {
		this.numPatterns = numPatterns;
		this.numFeatures = numFeatures;
		input = new double[numPatterns][numFeatures];
		output = new double[numPatterns];
		id = new int[numPatterns];
	}
	
	public void setInput (int pattern, int feature, double value) {
		input[pattern][feature] = value;
	}
	
	public void setOutput (int pattern, double value, int id) {
		output[pattern] = value;
		this.id[pattern] = id;
	}
	
	public double[][] getInput () {
		return input;
	}
	
	public double[] getOutput () {
		return output;
	}
	
	public double getOutput (int pattern) {
		return output[pattern];
	}
	
	public int getId (int pattern) {
		return id[pattern];
	}
	
	public int getNumPatterns () {
		return numPatterns;
	}
}