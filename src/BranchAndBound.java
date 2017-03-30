
public class BranchAndBound {

	public static void main(String[] args) {
		
		System.err.close(); //Don't print warnings that the SCPSolver pushes to std.error  
		checkArgs(args); 
		int demoProgram = getDemoProgram(args);
		new BnB_Guide(demoProgram);
		
	}
	
	private static void checkArgs(String[] args) {
		if (args.length != 1){
			System.out.println("Not enough arguments \nArgunments:  [1-3], each Integer in range represents a demo problem ");
			System.exit(1);
		}	
	}
	
	private static int getDemoProgram(String[] args) {
		int toReturn = -1;
		try {
			toReturn = Integer.parseInt(args[0]);
		}catch(NumberFormatException e){
			System.err.println("Argument must be an integer between [1,3]");
			System.exit(1);
		}
		if(toReturn > 3 || toReturn < 1 ){
			System.err.println("Argument must be an integer between [1,3]");
			System.exit(1);
		}
		return toReturn;

	}
}
