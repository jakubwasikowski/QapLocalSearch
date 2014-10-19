package edu.mioib.qaplocalsearch;

import static java.lang.System.nanoTime;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import edu.mioib.qaplocalsearch.algorithm.Algorithm;
import edu.mioib.qaplocalsearch.model.AlgorithmResult;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

@Value
public class AlgorithmRunner {
	public static List<AlgorithmResult> runAlgorithm(Problem problem, Algorithm algorithm, AlgorithmRunSettings settings) {
		List<AlgorithmResult> result = new ArrayList<AlgorithmResult>(settings.getExecutionNumber());

		long startTime = nanoTime();
		long lastTime = startTime;
		int callCounter = 0;
		while (lastTime - startTime < settings.getMaxExecutionTimeNano()
				|| callCounter < settings.getExecutionNumber()) {
			Solution solution = algorithm.resolveProblem(problem);
			
			result.add(new AlgorithmResult(solution, nanoTime() - lastTime));

			lastTime = nanoTime();
			callCounter++;
		}

		return result;
	}
}
