package edu.mioib.qaplocalsearch;

import static java.lang.System.nanoTime;
import lombok.Getter;
import lombok.experimental.Delegate;
import edu.mioib.qaplocalsearch.model.ExecutionReport;

public class AlgorithmRunMeasurer {
	private AlgorithmRunSettings settings;
	private long startTime;
	private Long lastTime;

	@Getter
	@Delegate(types = ExecutionReport.class)
	private ExecutionReport executionReport;

	public AlgorithmRunMeasurer(AlgorithmRunSettings runSettings) {
		this.settings = runSettings;
		this.startTime = nanoTime();
		this.lastTime = null;
		this.executionReport = new ExecutionReport();
	}

	public void startMeasuring() {
		if (lastTime != null) {
			throw new IllegalStateException();
		}
		lastTime = nanoTime();
	}

	public boolean isRunTimeCorrect() {
		if (lastTime == null) {
			throw new IllegalStateException();
		}
		long timeInterval = nanoTime() - startTime;
		return (timeInterval / 1000000000) < settings.getMaxExecutionSecTime();
	}

	public long stopMeasuring() {
		if (lastTime == null) {
			throw new IllegalStateException();
		}
		long result = nanoTime() - lastTime;
		lastTime = null;

		return result/1000000000;
	}
}
