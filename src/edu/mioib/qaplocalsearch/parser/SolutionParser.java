package edu.mioib.qaplocalsearch.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import edu.mioib.qaplocalsearch.model.Solution;

public class SolutionParser {
	private static List<Integer> solution;

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
		line.trim();
		String[] solutionPart = line.split(" ");
		solution = null;
		for(int i=2; i<solutionPart.length; i++){
			solution.add(Integer.parseInt(solutionPart[i]));
		}
		Solution result = new Solution(Integer.parseInt(solutionPart[0]), Integer.parseInt(solutionPart[1]), solution);
		
		return result;
	}
}
