package edu.mioib.qaplocalsearch;

import static edu.mioib.qaplocalsearch.parser.ProblemParser.parseProblemFileFromResource;
import static edu.mioib.qaplocalsearch.parser.SolutionParser.parseSolutionFileFromResource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import edu.mioib.qaplocalsearch.algorithm.AbstractAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.GreedyAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.RandomAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.SimpleHeuristicAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.SimulatedAnnealingAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.SteepestAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.TabuSearchAlgorithm;
import edu.mioib.qaplocalsearch.model.AlgorithmResult;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.StateEvaluation;
import edu.mioib.qaplocalsearch.permutation.DamerauLevenshteinAlgorithm;
import edu.mioib.qaplocalsearch.saver.Ex2ExperimentSaver;
import edu.mioib.qaplocalsearch.saver.Ex4ExperimentSaver;
import edu.mioib.qaplocalsearch.saver.ExTSSAExperimentSaver;
import edu.mioib.qaplocalsearch.saver.ExperimentSaver;
import edu.mioib.qaplocalsearch.saver.GenericExperimentSaver;

//TODO refactor this class
public class ExperimentRunner {
	private AlgorithmRunner algorithmRunner;
	private ExperimentSaver experimentSaver;

	public ExperimentRunner() {
		this.algorithmRunner = new AlgorithmRunner();
		this.experimentSaver = new ExperimentSaver();
	}

	public void runExperimentForExercise3() throws NumberFormatException, ParseException, IOException {
		String[] problemNameList = { "bur26g", "esc16e", "lipa40b", "nug18", "sko100a", "tai80a", "wil100", "kra30a",
				"sko81", "chr12a", "scr20", "had12", "tai40b", "lipa90b" };
		for (String problemName : problemNameList) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + problemName);
			Problem problem = parseProblemFileFromResource("/" + problemName + ".dat");

			AbstractAlgorithm greedy = new GreedyAlgorithm();
			AbstractAlgorithm steepest = new SteepestAlgorithm();

			Evaluator evaluator = new QapEvaluator(problem);
			AlgorithmRunSettings settings = new AlgorithmRunSettings(200, Long.MAX_VALUE);

