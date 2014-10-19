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
	private enum ParseState {
		PROBLEM_SIZE, PROBLEM_SIZE_SPACE, LOCALIZATIONS, LOCALIZATIONS_SPACE, FACILITIES, END
	}
	
	public static Problem parseProblemFile(String path) throws NumberFormatException, FileNotFoundException,
			IOException, ParseException {
		return parseProblemFile(new FileInputStream(path));
	}

	public static Problem parseProblemFile(InputStream inputStream) throws NumberFormatException, IOException,
			ParseException {
		Problem result = null;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line = null;
			ParseState state = ParseState.PROBLEM_SIZE;
			int problemSize = 0;
			int[][] localizationMatrix = null;
			int[][] facilitiesMatrix = null;
			int matrixCounter = 0;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				if (state == ParseState.PROBLEM_SIZE) {
					problemSize = Integer.parseInt(line);
					localizationMatrix = new int[problemSize][problemSize];
					facilitiesMatrix = new int[problemSize][problemSize];
					state = ParseState.PROBLEM_SIZE_SPACE;
				} else if (state == ParseState.PROBLEM_SIZE_SPACE) {
					if (!line.isEmpty()) {
						throw new ParseException();
					}
					state = ParseState.LOCALIZATIONS;
				} else if (state == ParseState.LOCALIZATIONS) {
					String[] matrixElems = line.split("\\s+");
					for(int i=0; i<matrixElems.length; i++){
						localizationMatrix[matrixCounter][i] = Integer.parseInt(matrixElems[i]);
					}
					matrixCounter++;
					if (matrixCounter == problemSize) {
						state = ParseState.LOCALIZATIONS_SPACE;
					}
				} else if (state == ParseState.LOCALIZATIONS_SPACE) {
					if (!line.isEmpty()) {
						throw new ParseException();
					}
					matrixCounter = 0;
					state = ParseState.FACILITIES;
				} else if (state == ParseState.FACILITIES) {
					String[] matrixElems = line.split("\\s+");
					for (int i = 0; i < matrixElems.length; i++) {
						facilitiesMatrix[matrixCounter][i] = Integer.parseInt(matrixElems[i]);
					}
					matrixCounter++;
					if (matrixCounter == problemSize) {
						state = ParseState.END;
					}
				}
			}
			result = new Problem(problemSize, localizationMatrix, facilitiesMatrix);
		}
		return result;

	}
}
