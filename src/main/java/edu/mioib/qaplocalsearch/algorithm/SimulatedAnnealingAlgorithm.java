package edu.mioib.qaplocalsearch.algorithm;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptStateHolder;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SimulatedAnnealingAlgorithm extends AbstractAlgorithm {

	double coolingRate;
	int iterationCounter;

	public SimulatedAnnealingAlgorithm() {
		this (0.9, 10);
	}

	@Override
	public int[] resolveProblem(int[] startState, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		Random rand = new Random();
		int[] currentState = startState;
		long currentEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);

		long bestEvaluation = currentEvaluation;
		int[] best = currentState.clone();

		TwoOptStateHolder neighbourIterator;
		double temperature = Double.MAX_VALUE;
		double temperatureTreshold = Double.MIN_VALUE;
		int markovLength = startState.length^2/2;
		int lCounter = markovLength;
		int lpCounter = iterationCounter * markovLength;

		neighbourIterator = new TwoOptStateHolder(currentState);
		double startCurrEval = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);
		double startBestEval = startCurrEval;
		double startWorstEval = startCurrEval;
		for (int i = 0; i < neighbourIterator.getNeighboursNumber(); i++) {
			neighbourIterator.nextNeighbour();
			double temp = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);
			if(temp < startBestEval){
				startBestEval = temp;
			}
			if(startWorstEval < temp){
				startWorstEval = temp;
			}
		}
		neighbourIterator.switchToOriginalState();
		temperature = (startCurrEval-startWorstEval)/Math.log(0.95);
		temperatureTreshold = (startCurrEval-startBestEval)/Math.log(0.01);
		
		do{
			lpCounter--;
			neighbourIterator = new TwoOptStateHolder(currentState);
			
			int randomNum = rand.nextInt(neighbourIterator.getNeighboursNumber());
			for (int i = 0; i < randomNum; i++) {
				neighbourIterator.nextNeighbour();
			}
			long newEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);
			
			if (!(acceptanceProbability(currentEvaluation, newEvaluation, temperature) > Math.random())) {
				neighbourIterator.switchToOriginalState();
			}
			
			currentEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);
			if (currentEvaluation < bestEvaluation) {
				best = currentState.clone();
				bestEvaluation = currentEvaluation;
				lCounter = 0;
				lpCounter = iterationCounter * markovLength;
			} else {
				lCounter--;
			}
			
			if(lCounter == 0){
				temperature *= coolingRate;
				lCounter = markovLength;
			}
			measurer.recordStep();
		} while(lpCounter>0 && temperature>temperatureTreshold);
		return best;
	}

	@Override
	public String getName() {
		return "Simulated Anneling";
	}

	private double acceptanceProbability(long currentEval, long newEval, double temperature) {
		if (newEval < currentEval) {
			return 1.0;
		}
		return Math.exp((currentEval - newEval) / temperature);
	}
}
