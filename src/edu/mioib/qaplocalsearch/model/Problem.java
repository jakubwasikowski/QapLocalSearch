package edu.mioib.qaplocalsearch.model;

import lombok.Value;

@Value
public class Problem {
	int problemSize;
	int[][] localisations;
	int[][] solution;
}
