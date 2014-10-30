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
import edu.mioib.qaplocalsearch.saver.Ex4ExperimentSaver;
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
		AlgorithmRunSettings settings = new AlgorithmRunSettings(100, Long.MAX_VALUE);
		
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

	
	public void runExperimentForExercise2() throws NumberFormatException, ParseException, IOException {
		String[] problemNameList = {"bur26g", "esc16e", "lipa40b", "nug18", "sko100a", "tai80a", "wil100", "kra30a", "scr12", "sko81"};

		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
		AbstractAlgorithm random = new RandomAlgorithm();
		AbstractAlgorithm simpleHeuristic = new SimpleHeuristicAlgorithm();
		
		Ex2ExperimentSaver ex2ExperimentSaver = new Ex2ExperimentSaver();
		
		for(String problemName : problemNameList){
			Problem problem = parseProblemFileFromResource("/"+problemName+".dat");
			Evaluator evaluator = new QapEvaluator(problem);
			AlgorithmRunSettings settings = new AlgorithmRunSettings(15, 1000000);
			List<AlgorithmResult> greedyResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), greedy, evaluator,
					settings);
			List<AlgorithmResult> steepestResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), steepest,
					evaluator, settings);
			List<AlgorithmResult> simpleHeuristicResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), simpleHeuristic,
					evaluator, settings);
			
			int timeExecutionForRandom = 0;
			for(AlgorithmResult algoritmResult : greedyResults){
				timeExecutionForRandom += algoritmResult.getExecutionReport().getExecutionTime();
				ex2ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for(AlgorithmResult algoritmResult : steepestResults){
				timeExecutionForRandom += algoritmResult.getExecutionReport().getExecutionTime();
				ex2ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for(AlgorithmResult algoritmResult : simpleHeuristicResults){
				ex2ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			timeExecutionForRandom /= (greedyResults.size()+steepestResults.size());
			
			AlgorithmRunSettings randomSettings = new AlgorithmRunSettings(15, timeExecutionForRandom);
			List<AlgorithmResult> randomResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), random,
					evaluator, randomSettings);
			for(AlgorithmResult algoritmResult : randomResults){
				ex2ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
		}
		
		ex2ExperimentSaver.saveFile("results/exercise2.csv");
	}
	
	public void runExperimentForExercise4() throws NumberFormatException, ParseException, IOException {
		String[] problemNameList = {"wil100", "esc16e", "lipa40b"};
		
		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
		
		Ex4ExperimentSaver ex4ExperimentSaver = new Ex4ExperimentSaver();
		
		for(String problemName : problemNameList){
			Problem problem = parseProblemFileFromResource("/"+problemName+".dat");
			Evaluator evaluator = new QapEvaluator(problem);
			AlgorithmRunSettings settings = new AlgorithmRunSettings(400, 1000000);
			List<AlgorithmResult> greedyResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), greedy, evaluator,
					settings);
			List<AlgorithmResult> steepestResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), steepest,
					evaluator, settings);
			
			for(AlgorithmResult algoritmResult : greedyResults){
				ex4ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for(AlgorithmResult algoritmResult : steepestResults){
				ex4ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
		}
		ex4ExperimentSaver.saveFile("results/exercise4.csv");
	}
}
