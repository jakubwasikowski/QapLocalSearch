package edu.mioib.qaplocalsearch;

import lombok.Value;
import edu.mioib.qaplocalsearch.model.Problem;

@Value
public class QapEvaluator implements Evaluator {

	Problem problem;

	@Override
	public long evaluateState(int[] state) {
		return evaluateStatePartially(state, 0, state.length - 1);
	}

	@Override
	public long evaluateStatePartially(int[] state, int startIdx, int endIdx) {
		long result = 0;
		int[][] localizations = problem.getLocalizations();
		int[][] facilities = problem.getFacilities();

		for (int i = startIdx; i <= endIdx; i++) {
			for (int j = startIdx; j <= endIdx; j++) {
				result += facilities[state[i] - 1][state[j] - 1] * localizations[i][j];
			}
		}

		return result;
	}

}
