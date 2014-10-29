package edu.mioib.qaplocalsearch.algorithm;

import static edu.mioib.qaplocalsearch.helper.ArraysUtil.generateRandomPerm;

import java.util.Random;

import lombok.Value;
import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptStateHolder;

@Value
public class SimulatedAnnealingAlgorithm extends AbstractAlgorithm {

	double startTemperature;
	double coolingRate;

	@Override
	public int[] resolveProblem(int permSize, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		Random rand = new Random();
		int[] currentState = generateRandomPerm(permSize);
		int currentEvaluation = evaluator.evaluateState(currentState);

		int bestEvaluation = currentEvaluation;
		int[] best = currentState.clone();

		TwoOptStateHolder neighbourIterator;
		double temperature = startTemperature;
		while (temperature > 1 && !checkIfInterrupt(measurer)) {
			neighbourIterator = new TwoOptStateHolder(currentState);
			
			int randomNum = rand.nextInt(neighbourIterator.getNeighboursNumber());
			for (int i = 0; i < randomNum; i++) {
				neighbourIterator.nextNeighbour();
			}
			int newEvaluation = evaluator.evaluateState(currentState);

			if (!(acceptanceProbability(currentEvaluation, newEvaluation, temperature) > Math.random())) {
				neighbourIterator.switchToOriginalState();
			}
			
			currentEvaluation = evaluator.evaluateState(currentState);
			if (currentEvaluation < bestEvaluation) {
				best = currentState.clone();
				bestEvaluation = currentEvaluation;
			}
			
			temperature *= 1 - coolingRate;
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
