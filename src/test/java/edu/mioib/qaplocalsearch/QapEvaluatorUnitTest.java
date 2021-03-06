package edu.mioib.qaplocalsearch;

import static edu.mioib.qaplocalsearch.parser.ProblemParser.parseProblemFile;
import static edu.mioib.qaplocalsearch.parser.SolutionParser.parseSolutionFile;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import lombok.Value;

import org.junit.Test;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import edu.mioib.qaplocalsearch.model.Problem;
import edu.mioib.qaplocalsearch.model.StateEvaluation;

public class QapEvaluatorUnitTest {

	private Evaluator evaluator;

	@Test
	public void shouldReturnEvalTheSameAsInTheSolutionOfBur26a() throws NumberFormatException, IOException,
			ParseException {
		ExperimentResult expResult = getExperimentResult("/bur26a.dat", "/bur26a.sln");
		evaluator = new QapEvaluator(expResult.getProblem());
		long eval = evaluator.evaluateState(expResult.getSolution().getState());
		long expectedEval = expResult.getSolution().getEvaluation();
		assertEquals(expectedEval, eval);
	}

	@Test
	public void shouldReturnEvalTheSameAsInTheSolutionOfchr12a() throws ParseException, IOException {
		ExperimentResult expResult = getExperimentResult("/chr12a.dat", "/chr12a.sln");
		evaluator = new QapEvaluator(expResult.getProblem());
		long eval = evaluator.evaluateState(expResult.getSolution().getState());
		long expectedEval = expResult.getSolution().getEvaluation();
		assertEquals(expectedEval, eval);
	}

	@Test
	public void shouldReturnEvalTheSameAsInTheSolutionOfHad12() throws NumberFormatException, IOException,
			ParseException {
		ExperimentResult expResult = getExperimentResult("/had12.dat", "/had12.sln");
		evaluator = new QapEvaluator(expResult.getProblem());
		long eval = evaluator.evaluateState(expResult.getSolution().getState());
		long expectedEval = expResult.getSolution().getEvaluation();
		assertEquals(expectedEval, eval);
	}

	@Test
	public void shouldReturnEvalTheSameAsInTheSolutionOfLipa90b() throws NumberFormatException, IOException,
			ParseException {
		ExperimentResult expResult = getExperimentResult("/lipa90b.dat", "/lipa90b.sln");
		evaluator = new QapEvaluator(expResult.getProblem());
		long eval = evaluator.evaluateState(expResult.getSolution().getState());
		long expectedEval = expResult.getSolution().getEvaluation();
		assertEquals(expectedEval, eval);
	}

	@Test
	public void shouldReturnEvalTheSameAsInTheSolutionOfScr20() throws NumberFormatException, IOException,
			ParseException {
		ExperimentResult expResult = getExperimentResult("/scr20.dat", "/scr20.sln");
		evaluator = new QapEvaluator(expResult.getProblem());
		long eval = evaluator.evaluateState(expResult.getSolution().getState());
		long expectedEval = expResult.getSolution().getEvaluation();
		assertEquals(expectedEval, eval);
	}

	@Test
	public void shouldReturnEvalTheSameAsInTheSolutionOfSko100a() throws NumberFormatException, IOException,
			ParseException {
		ExperimentResult expResult = getExperimentResult("/sko100a.dat", "/sko100a.sln");
		evaluator = new QapEvaluator(expResult.getProblem());
		long eval = evaluator.evaluateState(expResult.getSolution().getState());
		long expectedEval = expResult.getSolution().getEvaluation();
		assertEquals(expectedEval, eval);
	}

	@Test
	public void shouldReturnEvalTheSameAsInTheSolutionOfTai40b() throws NumberFormatException, IOException,
			ParseException {
		ExperimentResult expResult = getExperimentResult("/tai40b.dat", "/tai40b.sln");
		evaluator = new QapEvaluator(expResult.getProblem());
		long eval = evaluator.evaluateState(expResult.getSolution().getState());
		long expectedEval = expResult.getSolution().getEvaluation();
		assertEquals(expectedEval, eval);
	}

	@Test
	public void shouldReturnEvalTheSameAsInTheSolutionOfWil100() throws NumberFormatException, IOException,
			ParseException {
		ExperimentResult expResult = getExperimentResult("/wil100.dat", "/wil100.sln");
		evaluator = new QapEvaluator(expResult.getProblem());
		long eval = evaluator.evaluateState(expResult.getSolution().getState());
		long expectedEval = expResult.getSolution().getEvaluation();
		assertEquals(expectedEval, eval);
	}

	private ExperimentResult getExperimentResult(String problemPath, String solutionPath) throws IOException, ParseException {
		Problem problem = parseProblemFile(getClass().getResourceAsStream(problemPath));
		StateEvaluation solution = parseSolutionFile(getClass().getResourceAsStream(solutionPath));
			
		return new ExperimentResult(problem, solution);
	}

	@Value
	private static class ExperimentResult {
		Problem problem;
		StateEvaluation solution;
	}
}
