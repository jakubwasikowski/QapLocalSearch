package edu.mioib.qaplocalsearch.algorithm;

import java.util.Random;

import lombok.Value;
import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptStateHolder;

@Value
public class SimulatedAnnealingAlgorithm extends AbstractAlgorithm {

	double startTemperature;
	double coolingRate = 0.9;
	int iterationCounter = 10;

	@Override
	public int[] resolveProblem(int[] startState, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		Random rand = new Random();
		int[] currentState = startState;
		int currentEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);

		int bestEvaluation = currentEvaluation;
		int[] best = currentState.clone();

		TwoOptStateHolder neighbourIterator;
		double temperature = startTemperature;
		double counter = iterationCounter;
		while (temperature > 1 && counter>0/*!checkIfInterrupt(measurer)*/) {
			neighbourIterator = new TwoOptStateHolder(currentState);
			
			int randomNum = rand.nextInt(neighbourIterator.getNeighboursNumber());
			for (int i = 0; i < randomNum; i++) {
				neighbourIterator.nextNeighbour();
			}
			int newEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);

			if (!(acceptanceProbability(currentEvaluation, newEvaluation, temperature) > Math.random())) {
				neighbourIterator.switchToOriginalState();
			}
			
			currentEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);
			if (currentEvaluation < bestEvaluation) {
				best = currentState.clone();
				bestEvaluation = currentEvaluation;
			}
			
			temperature *= coolingRate;
			counter--;

			measurer.recordStep();
		}
		return best;
	}

	@Override
	public String getName() {
		return "Simulated Anneling";
	}

	private double acceptanceProbability(int currentEval, int newEval, double temperature) {
		if (newEval < currentEval) {
			return 1.0;
		}
		return Math.exp((currentEval - newEval) / temperature);
	}
}
