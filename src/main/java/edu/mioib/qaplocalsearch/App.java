package edu.mioib.qaplocalsearch;

import java.io.IOException;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class App {

	public static void main(String[] args) throws NumberFormatException, ParseException, IOException {
		ExperimentRunner er = new ExperimentRunner();
		
		System.in.read();

		er.runExperimentForExercise2();
	}

}
