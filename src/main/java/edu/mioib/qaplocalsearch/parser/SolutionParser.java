package edu.mioib.qaplocalsearch.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.mioib.qaplocalsearch.model.Solution;

public class SolutionParser {
	public static Solution parseSolutionFile(String path) throws FileNotFoundException, IOException {
		return parseSolutionFile(new FileInputStream(path));
	}

	public static Solution parseSolutionFile(InputStream inputStream) throws IOException {
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
		Solution result = new Solution(eval, locationsOrder);
		
		return result;
	}
}
