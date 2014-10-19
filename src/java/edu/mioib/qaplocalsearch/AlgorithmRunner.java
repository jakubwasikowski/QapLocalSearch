package edu.mioib.qaplocalsearch;

import static java.lang.System.nanoTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Value;
import edu.mioib.qaplocalsearch.algorithm.Algorithm;
import edu.mioib.qaplocalsearch.model.AlgorithmResult;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;
import edu.mioib.qaplocalsearch.helper.ArraysUtil;

@Value
public class AlgorithmRunner {
	public List<AlgorithmResult> runAlgorithm(Problem problem, Algorithm algorithm, AlgorithmRunSettings settings) {
		List<AlgorithmResult> result = new ArrayList<AlgorithmResult>(settings.getExecutionNumber());

		long startTime = nanoTime();
		long lastTime = startTime;
		int callCounter = 0;
		while (lastTime - startTime < settings.getMaxExecutionTimeNano()
				|| callCounter < settings.getExecutionNumber()) {
			Solution solution = algorithm.resolveProblem(problem, startState(problem.getProblemSize()));
			
			result.add(new AlgorithmResult(solution, nanoTime() - lastTime));

			lastTime = nanoTime();
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
