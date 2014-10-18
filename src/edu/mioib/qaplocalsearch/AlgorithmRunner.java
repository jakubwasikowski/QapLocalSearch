package edu.mioib.qaplocalsearch;

import lombok.Value;
import edu.mioib.qaplocalsearch.algorithm.Algorithm;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

@Value
public class AlgorithmRunner {
	Problem problem;
	Algorithm algorithm;
	int executionNumber;
	
	public void runAlgorithm(){
		Solution[] algorithmSolutions = new Solution[executionNumber];
		for(int i=0; i<executionNumber; i++){
			algorithmSolutions[i] = algorithm.resolveProblem(problem);
		}
	}
}
