package edu.mioib.qaplocalsearch;

import static edu.mioib.qaplocalsearch.parser.ProblemParser.parseProblemFile;
import static edu.mioib.qaplocalsearch.parser.SolutionParser.parseSolutionFile;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.Solution;

public class QapEvaluatorUnitTest {
	private static final String PROBLEM_PATH = "/had12.dat";
	private static final String SOLUTION_PATH = "/had12.sln";

	private Problem problem;
	private Solution solution;
	private Evaluator evaluator;

	@Before
	public void setUp() throws NumberFormatException, IOException {
		evaluator = new QapEvaluator();
		problem = parseProblemFile(getClass().getResourceAsStream(PROBLEM_PATH));
		solution = parseSolutionFile(getClass().getResourceAsStream(SOLUTION_PATH));
	}

	@Test
	public void shouldReturnEvalTheSameAsInTheSolutionFile() throws NumberFormatException, IOException {
		int eval = evaluator.evaluateState(problem, solution.getLocationsOrder());
		int expectedEval = solution.getFunctionValue();
		assertEquals(expectedEval, eval);
	}

}
