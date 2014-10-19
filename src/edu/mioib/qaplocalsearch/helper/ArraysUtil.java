package edu.mioib.qaplocalsearch.helper;

public class ArraysUtil {
	public static void swap(int[] array, int idx1, int idx2) {
		array[idx1] = array[idx1] + array[idx2];
		array[idx2] = array[idx1] - array[idx2];
		array[idx1] = array[idx1] - array[idx2];
	}
}
