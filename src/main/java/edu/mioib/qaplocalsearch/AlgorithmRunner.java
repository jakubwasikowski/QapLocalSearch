package edu.mioib.qaplocalsearch;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import edu.mioib.qaplocalsearch.algorithm.AbstractAlgorithm;
import edu.mioib.qaplocalsearch.helper.ArraysUtil;
import edu.mioib.qaplocalsearch.model.AlgorithmResult;
import edu.mioib.qaplocalsearch.model.StateEvaluation;

@Value
public class AlgorithmRunner {
	public List<AlgorithmResult> runAlgorithm(int problemSize, AbstractAlgorithm algorithm, Evaluator evaluator,
			AlgorithmRunSettings settings) {
		List<AlgorithmResult> result = new ArrayList<AlgorithmResult>(settings.getExecutionNumber());

		int callCounter = 0;
		while (callCounter < settings.getExecutionNumber()) {
			AlgorithmRunMeasurer measurer = new AlgorithmRunMeasurer(settings);
			int[] startState = ArraysUtil.generateRandomPerm(problemSize);
			
			int[] startStateClone = startState.clone();
			measurer.startMeasuring();
			int[] finalState = algorithm.resolveProblem(startStateClone, evaluator, measurer);
			long executionTime = measurer.stopMeasuring();
			
			StateEvaluation initialState = new StateEvaluation(evaluator.evaluateState(startState), startState);
			StateEvaluation solution = new StateEvaluation(evaluator.evaluateState(finalState), finalState);
			result.add(new AlgorithmResult(algorithm.getName(), initialState, solution, executionTime, measurer
					.getExecutionReport()));

			callCounter++;
		}

		return result;
	}
}
