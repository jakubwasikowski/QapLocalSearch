package edu.mioib.qaplocalsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Value;
import edu.mioib.qaplocalsearch.algorithm.AbstractAlgorithm;
import edu.mioib.qaplocalsearch.helper.ArraysUtil;
import edu.mioib.qaplocalsearch.model.AlgorithmResult;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

@Value
public class AlgorithmRunner {
	public List<AlgorithmResult> runAlgorithm(Problem problem, AbstractAlgorithm algorithm, Evaluator evaluator,
			AlgorithmRunSettings settings) {
		List<AlgorithmResult> result = new ArrayList<AlgorithmResult>(settings.getExecutionNumber());

		AlgorithmRunMeasurer measurer = new AlgorithmRunMeasurer(settings);
		int callCounter = 0;
		while (callCounter < settings.getExecutionNumber()) {
			measurer.startMeasuring();
			Solution solution = algorithm.resolveProblem(problem, evaluator, startState(problem.getProblemSize()),
					measurer);
			long executionTime = measurer.stopMeasuring();
			
			result.add(new AlgorithmResult(algorithm.getName(), solution, executionTime));

			callCounter++;
		}

		return result;
	}
	
	private int[] startState(int problemSize){
		int[] randomState = new int[problemSize];
		for(int i=0; i<problemSize; i++){
			randomState[i] = i+1;
		}
		
		Random rand = new Random();
		for(int i=0; i<problemSize-1; i++){
			int randomNum = rand.nextInt(problemSize-i + 1);
			ArraysUtil.swap(randomState, randomNum, randomState[problemSize-i-1]);
		}
		return randomState;
	}
}
