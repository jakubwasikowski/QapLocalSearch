package edu.mioib.qaplocalsearch;

import static edu.mioib.qaplocalsearch.parser.ProblemParser.parseProblemFileFromResource;

import java.io.IOException;
import java.util.List;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import edu.mioib.qaplocalsearch.algorithm.AbstractAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.GreedyAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.SteepestAlgorithm;
import edu.mioib.qaplocalsearch.model.AlgorithmResult;
import edu.mioib.qaplocalsearch.model.Problem;

public class ExperimentRunner {
	private AlgorithmRunner algorithmRunner;
	private ExperimentSaver experimentSaver;

	public ExperimentRunner() {
		this.algorithmRunner = new AlgorithmRunner();
		this.experimentSaver = new ExperimentSaver();
	}

	public void runExperimentForExercise3() throws NumberFormatException, ParseException, IOException {
		Problem problem = parseProblemFileFromResource("/tai40b.dat");

		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
		
		Evaluator evaluator = new QapEvaluator(problem);
		AlgorithmRunSettings settings = new AlgorithmRunSettings(200, 60);
		
		List<AlgorithmResult> greedyResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), greedy, evaluator,
				settings);
		List<AlgorithmResult> steepestResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), steepest,
				evaluator, settings);

		System.out.println(greedyResults);
		System.out.println(steepestResults);
	}
}
