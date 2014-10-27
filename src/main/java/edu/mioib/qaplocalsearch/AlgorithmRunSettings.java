package edu.mioib.qaplocalsearch;

import lombok.Value;

@Value
public class AlgorithmRunSettings {
	public int executionNumber;
	public long maxExecutionSecTime;

	public long getMaxExecutionNanoTime() {
		return maxExecutionSecTime * 1000000000;
	}
}