package edu.mioib.qaplocalsearch;

import java.io.IOException;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class App {

	public static void main(String[] args) {
		ExperimentRunner er = new ExperimentRunner();
		try {
			er.runExperminents();
		} catch (NumberFormatException | ParseException | IOException e) {
			e.printStackTrace();
		}
	}

}
