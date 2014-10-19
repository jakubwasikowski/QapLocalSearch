package edu.mioib.qaplocalsearch.algorithm.neighboursgenerator;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Test;

public class TwoOptNeighboursIteratorUnitTest {

	@Test
	public void shouldDoesNotHasNextForEmptyState() {
		int[] state = new int[]{};
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);

		assertFalse(iterator.hasNext());
	}

	@Test
	public void shouldHasNextForNonEmptyState() {
		int[] state = new int[] { 1, 2, 3, 4 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);

		assertTrue(iterator.hasNext());
	}

	@Test
	public void shouldDoesNotHasNextAfterLastElementOfState() {
		int[] state = new int[] { 1, 2 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);
		iterator.next();

		assertFalse(iterator.hasNext());
	}

	@Test
	public void shouldReturnCorrectNeightbours() {
		int[] state = new int[] { 1, 2, 3, 4 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);
		
		assertTrue(iterator.hasNext());
		assertArrayEquals(new int[]{2, 1, 3, 4}, iterator.next());
		assertTrue(iterator.hasNext());
		assertArrayEquals(new int[]{3, 2, 1, 4}, iterator.next());
		assertTrue(iterator.hasNext());
		assertArrayEquals(new int[]{4, 2, 3, 1}, iterator.next());
		assertTrue(iterator.hasNext());
		assertArrayEquals(new int[]{1, 3, 2, 4}, iterator.next());
		assertTrue(iterator.hasNext());
		assertArrayEquals(new int[]{1, 4, 3, 2}, iterator.next());
		assertTrue(iterator.hasNext());
		assertArrayEquals(new int[]{1, 2, 4, 3}, iterator.next());
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void shouldDoesNotThrowNoSuchElementExceptionForUnexpectedNext() {
		int[] state = new int[] { 1, 2 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);
		iterator.next();
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldThrowNoSuchElementExceptionForUnexpectedNext() {
		int[] state = new int[] { 1, 2 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);
		iterator.next();
		iterator.next();
	}

}
