import java.text.DecimalFormat;

import scpsolver.constraints.*;
import scpsolver.lpsolver.*;
import scpsolver.problems.*;

public class BnB_Guide {
	
	private ExampleProblem example;
	private LinearProgramSolver solver;
	private int searchDepth = 0; //Depth of the search tree
	private boolean solutionFound = false;
	private double MinimizeProblemOptimalSolution = Double.MAX_VALUE; //The optimal objective value of the minimization problem
	private double MaximizeProblemOptimalSolution = Double.MIN_VALUE; //The optimal objective value of the maximization problem
	private LPSolution OptimalSolution;
	private double[] OptimalSolutionDecisionVariables; //The values of the decision variables 
	
	public BnB_Guide(int demoProblem){
		
		example = new ExampleProblem(demoProblem);
		LinearProgram lp = new LinearProgram();
		lp = example.getProblem().getLP();
		solver = SolverFactory.newDefault();
		
		double[] solution = solver.solve(lp); // Solution of the initial relaxation problem
		int maxElement =  getMax(solution); // Index of the maximum non-integer decision variable's value
		if(maxElement == -1 ) // We only got integers as values, hence we have an optimal solution
			verifyOptimalSolution(solution,lp);
		else
			this.solveChildProblems(lp, solution, maxElement); // create 2 child problems and solve them
		
		printSolution();
		
	}

	public void solveChildProblems(LinearProgram lp, double[] solution ,int maxElement){

		searchDepth++;
		
		LinearProgram lp1 = new LinearProgram(lp);
		LinearProgram lp2 = new LinearProgram(lp);
		
		String constr_name = "c" + (lp.getConstraints().size() + 1); // Name of the new constraint 
		double[] constr_val = new double[lp.getDimension()]; // The variables' values of the new constraint 
		
		for(int i=0;i<constr_val.length;i++){ // Populate the table
			if(i == maxElement )
				constr_val[i] = 1.0;
			else
				constr_val[i] = 0;
		}	
		
		//Create 2 child problems: 1. x >= ceil(value), 2. x <= floor(value)
		lp1.addConstraint(new LinearBiggerThanEqualsConstraint(constr_val, Math.ceil(solution[maxElement]), constr_name));
		lp2.addConstraint(new LinearSmallerThanEqualsConstraint(constr_val, Math.floor(solution[maxElement]), constr_name));
		
		solveProblem(lp1);
		solveProblem(lp2);
		
	}
		
	private void solveProblem(LinearProgram lp) {
		
		double[] sol = solver.solve(lp);
		int maxElement = this.getMax(sol);
		if(maxElement == -1 && lp.isFeasable(sol)){ //We found a solution
			solutionFound = true;
			verifyOptimalSolution(sol,lp);
		}
		else if(lp.isFeasable(sol) && !solutionFound) //Search for a solution in the child problems
			this.solveChildProblems(lp, sol, maxElement);
		
	}

	private void verifyOptimalSolution(double[] sol, LinearProgram lp) {
		
		LPSolution potentialOptimalSolution = new LPSolution(sol,lp);
		if(lp.isMinProblem() && potentialOptimalSolution.getObjectiveValue() < MinimizeProblemOptimalSolution ){ //If the current solution returns a greater objective value then make it optimal
			MinimizeProblemOptimalSolution = potentialOptimalSolution.getObjectiveValue();
			OptimalSolution = potentialOptimalSolution;
			OptimalSolutionDecisionVariables = sol;
		}
		else if(!lp.isMinProblem() && potentialOptimalSolution.getObjectiveValue() > MaximizeProblemOptimalSolution ){ //If the current solution returns a greater objective value then make it optimal
			MaximizeProblemOptimalSolution = potentialOptimalSolution.getObjectiveValue();
			OptimalSolution = potentialOptimalSolution;
			OptimalSolutionDecisionVariables = sol;
		}
		
	}

	private void printSolution(){
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		System.out.println("Solution found after " + searchDepth + " branch-and-bound iterations");
		System.out.println("Objective Value = " + df.format(OptimalSolution.getObjectiveValue()) );
		System.out.print("Decision Variables values: ");
		for(double d: OptimalSolutionDecisionVariables)
			 System.out.print( df.format(d) + " ");
		System.out.println();
		
	}

	public int getMax(double[] array){ //Returns the index of the maximum fractional part between all the decision variables
		
		double max = -1;
		int index = -1;
		for (int i = 0; i < array.length; i++){
		    if ((array[i] % 1) > max && Math.round(array[i]*10000.0)/10000.0 != (int) array[i] ){ // array[i] % 1 : returns the fractional part of the number
		      max = array[i];																    // Math.round(array[i]*10000.0)/10000.0 != (int) array[i] : ensures that the number isn't integer
		      index = i;
		    }
		}
		return index;
		    
	}
	
}
