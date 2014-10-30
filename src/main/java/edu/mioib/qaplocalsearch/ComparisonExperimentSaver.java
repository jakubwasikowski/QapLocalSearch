package edu.mioib.qaplocalsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.mioib.qaplocalsearch.model.AlgorithmResult;

public class ComparisonExperimentSaver {
	List<String[]> results;
	
	public ComparisonExperimentSaver() {
		results = new ArrayList<String[]>();
		
		String[] columnsNames = new String[9];
		columnsNames[0] = "Algorithm Name";
		columnsNames[1] = "Problem File Name";
		columnsNames[2] = "Problem Size";
		columnsNames[3] = "Function Value";
		columnsNames[4] = "Distance from optimum";
		columnsNames[5] = "Execution Time";
		columnsNames[6] = "Evaluated states number";
		columnsNames[7] = "Steps number";
		columnsNames[8] = "Localizations";
		
		results.add(columnsNames);
	}

	public void addExperimentResult(String problemName, AlgorithmResult algorithmResult) throws FileNotFoundException, IOException {
		int[] localizationsOrder = algorithmResult.getSolution().getState();
		int localizationsSize = localizationsOrder.length;
		String[] result = new String[localizationsSize+8];
		
		result[0] = algorithmResult.getAlgorithmName();
		result[1] = problemName;
		result[2] = String.valueOf(localizationsSize);
		result[3] = Integer.toString(algorithmResult.getSolution().getEvaluation());
		result[4] = Integer.toString(getSolutionValue(new FileInputStream(problemName+".sln")));
		result[5] = Long.toString(algorithmResult.getExecutionReport().getExecutionTime());
		result[6] = Integer.toString(algorithmResult.getExecutionReport().getEvaluatedStatesNumber());
		result[7] = Integer.toString(algorithmResult.getExecutionReport().getStepsNumber());
		
		for(int i=0; i<localizationsSize; i++){
			result[i + 8] = Integer.toString(localizationsOrder[i]);
		}
		
		results.add(result);
	}
	
	public void saveFile(String path) {
		try {
			File file = new File(path);
 
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for(String[] result: results){
				for(int i=0; i<result.length ;i++){
					writer.write(result[i]);
					if(i<result.length-2){
						writer.write(";");
					}
				}
				writer.newLine();
			}
			
			writer.flush();
			writer.close();
 
		} catch (IOException e) {
			e.printStackTrace();
			System.exit( 1 );
		}
	}
	
	public int getSolutionValue(InputStream inputStream) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			StringBuilder contentBuilder = new StringBuilder();
			String line = null;
			if((line = bufferedReader.readLine()) != null) {
				contentBuilder.append(line);
			}
			String[] solutionParts = contentBuilder.toString().trim().split("\\s+");
			
			return Integer.parseInt(solutionParts[1]);
		}
	}
}
