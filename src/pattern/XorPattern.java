package pattern;

public class XorPattern extends Pattern {
	
	public XorPattern () {
		super (4, 3);
		
		input[0][0] = 1;
		input[0][1] = -1;
		input[0][2] = 1; // bias

		input[1][0] = -1;
		input[1][1] = 1;
		input[1][2] = 1;

		input[2][0] = 1;
		input[2][1] = 1;
		input[2][2] = 1;

		input[3][0] = -1;
		input[3][1] = -1;
		input[3][2] = 1;
		
		output[0] = 1;
		output[1] = 1;
		output[2] = -1;
		output[3] = -1;
	}
}
