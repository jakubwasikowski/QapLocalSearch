package edu.mioib.qaplocalsearch;


public interface Evaluator {
	long evaluateState(int[] state);

	long evaluateStatePartially(int[] state, int startIdx, int endIdx);
}
