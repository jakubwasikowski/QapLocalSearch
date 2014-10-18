package edu.mioib.qaplocalsearch.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import edu.mioib.qaplocalsearch.model.Problem;

public class ProblemParser {
	public static Problem parseProblemFile(String path) {
		
		Problem result = null;
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
			String line = null;
			int problemState = 0;
			int problemSize = 0;
			int[][] localizationMatrix = null;
			int[][] facilitiesMatrix = null;
			int matrixCounter = 0;
			while ((line = bufferedReader.readLine()) != null) {
				if (problemState == 0) {
					problemSize = Integer.parseInt(line);
					localizationMatrix = new int[problemSize][problemSize];
					facilitiesMatrix = new int[problemSize][problemSize];
					problemState++;
				} else if (problemState==1 && !line.trim().isEmpty()) {
					String[] matrixElems = line.trim().split(" ");
					for(int i=0; i<matrixElems.length; i++){
						localizationMatrix[matrixCounter][i] = Integer.parseInt(matrixElems[i]);
					}
					matrixCounter++;
				} else if (problemState==1 && line.trim().isEmpty()){
					problemState++;
					matrixCounter = 0;
				} else if(problemState==2 && !line.trim().isEmpty()){
					String[] matrixElems = line.trim().split(" ");
					for(int i=0; i<matrixElems.length; i++){
						facilitiesMatrix[matrixCounter][i] = Integer.parseInt(matrixElems[i]);
					}
					matrixCounter++;
				}
			}
			result = new Problem(problemSize, localizationMatrix, facilitiesMatrix);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return result;

	}
}
