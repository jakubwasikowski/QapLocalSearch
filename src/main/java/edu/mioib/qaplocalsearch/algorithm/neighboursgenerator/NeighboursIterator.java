package edu.mioib.qaplocalsearch.algorithm.neighboursgenerator;


public interface NeighboursIterator {
	void getState();

	boolean hasNext();

	void next();

	int getNeighboursNumber();

	void saveCurrentNeighbourAsTheBest();

	void getTheBestNeighbour();
	
	boolean isTheBestExists();
}
