package edu.mioib.qaplocalsearch;

import static edu.mioib.qaplocalsearch.parser.ProblemParser.parseProblemFileFromResource;

import java.io.IOException;
import java.util.List;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import edu.mioib.qaplocalsearch.algorithm.AbstractAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.GreedyAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.RandomAlgorithm;
import edu.mioib.qaplocalsearch.algorithm.SimpleHeuristicAlgorithm;
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
	
	public void runExperimentForExercise2() throws NumberFormatException, ParseException, IOException {
		String[] problemNameList = {"bur26g", "esc16e", "lipa40b", "nug18", "sko100a", "tai80a", "wil100", "kra30a", "scr12", "sko81"};

		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
		AbstractAlgorithm random = new RandomAlgorithm();
		AbstractAlgorithm simpleHeuristic = new SimpleHeuristicAlgorithm();
		
		Ex2ExperimentSaver comparisonExperimentSaver = new Ex2ExperimentSaver();
		
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
			
			AlgorithmRunSettings randomSettings = new AlgorithmRunSettings(15, timeExecutionForRandom);
			List<AlgorithmResult> randomResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), random,
					evaluator, randomSettings);
			for(AlgorithmResult algoritmResult : randomResults){
				comparisonExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
		}
		
		comparisonExperimentSaver.saveFile("ex2.csv");
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
	
	public void runExperimentForExercise4() throws NumberFormatException, ParseException, IOException {
		String[] problemNameList = {"wil100", "esc16e", "lipa40b"};
		
		AbstractAlgorithm greedy = new GreedyAlgorithm();
		AbstractAlgorithm steepest = new SteepestAlgorithm();
		
		Ex4ExperimentSaver comparisonExperimentSaver = new Ex4ExperimentSaver();
		
		for(String problemName : problemNameList){
			Problem problem = parseProblemFileFromResource("/"+problemName+".dat");
			Evaluator evaluator = new QapEvaluator(problem);
			AlgorithmRunSettings settings = new AlgorithmRunSettings(400, 1000000);
			List<AlgorithmResult> greedyResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), greedy, evaluator,
					settings);
			List<AlgorithmResult> steepestResults = algorithmRunner.runAlgorithm(problem.getProblemSize(), steepest,
					evaluator, settings);
			
			for(AlgorithmResult algoritmResult : greedyResults){
				comparisonExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
			for(AlgorithmResult algoritmResult : steepestResults){
				comparisonExperimentSaver.addExperimentResult(problemName, algoritmResult);
			}
		}
		comparisonExperimentSaver.saveFile("ex4.csv");
	}
}
