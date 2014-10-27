package edu.mioib.qaplocalsearch.algorithm;

import java.util.Random;

import lombok.Value;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptNeighboursIterator;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

@Value
public class SimulatedAnnealingAlgorithm implements Algorithm {

	double startTemperature;
	double coolingRate;

	@Override
	public Solution resolveProblem(Problem problem, Evaluator evaluator,
			int[] currentState) {
		Random rand = new Random();

		int currentEvaluation = evaluator.evaluateState(problem, currentState);

		int bestEvaluation = currentEvaluation;
		int[] best = currentState.clone();

		TwoOptNeighboursIterator neighbourIterator;
		double temperature = startTemperature;
		while (temperature > 1) {
			neighbourIterator = new TwoOptNeighboursIterator(currentState);
			
			int randomNum = rand.nextInt(neighbourIterator.getNeighboursNumber());
			for (int i = 0; i < randomNum; i++) {
				neighbourIterator.next();
			}
			int newEvaluation = evaluator.evaluateState(problem, currentState);

			if (!(acceptanceProbability(currentEvaluation, newEvaluation, temperature) > Math.random())) {
				neighbourIterator.switchToOriginalState();
			}
			
			currentEvaluation = evaluator.evaluateState(problem, currentState);
			if (currentEvaluation < bestEvaluation) {
				best = currentState.clone();
				bestEvaluation = currentEvaluation;
			}
			
			temperature *= 1 - coolingRate;
		}
		return new Solution(bestEvaluation, best);
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
