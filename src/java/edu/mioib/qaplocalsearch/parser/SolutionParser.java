package edu.mioib.qaplocalsearch.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import edu.mioib.qaplocalsearch.model.Solution;

public class SolutionParser {
	public static Solution parseSolutionFile(String path) {

		String line = "";
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
			while ((line = bufferedReader.readLine()) != null) {
				line += line;
				line += " ";
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		String[] solutionPart = line.trim().split("\\s");

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
