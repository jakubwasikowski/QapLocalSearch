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
		columnsNames[4] = "Localizations";
		
		results.add(columnsNames);
	}

	void addExperimentResult(String problemName, AlgorithmResult algorithmResult){
		int[] localizationsOrder = algorithmResult.getSolution().getLocationsOrder();
		int localizationsSize = localizationsOrder.length;
		String[] result = new String[localizationsSize+4];
		
		result[0] = algorithmResult.getAlgorithmName();
		result[1] = problemName;
		result[2] = Integer.toString(algorithmResult.getSolution().getFunctionValue());
		result[3] = Long.toString(algorithmResult.getExecutionTime());
		
		for(int i=0; i<localizationsSize; i++){
			result[i+4] = Integer.toString(localizationsOrder[i]);
		}
		
		results.add(result);
	}
	
	void saveFile(String path){
		try {
			File file = new File(path);
 
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for(String[] result: results){
				for(String elem : result){
					writer.write(elem + ";");
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
