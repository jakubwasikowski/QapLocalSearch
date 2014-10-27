package edu.mioib.qaplocalsearch;

import static java.lang.System.nanoTime;

public class AlgorithmRunMeasurer {
	private AlgorithmRunSettings settings;
	private long startTime;
	private Long lastTime;

	public AlgorithmRunMeasurer(AlgorithmRunSettings runSettings) {
		this.settings = runSettings;
		this.startTime = nanoTime();
		this.lastTime = null;
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
		return nanoTime() - startTime < settings.getMaxExecutionNanoTime();
	}

	public long stopMeasuring() {
		if (lastTime == null) {
			throw new IllegalStateException();
		}
		long result = nanoTime() - lastTime;
		lastTime = null;

		return result;
	}
}
