package edu.mioib.qaplocalsearch.algorithm;

import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptNeighboursIterator;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

public class SimulatedAnnealingAlgorithm implements Algorithm {

	@Override
	public Solution resolveProblem(Problem problem, Evaluator evaluator,
			int[] startState) {
		int[] currentState = startState;
		int currentEvaluation = evaluator.evaluateState(problem, currentState);

		TwoOptNeighboursIterator neighbourIterator;
		
        double temp = 10000;
        double coolingRate = 0.003;
		while(temp>1)
		{
			neighbourIterator = new TwoOptNeighboursIterator(currentState);
			
			while(neighbourIterator.hasNext()){
				int[] newState = neighbourIterator.next();
				int newStateEvaluation = evaluator.evaluateState(problem, newState);
				if(newStateEvaluation > currentEvaluation){
					currentState = newState;
					currentEvaluation = newStateEvaluation;
				}
			}
			temp *= 1-coolingRate;
		}
		return new Solution(currentEvaluation, currentState);
	}

	@Override
	public String getName() {
		return "Simulated Anneling";
	}
}
