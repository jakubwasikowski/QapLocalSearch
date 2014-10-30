package edu.mioib.qaplocalsearch;

import edu.mioib.qaplocalsearch.parser.ProblemParser;
import edu.mioib.qaplocalsearch.parser.SolutionParser;

public class ExperimentRunner {
	private AlgorithmRunner algorithmRunner;
	private ProblemParser problemParser;
	private SolutionParser solutionParser;
	private ExperimentSaver experimentSaver;

	public ExperimentRunner() {
		this.algorithmRunner = new AlgorithmRunner();
		this.problemParser = new ProblemParser();
		this.solutionParser = new SolutionParser();
		this.experimentSaver = new ExperimentSaver();
	}

	public void runExperimentForExercise3() {
		
	}
}
