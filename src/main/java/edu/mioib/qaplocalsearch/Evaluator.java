package edu.mioib.qaplocalsearch;

import edu.mioib.qaplocalsearch.model.Problem;

public interface Evaluator {
	int evaluateState(Problem problem, int[] startState);
}
