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
	int iterationNumber;

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
		double temperatureThreshold = Double.MIN_VALUE;
		int markovLength = (int)Math.pow(startState.length,2)/2;
		int lCounter = markovLength;
		int lpCounter = iterationNumber * markovLength;

		neighbourIterator = new TwoOptStateHolder(currentState);
		long startBestEval = currentEvaluation;
		long startWorstEval = currentEvaluation;
		for (int i = 0; i < neighbourIterator.getNeighboursNumber(); i++) {
			neighbourIterator.nextNeighbour();
			long currNeighbourEval = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);
			if(currNeighbourEval < startBestEval){
				startBestEval = currNeighbourEval;
			}
			if(startWorstEval < currNeighbourEval){
				startWorstEval = currNeighbourEval;
			}
		}
		neighbourIterator.switchToOriginalState();
		temperature = (double)(currentEvaluation-startWorstEval)/Math.log(0.95);
		temperatureThreshold = (double)(currentEvaluation-startBestEval)/Math.log(0.01);
		
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
				lpCounter = iterationNumber * markovLength;
			} else {
				lCounter--;
			}
			
			if(lCounter <= 0){
				temperature *= coolingRate;
				lCounter = markovLength;
			}
			measurer.recordStep();
		} while(lpCounter>0 && temperature>temperatureThreshold);
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
