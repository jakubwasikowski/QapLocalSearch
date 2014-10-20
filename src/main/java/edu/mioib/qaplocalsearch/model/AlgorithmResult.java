package edu.mioib.qaplocalsearch.model;

import lombok.Value;

@Value
public class AlgorithmResult {
	String algorithmName;
	Solution solution;
	long executionTime;
}
