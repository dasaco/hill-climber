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
			if (point.get(0) + stepsize > this.problem.getMaxValues().get(0)) {
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
			if (point.get(1) + stepsize > this.problem.getMaxValues().get(1)) {
				point.set(1, this.problem.getMaxValues().get(1));
			} else {
				point.set(1, point.get(1) + stepsize);
			}
		}
		
		return point;
	}

	public ArrayList<Double> findOptima(int iterations, int neighboars, double stepsize) {
		ArrayList<ArrayList<Double>> solutions = new ArrayList<>();
		ArrayList<Double> solutionsResult = new ArrayList<>();
		int solutionsCount = 0;
		
		
		for (int itr = 0; itr < iterations; itr++) {
			ArrayList<Double> bestSolutionPoint = this.initPoint(this.problem);
			
			double bestSolutionResult = this.problem.Eval(bestSolutionPoint);			
			boolean shouldContinue;
			
			System.out.println();
			System.out.println("----------------- New Iteration --------------------");
			System.out.println("Starting at x: " + bestSolutionPoint.get(0) + ", y: " + bestSolutionPoint.get(1) + ". And result is: " + bestSolutionResult);
			
			do {
				ArrayList<Double> newSolutionPoint = bestSolutionPoint;
				ArrayList<ArrayList<Double>> neighboarPoints = new ArrayList<ArrayList<Double>>();
				ArrayList<Double> neighboarResults = new ArrayList<>();
				
				newSolutionPoint = alterPointRandomly(newSolutionPoint, stepsize);
				
				for (int n = 0; n < neighboars; n++) {
					ArrayList<Double> neighboarPoint = alterPointRandomly(newSolutionPoint, stepsize);
					neighboarResults.add(this.problem.Eval(neighboarPoint));
					neighboarPoints.add(neighboarPoint);
					solutionsCount++;
				}
				
				System.out.println("Neigboars: " + neighboarResults);
			
				int maxIndex = SimpleHillClimbing.getMaxIndex(neighboarResults);
				
				if (this.problem.Eval(neighboarPoints.get(maxIndex)) > this.problem.Eval(newSolutionPoint)) {
					newSolutionPoint = neighboarPoints.get(maxIndex);
				}
				
				solutionsCount++;
				
				System.out.println("Going with the one with: " + neighboarResults.get(maxIndex));
				
				double newResult = this.problem.Eval(newSolutionPoint);
				
				if (bestSolutionResult <= newResult) {
					System.out.println("Better solution found. Continuing to climb. Rebasing on point: " + newSolutionPoint + ", where result is: " + newResult);
					bestSolutionPoint = newSolutionPoint;
					bestSolutionResult = newResult;
					shouldContinue = true;
				} else {
					System.out.println("Got Stuck! Old result was: " + bestSolutionResult + " and the new is " + newResult + ", didn't get better, so I'm stopping here.");
					System.out.println("Therefore best points are x: " + bestSolutionPoint.get(0) + ", y: " + bestSolutionPoint.get(1) + ". And result is: " + bestSolutionResult);
					shouldContinue = false;
				}
			} while (shouldContinue);

			solutions.add(bestSolutionPoint);
			solutionsResult.add(bestSolutionResult);
		}
		
		int maxIndex = SimpleHillClimbing.getMaxIndex(solutionsResult);
		
		System.out.println();
		System.out.println("<<<<<<<<<<<<<<< Find Optima Function Finished >>>>>>>>>>>>>>>");
		System.out.print("Iterations:" + iterations);
		System.out.print(", Neighboars: " + neighboars);
		System.out.print(", Stepsize: " + stepsize);
		//System.out.println("Total solutions evaluated: " + solutionsCount);
		
		System.out.print(", Solutions: " + solutionsCount);
		System.out.print(" -> Best: " + round(solutionsResult.get(maxIndex), 2));
		
		return solutions.get(maxIndex);
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
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	public static void main(String[] args) {

		Problem problem = new RevAckley();

		SimpleHillClimbing climber = new SimpleHillClimbing(problem);
		
		climber.findOptima(10, 10, 0.1);
//		climber.findOptima(10, 100, 0.1);
//		climber.findOptima(10, 200, 0.1);
//		
//		climber.findOptima(100, 10, 0.1);
//		climber.findOptima(100, 100, 0.1);
//		climber.findOptima(100, 200, 0.1);
//		
//		climber.findOptima(200, 10, 0.1);
//		climber.findOptima(200, 100, 0.1);
//		climber.findOptima(200, 200, 0.1);
//		
//		climber.findOptima(10, 10, 0.01);
//		climber.findOptima(10, 100, 0.01);
//		climber.findOptima(10, 200, 0.01);
//		
//		climber.findOptima(100, 10, 0.01);
//		climber.findOptima(100, 100, 0.01);
//		climber.findOptima(100, 200, 0.01);
//		
//		climber.findOptima(200, 10, 0.01);
//		climber.findOptima(200, 100, 0.01);
//		climber.findOptima(200, 200, 0.01);
	}

}
