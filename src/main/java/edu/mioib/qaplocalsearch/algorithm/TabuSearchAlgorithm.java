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

@Value
@EqualsAndHashCode(callSuper = false)
public class TabuSearchAlgorithm extends AbstractAlgorithm {

	int tabuSize;
	int eliteCandidatesNumber;

	@Override
	public int[] resolveProblem(int[] startState, Evaluator evaluator, AlgorithmRunMeasurer measurer) {
		int problemSize = startState.length;
		int[] currentState = startState;
		int[][] tabu = new int[problemSize][problemSize];
		int currentIteration = 0;

		while (!checkIfInterrupt(measurer) /* TODO zajebistszy warunek stopu */) {
			List<Move> candidates = generateCandidates(currentState, evaluator, tabu, currentIteration);

			for (Move candidate : candidates) {

			}
		}
	}

	private List<Move> generateCandidates(int[] currentState, Evaluator evaluator, int[][] tabu, int currentIteration) {
		List<Move> candidates = Lists.newArrayList();
		
		TwoOptStateHolder neighbourIterator = new TwoOptStateHolder(currentState);
		while (neighbourIterator.hasNextNeighbour()) {
			neighbourIterator.nextNeighbour();
			int idx1 = neighbourIterator.getIdx1();
			int idx2 = neighbourIterator.getIdx2();
			if (!constainsTabuMove(tabu, currentIteration, idx1, idx2)) {
				int stateEval = evaluator.evaluateState(currentState);
				candidates.add(new Move(idx1, idx2, stateEval));
			}
		}
		neighbourIterator.switchToOriginalState();

		return FluentIterable.from(candidates).limit(eliteCandidatesNumber).toSortedList(new MoveComparator());
	}

	@Override
	public String getName() {
		return "Tabu search";
	}

	private void addMoveToTabu(int[][] tabu, int currentIteration, int idx1, int idx2) {
		tabu[idx1][idx2] = currentIteration;
	}

	private boolean constainsTabuMove(int[][] tabu, int currentIteration, int idx1, int idx2) {
		return tabu[idx1][idx2] + tabuSize > currentIteration;
	}

	@Value
	private static class Move {
		int idx1;
		int idx2;
		int eval;
	}

	private static class MoveComparator implements Comparator<Move> {

		@Override
		public int compare(Move move1, Move move2) {
			return move1.eval - move2.eval;
		}

	}
}
