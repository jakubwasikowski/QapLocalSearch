package edu.mioib.qaplocalsearch.algorithm;

import java.util.Comparator;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptStateHolder;
import edu.mioib.qaplocalsearch.helper.ArraysUtil;

@Value
@EqualsAndHashCode(callSuper = false)
public class TabuSearchAlgorithm extends AbstractAlgorithm {

	int tabuSize;
	int eliteCandidatesNumber;
	int pValue = 10;

	@Override
	public int[] resolveProblem(int[] startState, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		int problemSize = startState.length;
		long[][] tabu = new long[problemSize][problemSize];
		
		long currentIteration = 0;
		long lastIterationWithImprovement = 0;
		int markowChainLength = problemSize * (problemSize - 1);
		
		int[] currentState = startState;
		int[] bestState = currentState.clone();
		long bestEval = evaluator.evaluateState(currentState);

		List<Move> candidates = null;
		
		while (!checkIfInterrupt(measurer)
				&& (currentIteration - lastIterationWithImprovement) < (markowChainLength * pValue)) {
			if (candidates == null) {
				candidates = generateCandidates(currentState, evaluator);
			}

			Move minTabuCandIndex = null;
			Move chosenMove = null;
			for (Move cand : candidates) {
				int currCandIdx1 = cand.getIdx1();
				int currCandIdx2 = cand.getIdx2();
				if (cand.getEval() < bestEval
						|| !constainsMoveInTabu(tabu, currentIteration, currCandIdx1, currCandIdx2)) {
					ArraysUtil.swap(currentState, currCandIdx1, currCandIdx2);
					chosenMove = cand;
					break;
				}

				int minTabuValueCandIdx1 = minTabuCandIndex.getIdx1();
				int minTabuValueCandIdx2 = minTabuCandIndex.getIdx2();
				if (minTabuCandIndex != null
						&& tabu[currCandIdx1][currCandIdx2] < tabu[minTabuValueCandIdx1][minTabuValueCandIdx2]) {
					minTabuCandIndex = cand;
				}
			}

			if (chosenMove == null) {
				ArraysUtil.swap(currentState, minTabuCandIndex.getIdx1(), minTabuCandIndex.getIdx2());
				chosenMove = minTabuCandIndex;
				candidates = null;
			}

			addMoveToTabu(tabu, currentIteration, chosenMove.getIdx1(), chosenMove.getIdx2());

			long currentStateEval = evaluator.evaluateState(currentState);
			if (currentStateEval < bestEval) {
				bestState = currentState.clone();
				bestEval = currentStateEval;
				
				lastIterationWithImprovement = currentIteration;
			}

			currentIteration++;
		}
		
		return bestState;
	}

	private List<Move> generateCandidates(int[] currentState, Evaluator evaluator) {
		List<Move> candidates = Lists.newArrayList();

		TwoOptStateHolder neighbourIterator = new TwoOptStateHolder(currentState);
		while (neighbourIterator.hasNextNeighbour()) {
			neighbourIterator.nextNeighbour();
			int idx1 = neighbourIterator.getIdx1();
			int idx2 = neighbourIterator.getIdx2();
			long stateEval = evaluator.evaluateState(currentState);
			candidates.add(new Move(idx1, idx2, stateEval));
		}
		neighbourIterator.switchToOriginalState();

		return FluentIterable.from(candidates).limit(eliteCandidatesNumber).toSortedList(new MoveComparator());
	}

	@Override
	public String getName() {
		return "Tabu search";
	}

	private void addMoveToTabu(long[][] tabu, long currentIteration, int idx1, int idx2) {
		tabu[idx1][idx2] = currentIteration;
	}

	private boolean constainsMoveInTabu(long[][] tabu, long currentIteration, int idx1, int idx2) {
		return tabu[idx1][idx2] + tabuSize > currentIteration;
	}

	@Value
	private static class Move {
		int idx1;
		int idx2;
		long eval;
	}

	private static class MoveComparator implements Comparator<Move> {

		@Override
		public int compare(Move move1, Move move2) {
			if (move1.eval < move2.eval) {
				return -1;
			}
			if (move1.eval > move2.eval) {
				return 1;
			}

			return 0;
		}

	}
}
