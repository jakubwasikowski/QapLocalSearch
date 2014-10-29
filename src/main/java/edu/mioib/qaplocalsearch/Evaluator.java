package edu.mioib.qaplocalsearch;


public interface Evaluator {
	int evaluateState(int[] state);

	int evaluateStatePartially(int[] state, int startIdx, int endIdx);
}
