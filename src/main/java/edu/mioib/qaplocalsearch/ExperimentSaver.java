package edu.mioib.qaplocalsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.mioib.qaplocalsearch.model.AlgorithmResult;

public class ExperimentSaver {
	List<String[]> results;
	
	public ExperimentSaver() {
		results = new ArrayList<String[]>();
		
		String[] columnsNames = new String[5];
		columnsNames[0] = "Algorithm Name";
		columnsNames[1] = "Problem File Name";
		columnsNames[2] = "Function Value";
		columnsNames[3] = "Execution Time";
		columnsNames[4] = "Evaluated states number";
		columnsNames[5] = "Steps number";
		columnsNames[6] = "Localizations";
		
		results.add(columnsNames);
	}

	public void addExperimentResult(String problemName, AlgorithmResult algorithmResult) {
		int[] localizationsOrder = algorithmResult.getSolution().getState();
		int localizationsSize = localizationsOrder.length;
		String[] result = new String[localizationsSize+6];
		
		result[0] = algorithmResult.getAlgorithmName();
		result[1] = problemName;
		result[2] = Integer.toString(algorithmResult.getSolution().getEvaluation());
		result[3] = Long.toString(algorithmResult.getExecutionReport().getExecutionTime());
		result[4] = Integer.toString(algorithmResult.getExecutionReport().getEvaluatedStatesNumber());
		result[5] = Integer.toString(algorithmResult.getExecutionReport().getStepsNumber());
		
		for(int i=0; i<localizationsSize; i++){
			result[i + 6] = Integer.toString(localizationsOrder[i]);
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
}
