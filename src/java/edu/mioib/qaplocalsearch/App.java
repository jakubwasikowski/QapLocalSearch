package edu.mioib.qaplocalsearch;

import static java.lang.System.nanoTime;

import java.util.Arrays;

public class App {

	private static final int MIN_TIME = 10000000;
	private static final int MIN_CALLS_NUMBER = 200;

	public static void main(String[] args) {
		App app = new App();
		// app.start();

		app.printPermNeighbors(new int[] { 1, 2, 3, 4 });
	}

	public void start() {
		long startTime = nanoTime();
		int callCounter = 0;

		double test = 0;

		while (nanoTime() - startTime < MIN_TIME || callCounter < MIN_CALLS_NUMBER) {
			test += testMethod(callCounter, test);

			callCounter++;
		}

		long avgTime = (nanoTime() - startTime) / callCounter;
		System.out.println(avgTime);
		System.out.println(test);
	}

	private double testMethod(int callCounter, double test) {
		for (int i = 0; i < 100000; i++) {
			test += Math.sqrt(1323124213) + 30 * callCounter;
		}
		return test;
	}

	private void printPermNeighbors(int[] perm) {
		for (int i = 0; i < perm.length; i++) {
			for (int j = i + 1; j < perm.length; j++) {
				swap(perm, i, j);
				System.out.println(Arrays.toString(perm));
				swap(perm, i, j);
			}
		}
	}

	private void swap(int[] perm, int i, int j) {
		int temp = perm[i];
		perm[i] = perm[j];
		perm[j] = temp;
	}

}
