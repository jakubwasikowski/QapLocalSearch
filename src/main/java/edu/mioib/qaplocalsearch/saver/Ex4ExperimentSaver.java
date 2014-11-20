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

public class Ex4ExperimentSaver {
	List<String[]> results;
	int exceutionCounter;
	long currentMinValue;
	int valueSum;
	
	public Ex4ExperimentSaver() {
		results = new ArrayList<String[]>();
		
		String[] columnsNames = new String[8];
		columnsNames[0] = "Algorithm Name";
		columnsNames[1] = "Problem File Name";
		columnsNames[2] = "Problem Size";
		columnsNames[3] = "Function Value";
		columnsNames[4] = "Distance from optimum";
		columnsNames[5] = "Current min value";
		columnsNames[6] = "Current average value";
		columnsNames[7] = "Execution Number";
		
		results.add(columnsNames);
		
		exceutionCounter = 0;
		currentMinValue = Integer.MAX_VALUE;
		valueSum = 0;
	}

	public void addExperimentResult(String problemName, AlgorithmResult algorithmResult) throws FileNotFoundException, IOException, NumberFormatException, ParseException {
		int[] localisationsOrder = algorithmResult.getSolution().getState();
		int localisationsSize = localisationsOrder.length;
		String[] result = new String[8];
		
		long evaluation = algorithmResult.getSolution().getEvaluation();
		valueSum += evaluation;
		if(evaluation<currentMinValue){
			currentMinValue = evaluation;
		}
		exceutionCounter++;
		
		SolutionParser solutionParser = new SolutionParser();
		StateEvaluation solution = solutionParser.parseSolutionFileFromResource("/"+problemName+".sln");
		
		result[0] = algorithmResult.getAlgorithmName();
		result[1] = problemName;
		result[2] = String.valueOf(localisationsSize);
		result[3] = Long.toString(evaluation);
		result[4] = Long.toString(algorithmResult.getSolution().getEvaluation() - solution.getEvaluation());
		result[5] = Long.toString(currentMinValue);
		int currAverage = valueSum/exceutionCounter;
		result[6] = Integer.toString(currAverage);
		result[7] = Integer.toString(exceutionCounter);
		
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
					if(i<(result.length-1)){
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

	public void nextAlgoritm() {
		exceutionCounter = 0;
		currentMinValue = Integer.MAX_VALUE;
		valueSum = 0;
	}
}
