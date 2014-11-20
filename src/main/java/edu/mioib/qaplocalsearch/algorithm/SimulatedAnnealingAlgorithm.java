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

	double startTemperature;
	double coolingRate;
	int iterationCounter;

	public SimulatedAnnealingAlgorithm(double startTemperature) {
		this(startTemperature, 0.9, 10);
	}

	@Override
	public int[] resolveProblem(int[] startState, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		Random rand = new Random();
		int[] currentState = startState;
		long currentEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);

		long bestEvaluation = currentEvaluation;
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
			long newEvaluation = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);

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

	private double acceptanceProbability(long currentEval, long newEval, double temperature) {
		if (newEval < currentEval) {
			return 1.0;
		}
		return Math.exp((currentEval - newEval) / temperature);
	}
}
