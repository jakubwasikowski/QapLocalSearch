package edu.mioib.qaplocalsearch.helper;

import java.util.Random;

public class ArraysUtil {
	public static void swap(int[] array, int idx1, int idx2) {
		if (idx1 != idx2) {
			array[idx1] = array[idx1] + array[idx2];
			array[idx2] = array[idx1] - array[idx2];
			array[idx1] = array[idx1] - array[idx2];
		}
	}

	public static int[] generateRandomPerm(int permLength) {
		int[] result = new int[permLength];
		for (int i = 0; i < permLength; i++) {
			result[i] = i + 1;
		}

		Random rand = new Random();
		for (int i = 0; i < permLength - 1; i++) {
			int j = rand.nextInt(permLength - i) + i;
			swap(result, i, j);
		}

		return result;
	}
}
