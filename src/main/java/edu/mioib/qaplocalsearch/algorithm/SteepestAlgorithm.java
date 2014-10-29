package edu.mioib.qaplocalsearch.algorithm;

import static edu.mioib.qaplocalsearch.helper.ArraysUtil.generateRandomPerm;
import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptStateHolder;

public class SteepestAlgorithm extends AbstractAlgorithm {

	@Override
	public int[] resolveProblem(int permSize, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		int[] currentState = generateRandomPerm(permSize);
		int currentEvaluation = evaluator.evaluateState(currentState);
		boolean currentStateChanged;

		TwoOptStateHolder neighbourIterator;
		do {
			neighbourIterator = new TwoOptStateHolder(currentState);
			currentStateChanged = false;
			while (neighbourIterator.hasNextNeighbour()) {
				neighbourIterator.nextNeighbour();
				int newStateEvaluation = evaluator.evaluateState(currentState);
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
		} while (currentStateChanged && !checkIfInterrupt(measurer));

		return currentState;
	}

	@Override
	public String getName() {
		return "Steepest";
	}
}
