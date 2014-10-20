package edu.mioib.qaplocalsearch.algorithm.neighboursgenerator;


public interface NeighboursIterator {
	void switchToOriginalState();

	boolean hasNext();

	void next();

	int getNeighboursNumber();

	void saveCurrentNeighbourAsTheBest();

	void switchToTheBestNeighbour();
	
	boolean isTheBestExists();
}
