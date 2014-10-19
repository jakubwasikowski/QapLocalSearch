package edu.mioib.qaplocalsearch.algorithm.neighboursgenerator;

import java.util.ArrayList;
import java.util.List;

import edu.mioib.qaplocalsearch.helper.ArraysUtil;

public class TwoOptNeighboursIterator implements NeighboursIterator {

	@Deprecated
	public List<int[]> generateAllNeighbours(int[] state) {
		List<int[]> result = new ArrayList<int[]>(getNeighboursNumber(state));
		for (int i = 0; i < state.length; i++) {
			for (int j = i + 1; j < state.length; j++) {
				ArraysUtil.swap(state, i, j);

				ArraysUtil.swap(state, i, j);
			}
		}

		return result;
	}

	@Override
	public int getNeighboursNumber(int[] state) {
		return (state.length * state.length - 1) / 2;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] next() {
		// TODO Auto-generated method stub
		return null;
	}
}
