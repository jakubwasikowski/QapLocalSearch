package edu.mioib.qaplocalsearch.algorithm.neighboursgenerator;


public interface StateHolder {
	void switchToOriginalState();

	boolean hasNextNeighbour();

	void nextNeighbour();

	int getNeighboursNumber();

	void saveCurrentNeighbourAsTheBest();

	void switchToTheBestNeighbour();
	
	boolean isTheBestExists();

	int getIdx1();

	int getIdx2();
}
