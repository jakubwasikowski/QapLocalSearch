package edu.mioib.qaplocalsearch.model;

import lombok.Getter;

@Getter
public class ExecutionReport {
	private int stepsNumber = 0;
	private int evaluatedStatesNumber = 0;

	public void recordStep() {
		stepsNumber++;
	}

	public void recordEvaluatedState() {
		evaluatedStatesNumber++;
	}
}
