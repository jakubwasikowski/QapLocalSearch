package edu.mioib.qaplocalsearch.model;

import lombok.Value;

@Value
public class AlgorithmResult {
	Solution solution;
	long executionTime;
}
