package edu.mioib.qaplocalsearch.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import edu.mioib.qaplocalsearch.model.Problem;

public class ProblemParser {
	public static Problem parseProblemFileFromResource(String path) throws NumberFormatException, ParseException,
			IOException {
		return parseProblemFile(ProblemParser.class.getResourceAsStream(path));
	}

	public static Problem parseProblemFile(String path) throws NumberFormatException, FileNotFoundException,
			IOException, ParseException {
		return parseProblemFile(new FileInputStream(path));
	}

	public static Problem parseProblemFile(InputStream inputStream) throws NumberFormatException, IOException,
			ParseException {
		Problem result = null;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			StringBuilder contentBuilder = new StringBuilder();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				contentBuilder.append(line).append(" ");
			}
			String[] problemParts = contentBuilder.toString().trim().split("\\s+");
			
			int problemSize = Integer.parseInt(problemParts[0]);
			int[][] localizationMatrix = new int[problemSize][problemSize];
			int[][] facilitiesMatrix = new int[problemSize][problemSize];
			
			for (int i = 0; i < problemSize; i++) {
				for (int j = 0; j < problemSize; j++) {
					localizationMatrix[i][j] = Integer.parseInt(problemParts[i * problemSize + j + 1]);
					facilitiesMatrix[i][j] = Integer.parseInt(problemParts[i * problemSize + j + problemSize
							* problemSize + 1]);
				}
			}

			result = new Problem(problemSize, localizationMatrix, facilitiesMatrix);
		}
		return result;

	}
}
