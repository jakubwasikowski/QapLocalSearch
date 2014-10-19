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
		String content = "";
		String line = null;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			while ((line = bufferedReader.readLine()) != null) {
				content += line;
				content += " ";
			}
		}
		String[] solutionPart = content.trim().split("\\s+");

		int locationsSize = Integer.parseInt(solutionPart[0]);
		int eval = Integer.parseInt(solutionPart[1]);

		int[] locationsOrder = new int[locationsSize];
		for(int i=2; i<solutionPart.length; i++){
			locationsOrder[i - 2] = Integer.parseInt(solutionPart[i]);
		}
		Solution result = new Solution(eval, locationsOrder);
		
		return result;
	}
}
