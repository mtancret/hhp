/**
 * Based on the "MLP neural network in Java" by Phil Brierley
 * www.philbrierley.com
 */

package nn;

import pattern.Pattern;
import pattern.XorPattern;

public class Nn {
	private int numEpochs = 950; // number of training cycles
	private int numInputs; // number of inputs - this includes the input bias
	private int numHidden = 5; // number of hidden units
	private int numTrainPatterns; // number of training patterns
	private int numValidatePatterns; // number of validation patterns
	private double LR_IH = 0.0001; // learning rate
	private double LR_HO = 0.00001; // learning rate

	// training data
	public static double[][] trainInputs; // double[numPatterns][numInputs];
	public static double[] trainOutputs; // double[numPatterns];
	
	// validate data
	public static double[][] validateInputs; // double[numPatterns][numInputs];
	public static double[] validateOutputs; // double[numPatterns];

	// the outputs of the hidden neurons
	private double[] hiddenVal; // = new double[numHidden];

	// the weights
	private double[][] weightsIH; // = new double[numInputs][numHidden];
	private double[] weightsHO; // = new double[numHidden];
	
	public Nn(Pattern trainPatterns, Pattern validatePatterns) {
		trainInputs = trainPatterns.getInput ();
		trainOutputs = trainPatterns.getOutput ();
		validateInputs = validatePatterns.getInput ();
		validateOutputs = validatePatterns.getOutput ();
		numInputs = trainInputs[0].length;
		numTrainPatterns = trainInputs.length;
		numValidatePatterns = validateInputs.length;
		hiddenVal = new double[numHidden];
		weightsIH = new double[numInputs][numHidden];
		weightsHO = new double[numHidden];
		
		System.out.println("numInputs:"+numInputs+" numTrainPatterns:"+numTrainPatterns+" numValidatePatterns:"+numValidatePatterns);
	}
	
	/* calculate the outputs of the hidden neurons */
	public OutputError calcNet(double[] inputs, double output) {
		OutputError outputError = new OutputError();
		
		// the hidden neurons are tanh
		for (int i=0; i<numHidden; i++) {
			hiddenVal[i] = 0.0;

			for (int j=0; j<numInputs; j++) {
				hiddenVal[i] = hiddenVal[i] + (inputs[j] * weightsIH[j][i]);
			}

			hiddenVal[i] = tanh(hiddenVal[i]);
		}

		// calculate the output of the network
		// the output neuron is linear
		outputError.output = 0.0;

		for (int i = 0; i < numHidden; i++) {
			outputError.output = outputError.output + hiddenVal[i] * weightsHO[i];
		}

		// calculate the error
		outputError.error = outputError.output - output;
		
		return outputError;
	}

	/* adjust the weights hidden-output */
	protected void WeightChangesHO(double error) {
		for (int k = 0; k < numHidden; k++) {
			double weightChange = LR_HO * error * hiddenVal[k];
			weightsHO[k] = weightsHO[k] - weightChange;

			// regularisation on the output weights
			if (weightsHO[k] < -5)
				weightsHO[k] = -5;
			else if (weightsHO[k] > 5)
				weightsHO[k] = 5;
		}
	}

	/* adjust the weights input-hidden */
	protected void WeightChangesIH(double[] inputs, double error) {
		for (int i = 0; i < numHidden; i++) {
			for (int k = 0; k < numInputs; k++) {
				double x = 1 - (hiddenVal[i] * hiddenVal[i]);
				x = x * weightsHO[i] * error * LR_IH;
				x = x * inputs[k];
				double weightChange = x;
				weightsIH[k][i] = weightsIH[k][i] - weightChange;
			}
		}
	}

	protected void initWeights() {
		for (int j = 0; j < numHidden; j++) {
			weightsHO[j] = (Math.random() - 0.5) / 2;
			for (int i = 0; i < numInputs; i++)
				weightsIH[i][j] = (Math.random() - 0.5) / 5;
		}
	}
	
	protected void replaceWeakest() {
		int minHidden = 0;
		double min = weightsHO[0];
		for (int i = 1; i < numHidden; i++) {
			if (weightsHO[i] < min) {
				minHidden = i;
				min = weightsHO[i];
			}
		}
		
		System.out.println ("Replacing: " + minHidden);
		
		for (int i = 0; i < numInputs; i++) {
			weightsIH[i][minHidden] += (Math.random() - 0.5) / 5;
		}
	}

	public double calcValidationError() {
		double RMSerror = 0.0;
		for (int i = 0; i < numValidatePatterns; i++) {
			OutputError output = calcNet(validateInputs[i], validateOutputs[i]);
			RMSerror = RMSerror + (output.error * output.error);
		}
		RMSerror = RMSerror / numValidatePatterns;
		RMSerror = java.lang.Math.sqrt(RMSerror);
		return RMSerror;
	}
	
	public double tanh(double x) {
		if (x > 20)
			return 1;
		else if (x < -20)
			return -1;
		else {
			double a = Math.exp(x);
			double b = Math.exp(-x);
			return (a - b) / (a + b);
		}
	}
	
	public void computeEntry (Pattern entryPattern) {
		double[][] entryInput = entryPattern.getInput();
		double[] entryOutput = entryPattern.getOutput();
		
		for (int i=0; i<entryInput.length; i++) {
			OutputError output = calcNet(entryInput[i], 0);
			entryOutput[i] = Math.pow(Math.E, output.output) - 1.0; // 0.209179;
			if (entryOutput[i] < 0.0) {
				entryOutput[i] = 0.0;
			} else if (entryOutput[i] > 15.0) {
				entryOutput[i] = 15.0;
			}
		}
	}
	
	public void displayResults() {
		for (int i = 0; i < 50/*numValidatePatterns*/; i++) {
			OutputError output = calcNet(validateInputs[i], validateOutputs[i]);
			System.out.printf("pattern = %d actual = %f predicted = %f error = %f\n", 
				(i + 1), validateOutputs[i], output.output, output.error);
		}
	}
	
	public void printNet() {
		for (int i = 0; i < numHidden; i++) {
			System.out.printf("hidden%d is %f", i, weightsHO[i]);
			System.out.printf(" with IH: ");
			for (int k = 0; k < numInputs; k++) {
				System.out.printf(" %f,", weightsIH[k][i]);
			}
			System.out.println();
		}
	}
	
	public void run() {
		// initiate the weights
		initWeights();

		// train the network
		for (int j = 0; j <= numEpochs; j++) {

			for (int i = 0; i < numTrainPatterns; i++) {

				// select a pattern at random
				int patNum = (int) ((Math.random() * numTrainPatterns) - 0.001);

				// calculate the current network output
				// and error for this pattern
				OutputError output = calcNet(trainInputs[patNum], trainOutputs[patNum]);

				// change network weights
				WeightChangesHO(output.error);
				WeightChangesIH(trainInputs[patNum], output.error);
			}

			// display the overall network error
			// after each epoch
			double RMSerror = calcValidationError();
			//System.out.println("epoch = " + j + "  RMS Error = " + RMSerror);
			System.out.println(RMSerror);
		}
		
		// training has finished
		// display the results
		//displayResults();
		//printNet();
	}
	
	public static void main(String[] args) {
		Pattern pattern = new XorPattern();
		Nn nn = new Nn(pattern, pattern);
		nn.run();
	}
}
