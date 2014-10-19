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

	public TwoOptNeighboursIterator(int[] state) {
		this.state = state;
		this.idx1 = this.idx2 = 0;
		this.neighboursNumber = (state.length * (state.length - 1)) / 2;
	}

	@Override
	public boolean hasNext() {
		return !(idx1 >= state.length - 2 && idx2 >= state.length - 1);
	}

	@Override
	public int[] next() {
		if (!(idx1 == 0 && idx2 == 0)) {
			ArraysUtil.swap(state, idx1, idx2);
		}
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
}
