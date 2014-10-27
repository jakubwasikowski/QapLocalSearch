package edu.mioib.qaplocalsearch.algorithm;

import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptStateHolder;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

public class GreedyAlgorithm extends AbstractAlgorithm {

	@Override
	public Solution resolveProblem(Problem problem, Evaluator evaluator, int[] currentState,
			AlgorithmRunMeasurer measurer) {
		int currentEvaluation = evaluator.evaluateState(problem, currentState);
		boolean currentStateChanged;

		TwoOptStateHolder neighbourIterator;
		do{
			neighbourIterator = new TwoOptStateHolder(currentState);
			currentStateChanged = false;
			while (neighbourIterator.hasNextNeighbour()) {
				neighbourIterator.nextNeighbour();
				int newStateEvaluation = evaluator.evaluateState(problem, currentState);
				if (newStateEvaluation < currentEvaluation) {
					neighbourIterator.saveCurrentNeighbourAsTheBest();
					neighbourIterator.switchToTheBestNeighbour();
					currentEvaluation = newStateEvaluation;
					currentStateChanged = true;
					break;
				}
			}
			if (!currentStateChanged) {
				neighbourIterator.switchToOriginalState();
			}
		} while (currentStateChanged && !checkIfInterrupt(measurer));
		
		return new Solution(currentEvaluation, currentState);
	}

	@Override
	public String getName() {
		return "Greedy";
	}
}
