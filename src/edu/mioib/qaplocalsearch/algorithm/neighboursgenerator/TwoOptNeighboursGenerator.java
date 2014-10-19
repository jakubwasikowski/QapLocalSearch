package edu.mioib.qaplocalsearch.algorithm.neighboursgenerator;

import java.util.ArrayList;
import java.util.List;

public class TwoOptNeighboursGenerator implements NeighboursGenerator {

	@Override
	public List<int[]> generateAllNeighbours(int[] state) {
		List<int[]> result = new ArrayList<int[]>();
		for (int i = 0; i < state.length; i++) {
			for (int j = i + 1; j < state.length; j++) {
			}
		}

		return result;
	}

	@Override
	public NeighboursIterator iterator(int[] state) {
		// TODO Auto-generated method stub
		return null;
	}

	public class TwoOptNeighboursIterator implements NeighboursIterator {
		private TwoOptNeighboursIterator() {
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

}
