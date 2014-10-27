package edu.mioib.qaplocalsearch.algorithm;

import java.util.Random;

import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptStateHolder;
import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

public class RandomAlgorithm extends AbstractAlgorithm {

	@Override
	public Solution resolveProblem(Problem problem, Evaluator evaluator, int[] currentState,
			AlgorithmRunMeasurer measurer) {

		int currentEvaluation = evaluator.evaluateState(problem, currentState);
		boolean currentStateChanged;
		
		TwoOptStateHolder neighbourIterator;
		do{
			neighbourIterator = new TwoOptStateHolder(currentState);
			currentStateChanged = false;
			int neighboursNumber = neighbourIterator.getNeighboursNumber();
			
			Random rand = new Random();		
			for(int i=0; i<neighboursNumber; i++){
				int randomNum = rand.nextInt(neighboursNumber-i);
				for(int j=0; j<randomNum; j++){
					neighbourIterator.nextNeighbour();
				}
				int newStateEvaluation = evaluator.evaluateState(problem, currentState);

				if(newStateEvaluation > currentEvaluation){
					neighbourIterator.saveCurrentNeighbourAsTheBest();
					neighbourIterator.switchToTheBestNeighbour();
					currentEvaluation = newStateEvaluation;
					currentStateChanged = true;
					break;
				}
				else{
					neighbourIterator.switchToOriginalState();
				}
			}			
		} while(currentStateChanged);
		
		return new Solution(currentEvaluation, currentState);
	}

	@Override
	public String getName() {
		return "Random";
	}
}
