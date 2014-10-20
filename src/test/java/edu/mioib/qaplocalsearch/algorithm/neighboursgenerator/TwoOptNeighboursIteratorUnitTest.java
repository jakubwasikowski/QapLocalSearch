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
		iterator.next();
		assertArrayEquals(new int[]{2, 1, 3, 4}, state);
		assertTrue(iterator.hasNext());
		iterator.next();
		assertArrayEquals(new int[] { 3, 2, 1, 4 }, state);
		assertTrue(iterator.hasNext());
		iterator.next();
		assertArrayEquals(new int[] { 4, 2, 3, 1 }, state);
		assertTrue(iterator.hasNext());
		iterator.next();
		assertArrayEquals(new int[] { 1, 3, 2, 4 }, state);
		assertTrue(iterator.hasNext());
		iterator.next();
		assertArrayEquals(new int[] { 1, 4, 3, 2 }, state);
		assertTrue(iterator.hasNext());
		iterator.next();
		assertArrayEquals(new int[] { 1, 2, 4, 3 }, state);
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

	@Test
	public void shouldReturnsCorrectOriginalStateAfterUseOfNext() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);
		iterator.next();
		iterator.next();
		iterator.getState();
		assertArrayEquals(new int[] { 1, 2, 3 }, state);
	}

	@Test
	public void shouldReturnsCorrectOriginalStateAfterUseOfTheBest() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);
		iterator.next();
		iterator.saveCurrentNeighbourAsTheBest();
		iterator.getTheBestNeighbour();
		iterator.getState();
		assertArrayEquals(new int[] { 1, 2, 3 }, state);
	}

	@Test
	public void shouldReturnsCorrectOriginalStateAfterIteratorCreation() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);
		iterator.getState();
		assertArrayEquals(new int[] { 1, 2, 3 }, state);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowIllegalStateExceptionWhenSaveIsCallBeforeNext() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);
		iterator.saveCurrentNeighbourAsTheBest();
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowIllegalStateExceptionWhenGetBestIsCallBeforeSave() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);
		iterator.next();
		iterator.getTheBestNeighbour();
	}

	@Test
	public void shouldReturnsCorrectTheBestAfterUseOfNext() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);
		iterator.next();
		int[] nextNeigbour = state.clone();
		iterator.saveCurrentNeighbourAsTheBest();
		iterator.next();
		iterator.getTheBestNeighbour();
		assertArrayEquals(nextNeigbour, state);
	}

	@Test
	public void shouldReturnsCorrectTheBestAfterUseOfGetState() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptNeighboursIterator iterator = new TwoOptNeighboursIterator(state);
		iterator.next();
		int[] nextNeigbour = state.clone();
		iterator.saveCurrentNeighbourAsTheBest();
		iterator.next();
		iterator.getState();
		iterator.getTheBestNeighbour();
		assertArrayEquals(nextNeigbour, state);
	}

}
