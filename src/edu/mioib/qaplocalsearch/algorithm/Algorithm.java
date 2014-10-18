package edu.mioib.qaplocalsearch.algorithm;

import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

public interface Algorithm {

	Solution resolveProblem(Problem problem);
	
}
