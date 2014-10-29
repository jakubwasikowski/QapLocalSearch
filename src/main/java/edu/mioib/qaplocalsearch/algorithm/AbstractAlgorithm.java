package edu.mioib.qaplocalsearch.algorithm;

import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;

public abstract class AbstractAlgorithm {

	public abstract int[] resolveProblem(int permSize, Evaluator evaluator, AlgorithmRunMeasurer measurer);
	
	public abstract String getName();

	protected boolean checkIfInterrupt(AlgorithmRunMeasurer measurer) {
		if (!(measurer.isRunTimeCorrect())) {
			System.out.println(getName() + " algorithm interrupted");
			return true;
		}
		return false;
	}
}
