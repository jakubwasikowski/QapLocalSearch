package edu.mioib.qaplocalsearch;

import edu.mioib.qaplocalsearch.model.Problem;

public class QapEvaluator implements Evaluator {

	@Override
	public int evaluateState(Problem problem, int[] state) {
		int result = 0;
		int[][] localizations = problem.getLocalizations();
		int[][] facilities = problem.getFacilities();

		for (int i = 0; i < problem.getProblemSize(); i++) {
			for (int j = 0; j < problem.getProblemSize(); j++) {
				result += facilities[state[i] - 1][state[j] - 1] * localizations[i][j];
			}
		}

		return result;
	}

}
