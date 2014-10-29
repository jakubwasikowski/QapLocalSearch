package edu.mioib.qaplocalsearch.algorithm;

import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptStateHolder;

public class GreedyAlgorithm extends AbstractAlgorithm {

	@Override
	public int[] resolveProblem(int[] startState, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		int[] currentState = startState;
		int currentEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);
		boolean currentStateChanged;

		TwoOptStateHolder neighbourIterator;
		do{
			neighbourIterator = new TwoOptStateHolder(currentState);
			currentStateChanged = false;
			while (neighbourIterator.hasNextNeighbour()) {
				neighbourIterator.nextNeighbour();
				int newStateEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);
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

			measurer.recordStep();
		} while (currentStateChanged && !checkIfInterrupt(measurer));
		
		return currentState;
	}

	@Override
	public String getName() {
		return "Greedy";
	}
}
