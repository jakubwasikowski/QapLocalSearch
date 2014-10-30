package edu.mioib.qaplocalsearch;

import static edu.mioib.qaplocalsearch.parser.ProblemParser.parseProblemFileFromResource;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import edu.mioib.qaplocalsearch.algorithm.AbstractAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.GreedyAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.SteepestAlgorithm;
import edu.mioib.qaplocalsearch.model.AlgorithmResult;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.saver.ExperimentSaver;
import edu.mioib.qaplocalsearch.saver.GenericExperimentSaver;

public class ExperimentRunner {
	private AlgorithmRunner algorithmRunner;
	private ExperimentSaver experimentSaver;

	public ExperimentRunner() {
		this.algorithmRunner = new AlgorithmRunner();
		this.experimentSaver = new ExperimentSaver();
	}

	public void runExperimentForExercise3() throws NumberFormatException, ParseException, IOException {
		Problem problem = parseProblemFileFromResource("/lipa90b.dat");

		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
		
		Evaluator evaluator = new QapEvaluator(problem);
		AlgorithmRunSettings settings = new AlgorithmRunSettings(10, Long.MAX_VALUE);
		
		List<AlgorithmResult> greedyResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), greedy, evaluator,
				settings);
		List<AlgorithmResult> steepestResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), steepest,
				evaluator, settings);

		List<String> columnsNames = Lists.newArrayList("greedy - jakość rozwiązania początkowego",
				"greedy - jakość rozwiązania końcowego", "steepest - jakość rozwiązania początkowego",
				"steepest - jakość rozwiązania końcowego");

		List<List<String>> valueRows = Lists.newArrayListWithCapacity(greedyResults.size());
		for (int i = 0; i < greedyResults.size(); i++) {
			int greedyInitialEvaluation = greedyResults.get(i).getInitialState().getEvaluation();
			int greedySolutionEvaluation = greedyResults.get(i).getSolution().getEvaluation();
			int steepestInitialEvaluation = steepestResults.get(i).getInitialState().getEvaluation();
			int steepestSolutionEvaluation = steepestResults.get(i).getSolution().getEvaluation();
			valueRows.add(Lists.newArrayList(Integer.toString(greedyInitialEvaluation),
					Integer.toString(greedySolutionEvaluation), Integer.toString(steepestInitialEvaluation),
					Integer.toString(steepestSolutionEvaluation)));
		}

		GenericExperimentSaver.save("results/exercise3.csv", columnsNames, valueRows);
	}
}
