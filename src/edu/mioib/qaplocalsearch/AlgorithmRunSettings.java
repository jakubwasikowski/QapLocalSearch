package edu.mioib.qaplocalsearch;

import lombok.Value;

@Value
public class AlgorithmRunSettings {
	public int executionNumber;
	public long maxExecutionTimeSec;

	public long getMaxExecutionTimeNano() {
		return maxExecutionTimeSec * 1000000000;
	}
}