package edu.mioib.qaplocalsearch.algorithm.neighboursgenerator;


public interface NeighboursIterator {
	boolean hasNext();

	int[] next();

	int getNeighboursNumber();
}
