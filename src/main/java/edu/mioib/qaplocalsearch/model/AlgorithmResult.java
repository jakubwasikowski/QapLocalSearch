package edu.mioib.qaplocalsearch.model;

import lombok.Value;

@Value
public class AlgorithmResult {
	String algorithmName;
	StateEvaluation initialState;
	StateEvaluation solution;
	long executionTime;
	ExecutionReport executionReport;
}
