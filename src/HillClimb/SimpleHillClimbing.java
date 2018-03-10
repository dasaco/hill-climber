package HillClimb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleHillClimbing {

	private Problem problem;
	private final Random random = new Random();

	public SimpleHillClimbing(Problem problem) {
		this.problem = problem;
	}
	
	private ArrayList<Double> initPoint(Problem problem) {
		ArrayList<Double> initPoint = new ArrayList<>();
		for (int dim = 0; dim < problem.getDimensions(); dim++) {
			initPoint.add(problem.getMinValues().get(dim) + Math.random()
			* (problem.getMaxValues().get(dim) - problem.getMinValues().get(dim)));
		}
		return initPoint;
	}
	
	public ArrayList<Double> alterPointRandomly(ArrayList<Double> point, double stepsize) {
		if (random.nextBoolean()) {
			if (point.get(0) - stepsize < this.problem.getMinValues().get(0)) {
				point.set(0, this.problem.getMinValues().get(0));
			} else {
				point.set(0, point.get(0) - stepsize);
			}
		} else {
			if (point.get(0) + stepsize < this.problem.getMaxValues().get(0)) {
				point.set(0, this.problem.getMaxValues().get(0));
			} else {
				point.set(0, point.get(0) + stepsize);
			}
		}
		
		if (random.nextBoolean()) {
			if (point.get(1) - stepsize < this.problem.getMinValues().get(1)) {
				point.set(1, this.problem.getMinValues().get(1));
			} else {
				point.set(1, point.get(1) - stepsize);
			}
		} else {
			if (point.get(1) + stepsize < this.problem.getMaxValues().get(1)) {
				point.set(1, this.problem.getMaxValues().get(1));
			} else {
				point.set(1, point.get(1) + stepsize);
			}
		}
		
		return point;
	}

	public ArrayList<Double> findOptima(int iterations, int neighboars, double stepsize) {
		ArrayList<Double> solutions = new ArrayList<>();
		
		for (int itr = 0; itr < iterations; itr++) {
			ArrayList<Double> bestSolutionPoint = this.initPoint(this.problem);
			
			double bestSolutionResult = this.problem.Eval(bestSolutionPoint);			
			boolean shouldContinue;
			
			System.out.println("-------------------------------------");
			System.out.println("Starting at x: " + bestSolutionPoint.get(0) + ", y: " + bestSolutionPoint.get(1) + ". And result is: " + bestSolutionResult);
			
			do {
				ArrayList<Double> newSolutionPoint = bestSolutionPoint;
				ArrayList<ArrayList<Double>> neighboarPoints = new ArrayList<ArrayList<Double>>();
				ArrayList<Double> neighboarResults = new ArrayList<>();
				
				for (int n = 0; n < neighboars; n++) {
					ArrayList<Double> neighboarPoint = alterPointRandomly(newSolutionPoint, stepsize);
					neighboarResults.add(this.problem.Eval(neighboarPoint));
					neighboarPoints.add(neighboarPoint);
				}
			
				int maxIndex = SimpleHillClimbing.getMaxIndex(neighboarResults);
				newSolutionPoint = neighboarPoints.get(maxIndex);
				
				double newResult = this.problem.Eval(newSolutionPoint);
				
				if (bestSolutionResult < newResult) {
					System.out.println("Better solution found. Continuing to climb.");
					bestSolutionResult = newResult;
					bestSolutionResult = this.problem.Eval(newSolutionPoint);
					shouldContinue = true;
				} else {
					System.out.println("Got Stuck!");
					System.out.println("Best points are x: " + bestSolutionPoint.get(0) + ", y: " + bestSolutionPoint.get(1) + ". And result is: " + bestSolutionResult);
					shouldContinue = false;
				}
			} while (shouldContinue);

			solutions.addAll(bestSolutionPoint);
		}
		
		return solutions;
	}
	
	public static int getMaxIndex(ArrayList<Double> inputArray){ 
	    double maxValue = inputArray.get(0);
	    int maxIndex = 0;
	    for(int i = 0; i < inputArray.size();i++){ 
	      if(inputArray.get(i) > maxValue){ 
	         maxValue = inputArray.get(i);
	         maxIndex = i;
	      } 
	    } 
	    return maxIndex; 
	  }

	public static void main(String[] args) {

		Problem problem = new P1();

		SimpleHillClimbing climber = new SimpleHillClimbing(problem);
		
		System.out.println(climber.findOptima(400, 100, 0.01));
	}

}
