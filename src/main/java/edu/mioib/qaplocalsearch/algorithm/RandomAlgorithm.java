package edu.mioib.qaplocalsearch.algorithm;

import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.helper.ArraysUtil;

public class RandomAlgorithm extends AbstractAlgorithm {

	@Override
	public int[] resolveProblem(int[] startState, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		int[] currentState = startState;
		int[] bestState = currentState;
		int bestEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);

		while (!checkIfInterrupt(measurer)) {
			int[] genState = ArraysUtil.generateRandomPerm(currentState.length);
			int genStateEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, genState);

			if (genStateEvaluation < bestEvaluation) {
				bestEvaluation = genStateEvaluation;
				bestState = genState;
			}

			measurer.recordStep();
		}

		return bestState;
	}

	@Override
	public String getName() {
		return "Random";
	}
}
