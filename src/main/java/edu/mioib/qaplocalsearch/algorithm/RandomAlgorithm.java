package edu.mioib.qaplocalsearch.algorithm;

import static edu.mioib.qaplocalsearch.helper.ArraysUtil.generateRandomPerm;
import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.helper.ArraysUtil;

public class RandomAlgorithm extends AbstractAlgorithm {

	@Override
	public int[] resolveProblem(int permSize, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		int[] currentState = generateRandomPerm(permSize);
		int[] bestState = currentState;
		int bestEvaluation = evaluator.evaluateState(currentState);

		while (!checkIfInterrupt(measurer)) {
			int[] genState = ArraysUtil.generateRandomPerm(currentState.length);
			int genStateEvaluation = evaluator.evaluateState(genState);

			if (genStateEvaluation < bestEvaluation) {
				bestEvaluation = genStateEvaluation;
				bestState = genState;
			}
		}

		return bestState;
	}

	@Override
	public String getName() {
		return "Random";
	}
}
