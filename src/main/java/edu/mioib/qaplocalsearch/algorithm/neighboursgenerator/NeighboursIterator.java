package edu.mioib.qaplocalsearch.algorithm.neighboursgenerator;


public interface NeighboursIterator {
	int[] getState();

	boolean hasNext();

	int[] next();

	int getNeighboursNumber();

	void saveCurrentNeighbourAsTheBest();

	int[] getTheBestNeighbour();
	
	boolean isTheBestExists();
}
