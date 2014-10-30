package edu.mioib.qaplocalsearch;

import static edu.mioib.qaplocalsearch.parser.ProblemParser.parseProblemFile;

import java.io.IOException;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import edu.mioib.qaplocalsearch.algorithm.AbstractAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.GreedyAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.SteepestAlgorithm;
import edu.mioib.qaplocalsearch.model.Problem;
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

	public void runExperimentForExercise3() throws NumberFormatException, ParseException, IOException {
		Problem problem = parseProblemFile(getClass().getResourceAsStream("/tai40b.dat"));

		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
	}
}
