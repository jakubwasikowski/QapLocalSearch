package edu.mioib.qaplocalsearch.algorithm;

import java.util.Random;

import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.helper.ArraysUtil;

public class SimpleHeuristicAlgorithm extends AbstractAlgorithm {

	@Override
	public int[] resolveProblem(int permSize, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		Random rand = new Random();
		int[] state = new int[permSize];
		int currentResultIdx = 0;

		int[] availableValues = new int[permSize];
		int lastAvailableValueIndex = permSize - 1;
		for (int i = 0; i < permSize; i++) {
			availableValues[i] = i + 1;
		}

		int randomFirstIdx = rand.nextInt(permSize);
		state[currentResultIdx] = availableValues[randomFirstIdx];
		currentResultIdx++;

		ArraysUtil.swap(availableValues, randomFirstIdx, lastAvailableValueIndex);
		lastAvailableValueIndex--;
		
		for (; currentResultIdx < permSize; currentResultIdx++) {
			int bestEval = Integer.MAX_VALUE;
			int bestAvailableIdx = -1;
			for (int i = 0; i <= lastAvailableValueIndex; i++) {
				state[currentResultIdx] = availableValues[i];
				int stateEval = evaluator.evaluateStatePartially(state, 0, currentResultIdx);
				if (stateEval < bestEval) {
					bestAvailableIdx = i;
					bestEval = stateEval;
				}
			}

			state[currentResultIdx] = availableValues[bestAvailableIdx];
			ArraysUtil.swap(availableValues, bestAvailableIdx, lastAvailableValueIndex);
			lastAvailableValueIndex--;
		}

		return state;
	}

	@Override
	public String getName() {
		return "Simple heuristic";
	}

}
