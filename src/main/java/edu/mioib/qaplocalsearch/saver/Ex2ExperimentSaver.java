package edu.mioib.qaplocalsearch.saver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import edu.mioib.qaplocalsearch.model.AlgorithmResult;
import edu.mioib.qaplocalsearch.model.StateEvaluation;
import edu.mioib.qaplocalsearch.parser.SolutionParser;

//TODO refactor this class
public class Ex2ExperimentSaver {
	List<String[]> results;
	
	public Ex2ExperimentSaver() {
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
		columnsNames[8] = "Localisations";
		
		results.add(columnsNames);
	}

	public void addExperimentResult(String problemName, AlgorithmResult algorithmResult) throws FileNotFoundException, IOException, NumberFormatException, ParseException {
		int[] localisationsOrder = algorithmResult.getSolution().getState();
		int localisationsSize = localisationsOrder.length;
		String[] result = new String[localisationsSize+8];
		
		SolutionParser solutionParser = new SolutionParser();
		StateEvaluation solution = solutionParser.parseSolutionFileFromResource("/"+problemName+".sln");
		
		result[0] = algorithmResult.getAlgorithmName();
		result[1] = problemName;
		result[2] = String.valueOf(localisationsSize);
		result[3] = Long.toString(algorithmResult.getSolution().getEvaluation());
		result[4] = Long.toString(algorithmResult.getSolution().getEvaluation() - solution.getEvaluation());
		result[5] = Long.toString(algorithmResult.getExecutionReport().getExecutionTime());
		result[6] = Integer.toString(algorithmResult.getExecutionReport().getEvaluatedStatesNumber());
		result[7] = Integer.toString(algorithmResult.getExecutionReport().getStepsNumber());
		
		for(int i=0; i<localisationsSize; i++){
			result[i + 8] = Integer.toString(localisationsOrder[i]);
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
