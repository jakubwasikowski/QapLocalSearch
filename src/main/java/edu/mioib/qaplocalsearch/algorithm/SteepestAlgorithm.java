package edu.mioib.qaplocalsearch.algorithm;

import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptNeighboursIterator;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

public class SteepestAlgorithm implements Algorithm {

	@Override
	public Solution resolveProblem(Problem problem, Evaluator evaluator, int[] currentState) {
		int currentEvaluation = evaluator.evaluateState(problem, currentState);
		boolean currentStateChanged;
		
		TwoOptNeighboursIterator neighbourIterator;
		do{
			neighbourIterator = new TwoOptNeighboursIterator(currentState);
			currentStateChanged = false;
			while (neighbourIterator.hasNext()) {
				neighbourIterator.next();
				int newStateEvaluation = evaluator.evaluateState(problem, currentState);
				if (newStateEvaluation < currentEvaluation) {
					neighbourIterator.saveCurrentNeighbourAsTheBest();
					currentEvaluation = newStateEvaluation;
					currentStateChanged = true;
				}
			}
			if (!currentStateChanged) {
				neighbourIterator.switchToOriginalState();
			} else {
				neighbourIterator.switchToTheBestNeighbour();
			}
		} while(currentStateChanged);
		
		return new Solution(currentEvaluation, currentState);
	}

	@Override
	public String getName() {
		return "Steepest";
	}
}
