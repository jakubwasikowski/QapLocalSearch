package edu.mioib.qaplocalsearch.algorithm.neighboursgenerator;

import java.util.List;

public interface NeighboursGenerator {
	List<int[]> generateAllNeighbours(int[] state);

	NeighboursIterator iterator(int[] state);

	int getNeighboursNumber(int[] state);

	public interface NeighboursIterator {
		boolean hasNext();

		int[] next();
	}
}
