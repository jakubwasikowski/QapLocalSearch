package edu.mioib.qaplocalsearch;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import edu.mioib.qaplocalsearch.algorithm.AbstractAlgorithm;
import edu.mioib.qaplocalsearch.model.AlgorithmResult;
import edu.mioib.qaplocalsearch.model.Solution;

@Value
public class AlgorithmRunner {
	public List<AlgorithmResult> runAlgorithm(int problemSize, AbstractAlgorithm algorithm, Evaluator evaluator,
			AlgorithmRunSettings settings) {
		List<AlgorithmResult> result = new ArrayList<AlgorithmResult>(settings.getExecutionNumber());

		AlgorithmRunMeasurer measurer = new AlgorithmRunMeasurer(settings);
		int callCounter = 0;
		while (callCounter < settings.getExecutionNumber()) {
			measurer.startMeasuring();
			int[] perm = algorithm.resolveProblem(problemSize, evaluator, measurer);
			long executionTime = measurer.stopMeasuring();
			
			Solution solution = new Solution(evaluator.evaluateState(perm), perm);
			result.add(new AlgorithmResult(algorithm.getName(), solution, executionTime));

			callCounter++;
		}

		return result;
	}
}
