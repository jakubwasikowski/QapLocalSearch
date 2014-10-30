package edu.mioib.qaplocalsearch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ExecutionReport {
	@Setter
	private long executionTime;
	private int stepsNumber = 0;
	private int evaluatedStatesNumber = 0;

	public void recordStep() {
		stepsNumber++;
	}

	public void recordEvaluatedState() {
		evaluatedStatesNumber++;
	}
}
