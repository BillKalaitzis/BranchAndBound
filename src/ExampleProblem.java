import scpsolver.problems.*;

public class ExampleProblem {
	
	LPWizard lp = new LPWizard();
	
	public ExampleProblem(int i){
		
		lp = setProblem(i);
		
	}	
	
	public LPWizard getProblem(){
		 		return lp;
	}
	
	public LPWizard setProblem(int i){
		LPWizard lp = new LPWizard();
		
		if( i == 1 ){ //Problem obtained from the 3rd lecture of Combinatorial Optimization
			lp.setMinProblem(true); //Minimize
			lp.plus("x1",1.0).plus("x2",4.0);
			lp.addConstraint("c1",46,"<=").plus("x1",2.0).plus("x2",5.0); 
			lp.addConstraint("c2",19,">=").plus("x1",3.0);
			lp.addConstraint("c3",0,"<=").plus("x1",1.0);
			lp.addConstraint("c4",0,"<=").plus("x2",1.0);
		}
		else if( i == 2 ){ //Problem obtained from the 3rd lecture of Combinatorial Optimization
			lp.setMinProblem(false); //Maximize
			lp.plus("x1",6.0).plus("x2",8.0);
			lp.addConstraint("c1",36,">=").plus("x1",4.0).plus("x2",6.0); 
			lp.addConstraint("c2",70,">=").plus("x1",10.0).plus("x2",7.0);
			lp.addConstraint("c3",0,"<=").plus("x1",1.0);
			lp.addConstraint("c4",0,"<=").plus("x2",1.0);
		}
		else if ( i == 3 ){
			lp.setMinProblem(false); //Maximize
			lp.plus("x1",3.0).plus("x2",5.0);
			lp.addConstraint("c1",25,">=").plus("x1",2.0).plus("x2",4.0); 
			lp.addConstraint("c2",8,">=").plus("x1",1.0);
			lp.addConstraint("c3",10,">=").plus("x2",2.0);
			lp.addConstraint("c4",0,"<=").plus("x1",1.0);
			lp.addConstraint("c5",0,"<=").plus("x2",1.0);
		}
		return lp;
	}
	
}
