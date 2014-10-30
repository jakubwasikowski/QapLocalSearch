package edu.mioib.qaplocalsearch;

import static edu.mioib.qaplocalsearch.parser.ProblemParser.parseProblemFileFromResource;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import edu.mioib.qaplocalsearch.algorithm.AbstractAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.GreedyAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.RandomAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.SimpleHeuristicAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.SteepestAlgorithm;
import edu.mioib.qaplocalsearch.model.AlgorithmResult;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.saver.ComparisonExperimentSaver;
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
	
	public void runExperimentForExercise1() throws NumberFormatException, ParseException, IOException {
		String[] problemNameList = {"bur26g", "esc16e", "lipa40b", "nug18", "sko100a", "tai80a", "wil100", "kra30a", "scr12", "sko81"};

		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
		AbstractAlgorithm random = new RandomAlgorithm();
		AbstractAlgorithm simpleHeuristic = new SimpleHeuristicAlgorithm();
		
		ComparisonExperimentSaver comparisonExperimentSaver = new ComparisonExperimentSaver();
		
		for(String problemName : problemNameList){
			Problem problem = parseProblemFileFromResource("/"+problemName+".dat");
			Evaluator evaluator = new QapEvaluator(problem);
			AlgorithmRunSettings settings = new AlgorithmRunSettings(10000, 60);
			List<AlgorithmResult> greedyResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), greedy, evaluator,
					settings);
			List<AlgorithmResult> steepestResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), steepest,
					evaluator, settings);
			List<AlgorithmResult> simpleHeuristicResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), simpleHeuristic,
					evaluator, settings);
			
			int timeExecutionForRandom = 0;
			for(AlgorithmResult algoritmResult : greedyResults){
				timeExecutionForRandom += algoritmResult.getExecutionReport().getExecutionTime();
				comparisonExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for(AlgorithmResult algoritmResult : steepestResults){
				timeExecutionForRandom += algoritmResult.getExecutionReport().getExecutionTime();
				comparisonExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for(AlgorithmResult algoritmResult : simpleHeuristicResults){
				comparisonExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			timeExecutionForRandom /= (greedyResults.size()+steepestResults.size());
			
			AlgorithmRunSettings randomSettings = new AlgorithmRunSettings(timeExecutionForRandom, 60);
			List<AlgorithmResult> randomResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), random,
					evaluator, randomSettings);
			for(AlgorithmResult algoritmResult : randomResults){
				comparisonExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
		}
		
		comparisonExperimentSaver.saveFile("ex1.csv");
	}
}
