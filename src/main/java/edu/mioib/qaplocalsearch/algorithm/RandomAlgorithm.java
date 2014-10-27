package edu.mioib.qaplocalsearch.algorithm;

import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.helper.ArraysUtil;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

public class RandomAlgorithm extends AbstractAlgorithm {

	@Override
	public Solution resolveProblem(Problem problem, Evaluator evaluator, int[] currentState,
			AlgorithmRunMeasurer measurer) {
		int[] bestState = currentState;
		int bestEvaluation = evaluator.evaluateState(problem, currentState);

		while (!checkIfInterrupt(measurer)) {
			int[] genState = ArraysUtil.generateRandomPerm(currentState.length);
			int genStateEvaluation = evaluator.evaluateState(problem, genState);

			if (genStateEvaluation < bestEvaluation) {
				bestEvaluation = genStateEvaluation;
				bestState = genState;
			}
		}

		return new Solution(bestEvaluation, bestState);
	}

	@Override
	public String getName() {
		return "Random";
	}
}
