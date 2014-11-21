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
	long optimum;
	long valueSum;
	double distanceSum;
	double measureSum;
	long timeSum;
	double evaluatedStatesSum;
	int resultCounter;
	
	public ExTSSAExperimentSaver() {
		results = new ArrayList<String[]>();
		avgResults = new ArrayList<String[]>();
		
		String[] columnsNames = new String[9];
		columnsNames[0] = "Algorithm Name";
		columnsNames[1] = "Problem File Name";
		columnsNames[2] = "Problem Size";
		columnsNames[3] = "Optimum";
		columnsNames[4] = "Value";
		columnsNames[5] = "Distance from optimum";
		columnsNames[6] = "Measure";
		columnsNames[7] = "Execution Time";
		columnsNames[8] = "Evaluated states number";
		
		results.add(columnsNames);
		avgResults.add(columnsNames);
		
		currentAlgName = "";
		distanceSum = 0;
		timeSum = 0;
		optimum = 0;
		valueSum = 0;
		measureSum = 0;
		evaluatedStatesSum = 0;
		resultCounter = 0;
	}

	public void addExperimentResult(String problemName, AlgorithmResult algorithmResult) throws FileNotFoundException, IOException, NumberFormatException, ParseException {
		int[] localisationsOrder = algorithmResult.getSolution().getState();
		int localisationsSize = localisationsOrder.length;
		String[] result = new String[9];
		
		SolutionParser solutionParser = new SolutionParser();
		StateEvaluation solution = solutionParser.parseSolutionFileFromResource("/"+problemName+".sln");
		if(!currentAlgName.equals(algorithmResult.getAlgorithmName()) && !currentAlgName.equals("")){
			String[] averageResult = new String[9];
			averageResult[0] = currentAlgName;
			averageResult[1] = currentProbName;
			averageResult[2] = currentProbSize;
			averageResult[3] = new Long(optimum).toString();
			averageResult[4] = new Double(measureSum / resultCounter).toString();
			averageResult[5] = new Double(distanceSum / resultCounter).toString();
			averageResult[6] = new Double(measureSum / resultCounter).toString();
			averageResult[7] = new Double(timeSum / resultCounter).toString();
			averageResult[8] = new Double(evaluatedStatesSum / resultCounter).toString();

			avgResults.add(averageResult);
			
			distanceSum = 0;
			timeSum = 0;
			optimum = 0;
			valueSum = 0;
			measureSum = 0;
			evaluatedStatesSum = 0;
			resultCounter = 0;
		}
		currentAlgName = algorithmResult.getAlgorithmName();
		currentProbName = problemName;
		currentProbSize = String.valueOf(localisationsSize);
		optimum = solution.getEvaluation();
		result[0] = currentAlgName;
		result[1] = currentProbName;
		result[2] = currentProbSize;
		result[3] = Long.toString(optimum);
		result[4] = Double.toString(algorithmResult.getSolution().getEvaluation());
		result[5] = Long.toString(algorithmResult.getSolution().getEvaluation() - optimum);
		result[6] = Double.toString(new Double((new Double(algorithmResult.getSolution().getEvaluation() - optimum))/optimum));
		result[7] = Long.toString(algorithmResult.getExecutionReport().getExecutionTime());
		result[8] = Integer.toString(algorithmResult.getExecutionReport().getEvaluatedStatesNumber());

		results.add(result);
		
		distanceSum += algorithmResult.getSolution().getEvaluation() - solution.getEvaluation();
		timeSum += algorithmResult.getExecutionReport().getExecutionTime();
		valueSum += algorithmResult.getSolution().getEvaluation();
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
		String[] averageResult = new String[9];
		averageResult[0] = currentAlgName;
		averageResult[1] = currentProbName;
		averageResult[2] = currentProbSize;
		averageResult[3] = new Long(optimum).toString();
		averageResult[4] = new Double(measureSum / resultCounter).toString();
		averageResult[5] = new Double(distanceSum / resultCounter).toString();
		averageResult[6] = new Double(measureSum / resultCounter).toString();
		averageResult[7] = new Double(timeSum / resultCounter).toString();
		averageResult[8] = new Double(evaluatedStatesSum / resultCounter).toString();

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
