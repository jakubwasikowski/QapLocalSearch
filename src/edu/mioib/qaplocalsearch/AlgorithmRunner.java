package edu.mioib.qaplocalsearch;

import static java.lang.System.nanoTime;
import lombok.Value;
import edu.mioib.qaplocalsearch.algorithm.Algorithm;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

@Value
public class AlgorithmRunner {
	Problem problem;
	Algorithm algorithm;
	AlgorithmRunSettings settings;

	public void runAlgorithm(){
		long startTime = nanoTime();
		int callCounter = 0;

		Solution[] algorithmSolutions = new Solution[settings.executionNumber];
		while (nanoTime() - startTime < settings.getMaxExecutionTimeNano()
				|| callCounter < settings.getExecutionNumber()) {
			algorithmSolutions[callCounter] = algorithm.resolveProblem(problem);

			callCounter++;
		}

		long avgTime = (nanoTime() - startTime) / callCounter;
	}
}
