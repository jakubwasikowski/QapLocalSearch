package edu.mioib.qaplocalsearch.model;

import java.util.List;

import lombok.Value;

@Value
public class Solution {
	int solutionSize;
	int functionValue;
	List<Integer> solution;
}
