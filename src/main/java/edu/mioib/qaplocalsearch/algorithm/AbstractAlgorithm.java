package edu.mioib.qaplocalsearch.algorithm;

import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

public abstract class AbstractAlgorithm {

	public abstract Solution resolveProblem(Problem problem, Evaluator evaluator, int[] currentState,
			AlgorithmRunMeasurer measurer);
	
	public abstract String getName();

	protected boolean checkIfInterrupt(AlgorithmRunMeasurer measurer) {
		if (!(measurer.isRunTimeCorrect())) {
			System.out.println(getName() + " algorithm interrupted");
			return true;
		}
		return false;
	}
}