			List<AlgorithmResult> greedyResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), greedy,
					evaluator, settings);
			List<AlgorithmResult> steepestResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), steepest,
					evaluator, settings);

			List<String> columnsNames = Lists.newArrayList("greedy - jakość rozwiązania początkowego",
					"greedy - jakość rozwiązania końcowego", "steepest - jakość rozwiązania początkowego",
					"steepest - jakość rozwiązania końcowego");

			List<List<String>> valueRows = Lists.newArrayListWithCapacity(greedyResults.size());
			for (int i = 0; i < greedyResults.size(); i++) {
				long greedyInitialEvaluation = greedyResults.get(i).getInitialState().getEvaluation();
				long greedySolutionEvaluation = greedyResults.get(i).getSolution().getEvaluation();
				long steepestInitialEvaluation = steepestResults.get(i).getInitialState().getEvaluation();
				long steepestSolutionEvaluation = steepestResults.get(i).getSolution().getEvaluation();
				valueRows.add(Lists.newArrayList(Long.toString(greedyInitialEvaluation),
						Long.toString(greedySolutionEvaluation), Long.toString(steepestInitialEvaluation),
						Long.toString(steepestSolutionEvaluation)));
			}

			GenericExperimentSaver.save("results/exercise3_" + problemName + ".csv", columnsNames, valueRows);
		}
	}

	
	public void runExperimentForExercise2() throws NumberFormatException, ParseException, IOException {
		String[] problemNameList = { "bur26g", "esc16e", "lipa40b", "nug18", "sko100a", "tai80a", "wil100", "kra30a",
				"scr12", "sko81" };

		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
		AbstractAlgorithm random = new RandomAlgorithm();
		AbstractAlgorithm simpleHeuristic = new SimpleHeuristicAlgorithm();
		AbstractAlgorithm simulatedAnnealing = new SimulatedAnnealingAlgorithm(); //FIXME test value
		AbstractAlgorithm tabuSearch = new TabuSearchAlgorithm();
		
		Ex2ExperimentSaver ex2ExperimentSaver = new Ex2ExperimentSaver();
		
		for(String problemName : problemNameList){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + problemName);
			Problem problem = parseProblemFileFromResource("/"+problemName+".dat");
			Evaluator evaluator = new QapEvaluator(problem);
			AlgorithmRunSettings settings = new AlgorithmRunSettings(15, Long.MAX_VALUE);
			List<AlgorithmResult> greedyResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), greedy,
					evaluator, settings);
			List<AlgorithmResult> steepestResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), steepest,
					evaluator, settings);
			List<AlgorithmResult> simpleHeuristicResults = algorithmRunner.runAlgorithm(problem.getProblemSize(),
					simpleHeuristic, evaluator, settings);
			List<AlgorithmResult> simulatedAnnealingResults = algorithmRunner.runAlgorithm(problem.getProblemSize(),
					simulatedAnnealing, evaluator, settings);
			List<AlgorithmResult> tabuSearchResults = algorithmRunner.runAlgorithm(problem.getProblemSize(),
					tabuSearch, evaluator, settings);
			
			long timeExecutionForRandom = 0;
			for (AlgorithmResult algoritmResult : greedyResults) {
				timeExecutionForRandom += algoritmResult.getExecutionReport().getExecutionTime();
				ex2ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for (AlgorithmResult algoritmResult : steepestResults) {
				timeExecutionForRandom += algoritmResult.getExecutionReport().getExecutionTime();
				ex2ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for (AlgorithmResult algoritmResult : simpleHeuristicResults) {
				ex2ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			timeExecutionForRandom /= (greedyResults.size() + steepestResults.size());

			for (AlgorithmResult algoritmResult : simulatedAnnealingResults) {
				ex2ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for (AlgorithmResult algoritmResult : tabuSearchResults) {
				ex2ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}

			AlgorithmRunSettings randomSettings = new AlgorithmRunSettings(15, timeExecutionForRandom);
			List<AlgorithmResult> randomResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), random,
					evaluator, randomSettings);
			for (AlgorithmResult algoritmResult : randomResults) {
				ex2ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
		}
		
		ex2ExperimentSaver.saveFile("results/exercise2.csv");
	}
	
	public void runExperimentForExercise4() throws NumberFormatException, ParseException, IOException {
		String[] problemNameList = {"esc16e", "lipa40b", "wil100"};
		
		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
		
		Ex4ExperimentSaver ex4ExperimentSaver = new Ex4ExperimentSaver();
		
		for(String problemName : problemNameList){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + problemName);
			Problem problem = parseProblemFileFromResource("/"+problemName+".dat");
			Evaluator evaluator = new QapEvaluator(problem);
			AlgorithmRunSettings settings = new AlgorithmRunSettings(400, Long.MAX_VALUE);
			List<AlgorithmResult> greedyResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), greedy, evaluator,
					settings);
			List<AlgorithmResult> steepestResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), steepest,
					evaluator, settings);
			
			for(AlgorithmResult algoritmResult : greedyResults){
				ex4ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			ex4ExperimentSaver.nextAlgoritm();
			for(AlgorithmResult algoritmResult : steepestResults){
				ex4ExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			ex4ExperimentSaver.nextAlgoritm();
		}
		ex4ExperimentSaver.saveFile("results/exercise4.csv");
	}

	public void runExperimentForExercise5() throws NumberFormatException, ParseException, IOException {
		String[] problemNameList = { "had12", "bur26a", "lipa90b" };

		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
		AbstractAlgorithm random = new RandomAlgorithm();
		AbstractAlgorithm heuristic = new SimpleHeuristicAlgorithm();

		for (String problemName : problemNameList) {
			Problem problem = parseProblemFileFromResource("/" + problemName + ".dat");
			StateEvaluation solution = parseSolutionFileFromResource("/" + problemName + ".sln");

			Evaluator evaluator = new QapEvaluator(problem);
			AlgorithmRunSettings settings = new AlgorithmRunSettings(1, Long.MAX_VALUE);
			List<AlgorithmResult> greedyResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), greedy,
					evaluator, settings);
			List<AlgorithmResult> steepestResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), steepest,
					evaluator, settings);
			List<AlgorithmResult> heuristicResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), heuristic,
					evaluator, settings);

			long randomMaxExecutionTime = (greedyResults.get(0).getExecutionReport().getExecutionTime() + steepestResults
					.get(0).getExecutionReport().getExecutionTime()) / 2;
			AlgorithmRunSettings randomSettings = new AlgorithmRunSettings(1, randomMaxExecutionTime);
			List<AlgorithmResult> randomResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), random,
					evaluator, randomSettings);

			System.out.println("Optimum (ocena " + solution.getEvaluation() + "):");
			System.out.println(Arrays.toString(solution.getState()));

			System.out.println("Greedy (ocena " + greedyResults.get(0).getSolution().getEvaluation() + "):");
			System.out.println(Arrays.toString(greedyResults.get(0).getSolution().getState()));
			System.out.println("Steepest (ocena " + steepestResults.get(0).getSolution().getEvaluation() + "):");
			System.out.println(Arrays.toString(steepestResults.get(0).getSolution().getState()));
			System.out.println("Heuristic (ocena " + heuristicResults.get(0).getSolution().getEvaluation() + "):");
			System.out.println(Arrays.toString(heuristicResults.get(0).getSolution().getState()));
			System.out.println("Random (ocena " + randomResults.get(0).getSolution().getEvaluation() + "):");
			System.out.println(Arrays.toString(randomResults.get(0).getSolution().getState()));

			DamerauLevenshteinAlgorithm dl = new DamerauLevenshteinAlgorithm(1, 1, 1, 1);
			List<Integer> optimumList = stateToIntegerList(solution.getState());
			System.out.println("Greedy DL: "
					+ dl.execute(optimumList, stateToIntegerList(greedyResults.get(0).getSolution().getState())));
			System.out.println("Steepest DL: "
					+ dl.execute(optimumList, stateToIntegerList(steepestResults.get(0).getSolution().getState())));
			System.out.println("Heuristic DL: "
					+ dl.execute(optimumList, stateToIntegerList(heuristicResults.get(0).getSolution().getState())));
			System.out.println("Random DL: "
					+ dl.execute(optimumList, stateToIntegerList(randomResults.get(0).getSolution().getState())));
		}
	}

	private List<Integer> stateToIntegerList(int[] state) {
		List<Integer> result = Lists.newArrayListWithCapacity(state.length);
		for (int value : state) {
			result.add(value);
		}
		return result;
	}
	
	public void runExperimentForExerciseTSSA() throws NumberFormatException, ParseException, IOException {

		final int executionNumber = 15;
		String[] problemNameList = {"bur26g", /*"esc16e", "lipa40b", "nug18", "sko100a", "tai80a", "wil100", "kra30a", "scr12", "sko81"*/};

		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
		AbstractAlgorithm random = new RandomAlgorithm();
		AbstractAlgorithm simpleHeuristic = new SimpleHeuristicAlgorithm();
		AbstractAlgorithm simAnnealing = new SimulatedAnnealingAlgorithm();
		AbstractAlgorithm tabuSearch = new TabuSearchAlgorithm();
		
		ExTSSAExperimentSaver exTSSAExperimentSaver = new ExTSSAExperimentSaver();
		
		for(String problemName : problemNameList){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + problemName);
			Problem problem = parseProblemFileFromResource("/"+problemName+".dat");
			Evaluator evaluator = new QapEvaluator(problem);

			AlgorithmRunSettings settings = new AlgorithmRunSettings(executionNumber, Long.MAX_VALUE);
			List<AlgorithmResult> greedyResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), greedy, evaluator,
					settings);
			List<AlgorithmResult> steepestResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), steepest,
					evaluator, settings);
			List<AlgorithmResult> simpleHeuristicResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), simpleHeuristic,
					evaluator, settings);
			
			long timeExecutionForRandom = 0;
			for(AlgorithmResult algoritmResult : greedyResults){
				timeExecutionForRandom += algoritmResult.getExecutionReport().getExecutionTime();
				exTSSAExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for(AlgorithmResult algoritmResult : steepestResults){
				timeExecutionForRandom += algoritmResult.getExecutionReport().getExecutionTime();
				exTSSAExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for(AlgorithmResult algoritmResult : simpleHeuristicResults){
				exTSSAExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			timeExecutionForRandom /= (greedyResults.size()+steepestResults.size());
			
			AlgorithmRunSettings randomSettings = new AlgorithmRunSettings(executionNumber, timeExecutionForRandom);
			List<AlgorithmResult> randomResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), random,
					evaluator, randomSettings);
			for(AlgorithmResult algoritmResult : randomResults){
				exTSSAExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			
			List<AlgorithmResult> simAnnelResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), simAnnealing,
					evaluator, settings);
			List<AlgorithmResult> tabuSearchResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), tabuSearch,
					evaluator, settings);
			
			for(AlgorithmResult algoritmResult : simAnnelResults){
				exTSSAExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for(AlgorithmResult algoritmResult : tabuSearchResults){
				exTSSAExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}

		}
		
		exTSSAExperimentSaver.saveFile("results/exerciseTSSA.csv");
		exTSSAExperimentSaver.saveAverageFile("results/exerciseAvgTSSA.csv");
	}
}
