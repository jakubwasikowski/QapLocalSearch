package edu.mioib.qaplocalsearch.algorithm;

import java.util.Comparator;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import edu.mioib.qaplocalsearch.AlgorithmRunMeasurer;
import edu.mioib.qaplocalsearch.Evaluator;
import edu.mioib.qaplocalsearch.algorithm.neighboursgenerator.TwoOptStateHolder;
import edu.mioib.qaplocalsearch.helper.ArraysUtil;

@Value
@EqualsAndHashCode(callSuper = false)
public class TabuSearchAlgorithm extends AbstractAlgorithm {
	@Override
	public int[] resolveProblem(int[] startState, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		int problemSize = startState.length;
		int tabuSize = problemSize / 4;
		int eliteCandidatesNumber = problemSize / 10;
		long[][] tabu = initTabu(problemSize);
		
		long currentIteration = 0;
		long lastIterationWithImprovement = 0;
		int markowChainLength = problemSize;
		
		int[] currentState = startState;
		int[] bestState = currentState.clone();
		long bestEval = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);

		List<Move> candidates = null;
		
		while (!checkIfInterrupt(measurer) && (currentIteration - lastIterationWithImprovement) < (markowChainLength)) {
			if (candidates == null) {
				candidates = generateCandidates(currentState, evaluator, measurer, eliteCandidatesNumber);
			}

			Move minTabuCandIndex = null;
			Move chosenMove = null;
			for (Move cand : candidates) {
				int currCandIdx1 = cand.getIdx1();
				int currCandIdx2 = cand.getIdx2();
				if (cand.getEval() < bestEval
						|| !constainsMoveInTabu(tabu, tabuSize, currentIteration, currCandIdx1, currCandIdx2)) {
					ArraysUtil.swap(currentState, currCandIdx1, currCandIdx2);
					chosenMove = cand;
					break;
				}

				if (minTabuCandIndex == null
						|| tabu[currCandIdx1][currCandIdx2] < tabu[minTabuCandIndex.getIdx1()][minTabuCandIndex
								.getIdx2()]) {
					minTabuCandIndex = cand;
				}
			}

			if (chosenMove == null) {
				ArraysUtil.swap(currentState, minTabuCandIndex.getIdx1(), minTabuCandIndex.getIdx2());
				chosenMove = minTabuCandIndex;
				candidates = null;
			}

			addMoveToTabu(tabu, currentIteration, chosenMove.getIdx1(), chosenMove.getIdx2());

			long currentStateEval = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);
			if (currentStateEval < bestEval) {
				bestState = currentState.clone();
				bestEval = currentStateEval;
				
				lastIterationWithImprovement = currentIteration;
			}

			currentIteration++;
			measurer.recordStep();
		}
		
		return bestState;
	}

	private long[][] initTabu(int problemSize) {
		long[][] result = new long[problemSize][problemSize];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				result[i][j] = -1;
			}
		}
		return result;
	}

	private List<Move> generateCandidates(int[] currentState, Evaluator evaluator, AlgorithmRunMeasurer measurer,
			int eliteCandidatesNumber) {
		List<Move> candidates = Lists.newLinkedList();

		TwoOptStateHolder neighbourIterator = new TwoOptStateHolder(currentState);
		while (neighbourIterator.hasNextNeighbour()) {
			neighbourIterator.nextNeighbour();
			int idx1 = neighbourIterator.getIdx1();
			int idx2 = neighbourIterator.getIdx2();
			long stateEval = evaluateStateAndRecordEvaluation(evaluator, measurer, currentState);
			candidates.add(new Move(idx1, idx2, stateEval));
		}
		neighbourIterator.switchToOriginalState();
		
		return Ordering.from(new MoveComparator()).leastOf(FluentIterable.from(candidates), eliteCandidatesNumber);
	}

	@Override
	public String getName() {
		return "Tabu search";
	}

	private void addMoveToTabu(long[][] tabu, long currentIteration, int idx1, int idx2) {
		tabu[idx1][idx2] = currentIteration;
	}

	private boolean constainsMoveInTabu(long[][] tabu, int tabuSize, long currentIteration, int idx1, int idx2) {
		if (tabu[idx1][idx2] >= 0) {
			return tabu[idx1][idx2] + tabuSize > currentIteration;
		}
		return false;
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
