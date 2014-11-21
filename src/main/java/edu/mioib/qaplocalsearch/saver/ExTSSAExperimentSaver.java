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

public class ExTSSAExperimentSaver {
	List<String[]> results;
	List<String[]> avgResults;
	
	String currentAlgName;
	String currentProbName;
	String currentProbSize;
	double resultSum;
	long timeSum;
	double measureSum;
	double evaluatedStatesSum;
	int resultCounter;
	
	public ExTSSAExperimentSaver() {
		results = new ArrayList<String[]>();
		avgResults = new ArrayList<String[]>();
		
		String[] columnsNames = new String[7];
		columnsNames[0] = "Algorithm Name";
		columnsNames[1] = "Problem File Name";
		columnsNames[2] = "Problem Size";
		columnsNames[3] = "Distance from optimum";
		columnsNames[4] = "Measure";
		columnsNames[5] = "Execution Time";
		columnsNames[6] = "Evaluated states number";
		
		results.add(columnsNames);
		avgResults.add(columnsNames);
		
		currentAlgName = "";
		resultSum = 0;
		timeSum = 0;
		measureSum = 0;
		evaluatedStatesSum = 0;
		resultCounter = 0;
	}

	public void addExperimentResult(String problemName, AlgorithmResult algorithmResult) throws FileNotFoundException, IOException, NumberFormatException, ParseException {
		int[] localisationsOrder = algorithmResult.getSolution().getState();
		int localisationsSize = localisationsOrder.length;
		String[] result = new String[7];
		
		SolutionParser solutionParser = new SolutionParser();
		StateEvaluation solution = solutionParser.parseSolutionFileFromResource("/"+problemName+".sln");
		if(!currentAlgName.equals(algorithmResult.getAlgorithmName()) && !currentAlgName.equals("")){
			String[] averageResult = new String[7];
			averageResult[0] = currentAlgName;
			averageResult[1] = currentProbName;
			averageResult[2] = currentProbSize;
			averageResult[3] = new Double(resultSum / resultCounter).toString();
			averageResult[4] = new Double(measureSum / resultCounter).toString();
			averageResult[5] = new Double(timeSum / resultCounter).toString();
			averageResult[6] = new Double(evaluatedStatesSum / resultCounter).toString();

			avgResults.add(averageResult);
			
			resultSum = 0;
			timeSum = 0;
			measureSum = 0;
			evaluatedStatesSum = 0;
			resultCounter = 0;
		}
		currentAlgName = algorithmResult.getAlgorithmName();
		currentProbName = problemName;
		currentProbSize = String.valueOf(localisationsSize);
		result[0] = currentAlgName;
		result[1] = currentProbName;
		result[2] = currentProbSize;
		result[3] = Long.toString(algorithmResult.getSolution().getEvaluation() - solution.getEvaluation());
		result[4] = Double.toString(new Double(algorithmResult.getSolution().getEvaluation() - solution.getEvaluation())/solution.getEvaluation());
		result[5] = Long.toString(algorithmResult.getExecutionReport().getExecutionTime());
		result[6] = Integer.toString(algorithmResult.getExecutionReport().getEvaluatedStatesNumber());

		results.add(result);
		
		resultSum += algorithmResult.getSolution().getEvaluation() - solution.getEvaluation();
		timeSum += algorithmResult.getExecutionReport().getExecutionTime();
		measureSum += new Double(algorithmResult.getSolution().getEvaluation() - solution.getEvaluation())/solution.getEvaluation();
		evaluatedStatesSum += algorithmResult.getExecutionReport().getEvaluatedStatesNumber();
		resultCounter++;
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
					if(i<result.length-1){
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

	public void saveAverageFile(String path) {
		String[] averageResult = new String[7];
		averageResult[0] = currentAlgName;
		averageResult[1] = currentProbName;
		averageResult[2] = currentProbSize;
		averageResult[3] = new Double(resultSum / resultCounter).toString();
		averageResult[4] = new Double(measureSum/resultCounter).toString();
		averageResult[5] = new Double(timeSum / resultCounter).toString();
		averageResult[6] = new Double(evaluatedStatesSum / resultCounter).toString();

		avgResults.add(averageResult);
		
		try {
			File file = new File(path);
 
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for(String[] result: avgResults){
				for(int i=0; i<result.length ;i++){
					writer.write(result[i]);
					if(i<result.length-1){
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
