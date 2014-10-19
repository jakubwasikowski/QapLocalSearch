package edu.mioib.qaplocalsearch.algorithm;

import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptNeighboursIterator;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

public class SteepestAlgorithm implements Algorithm {

	@Override
	public Solution resolveProblem(Problem problem, int[] startState) {
		int[] currentState = startState;
		int currentEvaluation = evaluateState(problem,currentState);
		boolean currentStateChanged = false;
		
		TwoOptNeighboursIterator neighbourIterator;
		do{
			neighbourIterator = new TwoOptNeighboursIterator(currentState);
			currentStateChanged = false;
			while(neighbourIterator.hasNext()){
				int[] newState = neighbourIterator.next();
				int newStateEvaluation = evaluateState(problem,newState);
				if(newStateEvaluation > currentEvaluation){
					currentState = newState;
					currentEvaluation = newStateEvaluation;
					currentStateChanged = true;
				}
			}
		} while(currentStateChanged);
		
		return new Solution(currentEvaluation, currentState);
	}
	
	private int evaluateState(Problem problem, int[] startState){
		int result = 0;
		int[][] localisations = problem.getLocalisations();
		int[][] facilities = problem.getFacilities();
		
		for(int i=0; i<startState.length-1; i++){
			result += localisations[startState[i]][startState[i+1]] * facilities[startState[i]][startState[i+1]];
		}
		
		return result;
	}

}
