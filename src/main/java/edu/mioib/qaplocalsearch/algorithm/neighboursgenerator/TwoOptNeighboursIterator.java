package edu.mioib.qaplocalsearch.algorithm.neighboursgenerator;

import java.util.NoSuchElementException;

import lombok.Getter;
import edu.mioib.qaplocalsearch.helper.ArraysUtil;

public class TwoOptNeighboursIterator implements NeighboursIterator {

	@Getter
	private int neighboursNumber;
	private int[] state;
	
	private int idx1;
	private int idx2;
	
	private int idx1Best;
	private int idx2Best;

	private StateType lastGotState;

	private enum StateType {
		ORIGINAL_STATE, NEXT_NEIGHBOUR, THE_BEST_NEIGHBOUR
	}

	public TwoOptNeighboursIterator(int[] state) {
		this.state = state;
		this.idx1 = this.idx2 = 0;
		this.idx1Best = this.idx2Best = 0;
		this.lastGotState = StateType.ORIGINAL_STATE;
		this.neighboursNumber = (state.length * (state.length - 1)) / 2;
	}

	@Override
	public int[] getState() {
		if (lastGotState != StateType.ORIGINAL_STATE) {
			if (lastGotState == StateType.THE_BEST_NEIGHBOUR) {
				ArraysUtil.swap(state, idx1Best, idx2Best);
			} else if (!(idx1 == 0 && idx2 == 0)) {
				ArraysUtil.swap(state, idx1, idx2);
			}
			lastGotState = StateType.ORIGINAL_STATE;
		}

		return state;
	}

	@Override
	public boolean hasNext() {
		return !(idx1 >= state.length - 2 && idx2 >= state.length - 1);
	}

	@Override
	public int[] next() {
		if (lastGotState != StateType.ORIGINAL_STATE) {
			if (lastGotState == StateType.THE_BEST_NEIGHBOUR) {
				ArraysUtil.swap(state, idx1Best, idx2Best);
			} else if (!(idx1 == 0 && idx2 == 0)) {
				ArraysUtil.swap(state, idx1, idx2);
			}
		}
		lastGotState = StateType.NEXT_NEIGHBOUR;

		setNextIndexes();
		ArraysUtil.swap(state, idx1, idx2);
		return state;
	}

	private void setNextIndexes() {
		if (idx1 >= state.length - 2 && idx2 >= state.length - 1) {
			throw new NoSuchElementException();
		}

		if (idx2 == state.length - 1) {
			idx1++;
			idx2 = idx1 + 1;
		} else {
			idx2++;
		}
	}

	@Override
	public void saveCurrentNeighbourAsTheBest() {
		if (idx1 == 0 && idx2 == 0) {
			throw new IllegalStateException();
		}
		idx1Best = idx1;
		idx2Best = idx2;
	}

	@Override
	public int[] getTheBestNeighbour() {
		if (idx1Best == 0 && idx2Best == 0) {
			throw new IllegalStateException();
		}
		if (lastGotState != StateType.THE_BEST_NEIGHBOUR) {
			if (lastGotState == StateType.NEXT_NEIGHBOUR) {
				ArraysUtil.swap(state, idx1, idx2);
			}
			ArraysUtil.swap(state, idx1Best, idx2Best);
			lastGotState = StateType.THE_BEST_NEIGHBOUR;
		}
		return state;
	}
}
