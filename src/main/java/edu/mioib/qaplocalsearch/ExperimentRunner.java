package edu.mioib.qaplocalsearch;

import static edu.mioib.qaplocalsearch.parser.ProblemParser.parseProblemFile;
import static edu.mioib.qaplocalsearch.parser.SolutionParser.parseSolutionFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import edu.mioib.qaplocalsearch.algorithm.Algorithm;
import edu.mioib.qaplocalsearch.algorithm.GreedyAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.SimulatedAnnealingAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.SteepestAlgorithm;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;
import edu.mioib.qaplocalsearch.parser.ProblemParser;
import edu.mioib.qaplocalsearch.parser.SolutionParser;

public class ExperimentRunner {
	private List<Algorithm> algorithms;
	private AlgorithmRunner algorithmRunner;

	private ProblemParser problemParser;
	private SolutionParser solutionParser;
	private ExperimentSaver experimentSaver;

	public ExperimentRunner() {
		initAlgorithm();
		this.algorithmRunner = new AlgorithmRunner();

		this.problemParser = new ProblemParser();
		this.solutionParser = new SolutionParser();
		this.experimentSaver = new ExperimentSaver();
	}

	public void runExperminents() throws IOException, NumberFormatException, ParseException {
		Problem problem = parseProblemFile(getClass().getResourceAsStream("/tai40b.dat"));
		Solution solution = parseSolutionFile(getClass().getResourceAsStream("/tai40b.sln"));
	}

	private void initAlgorithm() {
		algorithms = new ArrayList<Algorithm>();
		algorithms.add(new GreedyAlgorithm());
		algorithms.add(new SteepestAlgorithm());
		algorithms.add(new SimulatedAnnealingAlgorithm(10000, 0.003));
	}
}
