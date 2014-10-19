package edu.mioib.qaplocalsearch.algorithm;

import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptNeighboursIterator;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

public class GreedyAlgorithm implements Algorithm {

	@Override
	public Solution resolveProblem(Problem problem, Evaluator evaluator, int[] startState) {
		int[] currentState = startState;
		int currentEvaluation = evaluator.evaluateState(problem, currentState);
		boolean currentStateChanged = false;
		
		TwoOptNeighboursIterator neighbourIterator;
		do{
			neighbourIterator = new TwoOptNeighboursIterator(currentState);
			currentStateChanged = false;
			while(neighbourIterator.hasNext() && !currentStateChanged){
				int[] newState = neighbourIterator.next();
				int newStateEvaluation = evaluator.evaluateState(problem, newState);
				if(newStateEvaluation > currentEvaluation){
					currentState = newState;
					currentEvaluation = newStateEvaluation;
					currentStateChanged = true;
				}
			}
		} while(currentStateChanged);
		
		return new Solution(currentEvaluation, currentState);
	}
}
