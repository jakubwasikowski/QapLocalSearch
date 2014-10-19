package edu.mioib.qaplocalsearch;

import edu.mioib.qaplocalsearch.model.Problem;

public class QapEvaluator implements Evaluator {

	@Override
	public int evaluateState(Problem problem, int[] startState) {
		int result = 0;
		int[][] localizations = problem.getLocalizations();
		int[][] facilities = problem.getFacilities();

		for (int i = 0; i < startState.length - 1; i++) {
			result += localizations[startState[i]][startState[i + 1]] * facilities[startState[i]][startState[i + 1]];
		}

		return result;
	}

}
