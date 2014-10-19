package edu.mioib.qaplocalsearch.algorithm.helper;

import java.util.List;

public abstract class NeighboursGenerator {
	public abstract List<int[]> generateAllNeighbours(int[] state);
	
	public NeighboursIterator iterate() {
		return new NeighboursIterator();
	}

	protected abstract int[] generateNeighbourForIndex(int[] state, int index);

	private class NeighboursIterator {
		private int callIndex;

		private NeighboursIterator() {
			this.callIndex = 0;
		}

		int[] generateNeighbour() {

		}
	}
}
