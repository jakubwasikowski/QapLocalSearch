package edu.mioib.qaplocalsearch.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import edu.mioib.qaplocalsearch.model.StateEvaluation;

public class SolutionParser {
	public static StateEvaluation parseSolutionFileFromResource(String path) throws NumberFormatException,
			ParseException, IOException {
		return parseSolutionFile(SolutionParser.class.getResourceAsStream(path));
	}

	public static StateEvaluation parseSolutionFile(String path) throws FileNotFoundException, IOException {
		return parseSolutionFile(new FileInputStream(path));
	}

	public static StateEvaluation parseSolutionFile(InputStream inputStream) throws IOException {
		StringBuilder contentBuilder = new StringBuilder();
		String line = null;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			while ((line = bufferedReader.readLine()) != null) {
				contentBuilder.append(line).append(" ");
			}
		}
		String[] solutionParts = contentBuilder.toString().trim().split("\\s+");

		int locationsSize = Integer.parseInt(solutionParts[0]);
		int eval = Integer.parseInt(solutionParts[1]);

		int[] locationsOrder = new int[locationsSize];
		for(int i=2; i<solutionParts.length; i++){
			locationsOrder[i - 2] = Integer.parseInt(solutionParts[i]);
		}
		StateEvaluation result = new StateEvaluation(eval, locationsOrder);
		
		return result;
	}
}
